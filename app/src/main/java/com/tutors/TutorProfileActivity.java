package com.tutors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.api.model.StringList;
import com.squareup.picasso.Picasso;
import com.tutors.Fragments.ChangePassword;

import java.util.Map;

public class TutorProfileActivity extends AppCompatActivity {

    Toolbar mTutorProfileToolbar;
    TextView tutor_name;
    Firebase mDatabaseRead;
    TextView mT_exp, mSub, mClass, mTime, mHour, mMobile,
            mOccupation, mAddress, mAvailability, mEducationQ;
    ImageView profileImage;
    FirebaseAuth firebaseAuth;
     ProgressDialog progressDialog;
    String url,uKey,userEmail,userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

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
        mDatabaseRead = new Firebase("https://tutors-372f4.firebaseio.com/tutors");

        progressDialog = new ProgressDialog(TutorProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TutorProfileActivity.this);
        uKey = preferences.getString("Key", "");
        Log.e("userKey", uKey);

        mDatabaseRead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    Information information = childSnapshot.getValue(Information.class);

                    if (uKey.equals(information.getuKey())) {

                        Log.e("name", information.getName());

                        String tName = information.getName();
                        tutor_name.setText(tName);

                        String time = information.getAvailableTime();
                        mTime.setText(time);

                        String hr = information.getMaxHourDay();
                        mHour.setText(hr);

                        String cls = information.getClassesCanTeach();
                        mClass.setText(cls);

                        String sb = information.getSubjectCanTeach();
                        mSub.setText(sb);

                        String mb = information.getMobile();
                        mMobile.setText(mb);

                        String exp = information.getTeachingExperience();
                        mT_exp.setText(exp);

                        String avl = information.getAvailability();
                        mAvailability.setText(avl);

                        String add = information.getAddress();
                        mAddress.setText(add);

                        String oc = information.getOccupation();
                        mOccupation.setText(oc);

                        String ed = information.getEducationQualification();
                        mEducationQ.setText(ed);

                        url = information.getImage();
                        if (!url.equals("empty")) {
                            Glide.with(TutorProfileActivity.this)
                                    .load(url)
                                    .into(profileImage);

                        }
                        progressDialog.hide();
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
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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
            finish();
            startActivity(new Intent(TutorProfileActivity.this, MainActivity.class));

        }
        if (id == R.id.updateProfile2) {
            startActivity(new Intent(this, upActivity.class));
            return true;
        }

        if (id == R.id.viewPost) {
            startActivity(new Intent(this, TutorsPostPageAllActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


