package com.tutors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class upActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    Spinner spinner;
    Toolbar mUpToolbar;
    Firebase firebase, firebaseName, firebaseAddress, firebaseOccupation,
            firebaseEducationQualification, firebaseSub, firebaseClass,
            firebaseTime, firebaseHour, firebaseT_exp,
            firebaseImage, firebaseAvailability;

    Button mUpdateProfile;
    EditText mEducation_Qualification;
    EditText mName;
    EditText mTeachingExperience, mSubjectCanTeach,
            mClassesCanTeach, mAvailableTime, mMaxHours,
            mAddress, mOccupation;
    String key;

    ImageView proPic;
    RelativeLayout uploadProfile;
    String downloadURL="";

    private FirebaseAuth firebaseAuth;
    private String userEmail, userKey, select;
    private StorageReference storageReference;

    private static final int GALLERY_INTENT = 2;

    private RadioGroup radioGroup;
    private String radioButtonSelect = "";
    String link="empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        mUpToolbar = (Toolbar) findViewById(R.id.upToolbar);
        setSupportActionBar(mUpToolbar);
        getSupportActionBar().setTitle(" Update profile");
        getSupportActionBar().setIcon(R.drawable.tutors);

        radioGroup = (RadioGroup) findViewById(R.id.availabilityRadioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = firebaseAuth.getCurrentUser().getEmail();

        firebase = new Firebase("https://tutors-372f4.firebaseio.com/tutors");

        mUpdateProfile = (Button) findViewById(R.id.update_profile);
        mEducation_Qualification = (EditText) findViewById(R.id.education_qualification);
        mTeachingExperience = (EditText) findViewById(R.id.teaching_exp);
        mSubjectCanTeach = (EditText) findViewById(R.id.subjects);
        mClassesCanTeach = (EditText) findViewById(R.id.classes);
        mAvailableTime = (EditText) findViewById(R.id.time);
        mMaxHours = (EditText) findViewById(R.id.hour);
        mName = (EditText) findViewById(R.id.name);
        mAddress = (EditText) findViewById(R.id.address);
        mOccupation = (EditText) findViewById(R.id.occupation);
        proPic = (ImageView) findViewById(R.id.profileImage);
        uploadProfile = (RelativeLayout) findViewById(R.id.uploadPhotoLayout);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        key = preferences.getString("Key", "");
        Log.e("key", key);

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qualification = mEducation_Qualification.getText().toString();
                String name = mName.getText().toString();
                String t_exp = mTeachingExperience.getText().toString();
                String sub = mSubjectCanTeach.getText().toString();
                String cls = mClassesCanTeach.getText().toString();
                String time = mAvailableTime.getText().toString();
                String m_hr = mMaxHours.getText().toString();
                String address = mAddress.getText().toString();
                String occupation = mOccupation.getText().toString();
                String availability = radioButtonSelect;


                //String userK=firebase.push().getKey();

                if (!name.equals("")) {
                    firebaseName = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/name");
                    firebaseName.setValue(name);
                }
                if (!address.equals("")) {
                    firebaseAddress = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/address");
                    firebaseAddress.setValue(address);
                }
                if (!occupation.equals("")) {
                    firebaseOccupation = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/occupation");
                    firebaseOccupation.setValue(occupation);
                }
                if (!qualification.equals("")) {
                    firebaseEducationQualification = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/educationQualification");
                    firebaseEducationQualification.setValue(qualification);
                }
                if (!t_exp.equals("")) {
                    firebaseT_exp = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/teachingExperience");
                    firebaseT_exp.setValue(t_exp);
                }
                if (!sub.equals("")) {
                    firebaseSub = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/subjectCanTeach");
                    firebaseSub.setValue(sub);
                }
                if (!cls.equals("")) {
                    firebaseClass = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/classesCanTeach");
                    firebaseClass.setValue(cls);
                }
                if (!time.equals("")) {
                    firebaseTime = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/availableTime");
                    firebaseTime.setValue(time);
                }
                if (!m_hr.equals("")) {
                    firebaseHour = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/maxHourDay");
                    firebaseHour.setValue(m_hr);
                }
                if (!availability.equals("")) {
                    firebaseAvailability = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/availability");
                    firebaseAvailability.setValue(availability);
                }

                mName.setText("");
                mAddress.setText("");
                mOccupation.setText("");
                mEducation_Qualification.setText("");
                mClassesCanTeach.setText("");
                mSubjectCanTeach.setText("");
                mAvailableTime.setText("");
                mMaxHours.setText("");
                mTeachingExperience.setText("");


                if(!downloadURL.equals("empty")){
                    Glide.with(upActivity.this)
                            .load(downloadURL)
                            .into(proPic);
                }

                startActivity(new Intent(upActivity.this, TutorProfileActivity.class));

            }
        });


        uploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            final StorageReference filePath = storageReference.child("Photos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // progressDialog.hide();
                     downloadURL = ""+taskSnapshot.getDownloadUrl();

                    //Intent intent = new Intent(upActivity.this, TutorProfileActivity.class);
                    //intent.putExtra("photoURL", downloadURL);
                    //intent.putExtra("key", userKey);

                    Log.e("url", downloadURL);
                    Log.e("key", key);

                    Toast.makeText(upActivity.this, "Image Upload Successful", Toast.LENGTH_LONG).show();
                   if(!downloadURL.equals("")){
                       firebaseImage = new Firebase("https://tutors-372f4.firebaseio.com/tutors/" + key + "/image");
                       firebaseImage.setValue(downloadURL);
                       link=downloadURL;
                   }

                    //startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upActivity.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (checkedId) {
            case R.id.radioAvailable:
                //if (checked)
                radioButtonSelect = "Available";
                Log.e("signUp", radioButtonSelect);
                break;
            case R.id.radioNegotiation:
                // if (checked)
                radioButtonSelect = "Negotiation";
                Log.e("signUp", radioButtonSelect);
                break;
        }
    }
}
