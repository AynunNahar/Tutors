package com.tutors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.tutors.Fragments.ChangePassword;

public class ListItemView extends AppCompatActivity {

    private static final String TAG =ListItemView.class.getSimpleName() ;
    Toolbar mTutorProfileToolbar;
    TextView tutor_name;
    Firebase mDatabaseRead;
    TextView mT_exp, mSub, mClass, mTime, mHour, mMobile,
            mOccupation, mAddress, mAvailability, mEducationQ,mEmailAddress;
    ImageView profileImage;
    String userKey;
    FirebaseAuth firebaseAuth;
    Spinner spinnerSub,spinnerClass;
    private ProgressDialog progressDialog;

    String url,userEmail,userUid;
    String spinnerValue;

    private String []subjects;
    private String []classes;
    String mobileNumber;

    String nameFav,eqFav,subFav,imageFav,emailFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_view);

        mTutorProfileToolbar = (Toolbar) findViewById(R.id.tutorProfileToolbar);
        setSupportActionBar(mTutorProfileToolbar);
        getSupportActionBar().setTitle(" Tutors");
        getSupportActionBar().setIcon(R.drawable.tutors);

        firebaseAuth = FirebaseAuth.getInstance();


        profileImage = (ImageView) findViewById(R.id.profile_image);
        tutor_name = (TextView) findViewById(R.id.name);
        mT_exp = (TextView) findViewById(R.id.tecahingExp);
        mSub = (TextView) findViewById(R.id.subject);
        mEducationQ = (TextView) findViewById(R.id.education);
        mMobile = (TextView) findViewById(R.id.mobile);
        mClass = (TextView) findViewById(R.id.classP);
        mTime = (TextView) findViewById(R.id.time);
        mHour = (TextView) findViewById(R.id.hour);
        mOccupation = (TextView) findViewById(R.id.occupation);
        mAddress = (TextView) findViewById(R.id.address);
        mAvailability = (TextView) findViewById(R.id.availabilityOption);
        mEmailAddress = (TextView) findViewById(R.id.emailAddress);
        mDatabaseRead = new Firebase("https://tutors-372f4.firebaseio.com/tutors");

      /*  progressDialog = new ProgressDialog(ListItemView.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
*/
        final String product = getIntent().getStringExtra("email");

        mDatabaseRead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Information information = childSnapshot.getValue(Information.class);

                    // getting attached intent data


                    if (product.equals(information.getEmail())) {

                       // progressDialog.hide();

                        Log.e("name", information.getName());
                        tutor_name.setText(information.getName());
                        mTime.setText(information.getAvailableTime());
                        mHour.setText(information.getMaxHourDay());
                        mClass.setText(information.getClassesCanTeach());
                        mSub.setText(information.getSubjectCanTeach());
                        mMobile.setText(information.getMobile());
                        mobileNumber=information.getMobile();
                        mT_exp.setText(information.getTeachingExperience());
                        mAvailability.setText(information.getAvailability());
                        mAddress.setText(information.getAddress());
                        mOccupation.setText(information.getOccupation());
                        mEducationQ.setText(information.getEducationQualification());
                        mEmailAddress.setText(information.getEmail());
                        url = information.getImage();
                        if(!url.equals("empty")){
                            Glide.with(ListItemView.this)
                                    .load(url)
                                    .into(profileImage);

                        }

                        nameFav=information.getName();
                        eqFav=information.getEducationQualification();
                        subFav=information.getSubjectCanTeach();
                        imageFav=information.getImage();
                        emailFav=information.getEmail();
                    }


                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.other_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.sms) {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", mobileNumber);
           // smsIntent.putExtra("sms_body", text);
            startActivity(Intent.createChooser(smsIntent, "SMS:"));
            return true;
        }

        if (id == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+mobileNumber+""));
            startActivity(intent);
            return true;
        }
        if (id == R.id.passwordC) {
            ChangePassword changePassword = new ChangePassword();
            changePassword.show(getSupportFragmentManager(), "Change password");
            return true;
        }

        if (id == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Key", "");
            editor.apply();

            startActivity(new Intent(ListItemView.this, MainActivity.class));
            finish();
        }
        if (id == R.id.post) {
            Intent intent = new Intent(ListItemView.this, UserTimeline.class);
            startActivity(intent);
            return true;
        }

        if(id==R.id.favourites){

            DatabaseHandler mf = new DatabaseHandler(getApplicationContext());
            Contact dt = new Contact(emailFav);
            mf.addContact(dt);
            Toast.makeText(getApplicationContext(), "Data added successfully!", Toast.LENGTH_LONG).show();
        }

        if(id==R.id.favouritesNever){
            Intent intent = new Intent(ListItemView.this,FavouritesList.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


