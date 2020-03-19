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

public class FavListViewActivity extends AppCompatActivity {
    private static final String TAG = FavListViewActivity.class.getSimpleName();
    Toolbar mTutorProfileToolbar;
    TextView tutor_name;
    Firebase mDatabaseRead;
    TextView mT_exp, mSub, mClass, mTime, mHour, mMobile,
            mOccupation, mAddress, mAvailability, mEducationQ, mEmailAddress;
    ImageView profileImage;
    String userKey;
    FirebaseAuth firebaseAuth;
    Spinner spinnerSub, spinnerClass;
    private ProgressDialog progressDialog;

    String url, userEmail, userUid;
    String spinnerValue;

    private String[] subjects;
    private String[] classes;
    String mobileNumber, deleteEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list_view);

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
                        mobileNumber = information.getMobile();
                        mT_exp.setText(information.getTeachingExperience());
                        mAvailability.setText(information.getAvailability());
                        mAddress.setText(information.getAddress());
                        mOccupation.setText(information.getOccupation());
                        mEducationQ.setText(information.getEducationQualification());
                        mEmailAddress.setText(information.getEmail());
                        deleteEmail = information.getEmail();
                        url = information.getImage();
                        if (!url.equals("empty")) {
                            Glide.with(FavListViewActivity.this)
                                    .load(url)
                                    .into(profileImage);

                        }

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
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int fav_id = item.getItemId();
        if (fav_id == R.id.post_fav) {
            Intent intent = new Intent(FavListViewActivity.this, UserTimeline.class);
            startActivity(intent);
            return true;
        }
        if (fav_id == R.id.call_fav) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+mobileNumber+""));
            startActivity(intent);
            return true;
        }
        if (fav_id == R.id.mgs_fav) {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", mobileNumber);
            // smsIntent.putExtra("sms_body", text);
            startActivity(Intent.createChooser(smsIntent, "SMS:"));
            return true;
        }
        if (fav_id == R.id.signout_fav) {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Key", "");
            editor.apply();

            startActivity(new Intent(FavListViewActivity.this, MainActivity.class));
            finish();
        }
        if (fav_id == R.id.timeline_fav) {

        }
        if (fav_id == R.id.fav) {
            Intent intent = new Intent(FavListViewActivity.this,FavouritesList.class);
            startActivity(intent);
            return true;
        }
        if (fav_id == R.id.fav_delete) {

            DatabaseHandler db=new DatabaseHandler(this);
            Contact cn=new Contact(deleteEmail);
            db.deleteContact(cn);
            Toast.makeText(FavListViewActivity.this,"Deleted",Toast.LENGTH_LONG).show();
            startActivity(new Intent(FavListViewActivity.this,FavouritesList.class));
        }
        if(fav_id==R.id.passC){
            ChangePassword changePassword = new ChangePassword();
            changePassword.show(getSupportFragmentManager(), "Change password");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
