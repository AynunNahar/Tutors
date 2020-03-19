package com.tutors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment implements
        RadioGroup.OnCheckedChangeListener {
    private EditText sEmail, sPassword, sName, sMobile;
    private Button sSignUp;
    private FirebaseAuth sAuth;
    private Firebase mFirebase_tutors, mFirebase_others;

    private RadioGroup radioGroup;
    private String radioButtonSelect;
    private static final String TAG = SignUpFragment.class.getSimpleName();

    String name, email, mobile, password, takeKey;

    View v;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_sign_up, container, false);

        sEmail = (EditText) v.findViewById(R.id.email);
        sPassword = (EditText) v.findViewById(R.id.password);
        sName = (EditText) v.findViewById(R.id.textInputEditTextName);
        sMobile = (EditText) v.findViewById(R.id.textInputEditTextMobail);
        sSignUp = (Button) v.findViewById(R.id.signupbutton);
        // mSignInSpinner=(Spinner)v.findViewById(R.id.signInSpinner);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        sAuth = FirebaseAuth.getInstance();


        sSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = sName.getText().toString();
                mobile = sMobile.getText().toString();

                signUp();

            }
        });

        return v;
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = sAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }


    public void signUp() {
        final String mEmail = sEmail.getText().toString();
        password = sPassword.getText().toString();

        mFirebase_tutors = new Firebase("https://tutors-372f4.firebaseio.com/tutors");
        mFirebase_others = new Firebase("https://tutors-372f4.firebaseio.com/others");

        sAuth.createUserWithEmailAndPassword(mEmail, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            sName.setText("");
                            sPassword.setText("");
                            sEmail.setText("");
                            sMobile.setText("");

                            // sendEmailVerification();
                            // Sign in success, update UI with the signed-in user's information
                            if (radioButtonSelect == "tutor") {
                                Map<String, String> info = new HashMap<String, String>();

                                info.put("name", name);
                                info.put("mobile", mobile);
                                info.put("email", mEmail);
                                info.put("educationQualification", "empty");
                                info.put("teachingExperience", "empty");
                                info.put("subjectCanTeach", "empty");
                                info.put("classesCanTeach", "empty");
                                info.put("availableTime ", "empty");
                                info.put("maxHourDay", "empty");
                                info.put("occupation", "empty");
                                info.put("address", "empty");
                                info.put("availability", "empty");
                                info.put("image", "empty");

                                String uKey = mFirebase_tutors.push().getKey();
                                info.put("uKey", uKey);

                                mFirebase_tutors.child(uKey).setValue(info);

                                Log.e(TAG, "createUserWithEmail:success");
                                FirebaseUser user = sAuth.getCurrentUser();
                                updateUI(user);

                                Toast.makeText(getActivity(), "Sign Up completed", Toast.LENGTH_SHORT).show();

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Key", uKey);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), TutorProfileActivity.class);
                                startActivity(intent);

                            } else if (radioButtonSelect == "other") {

                                Map<String, String> info = new HashMap<String, String>();

                                info.put("name", name);
                                info.put("mobile", mobile);
                                info.put("email", mEmail);

                                String uKey = mFirebase_others.push().getKey();
                                info.put("uKey", uKey);

                                mFirebase_others.push().setValue(info);
                                Log.e(TAG, "createUserWithEmail:success");
                                FirebaseUser user = sAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(getActivity(), "Sign Up completed" + uKey, Toast.LENGTH_LONG).show();

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Key", uKey);
                                editor.apply();
                                Log.e("sign", "key" + uKey);

                                Intent intent = new Intent(getActivity(), TutorsListActivity.class);
                                startActivity(intent);                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                           /* Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            updateUI(null);
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail" + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Fail" + e.getMessage());
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_tutors:
                //if (checked)
                radioButtonSelect = "tutor";
                Log.e("signUp", radioButtonSelect);
                break;
            case R.id.radio_others:
                // if (checked)
                radioButtonSelect = "other";
                Log.e("signUp", radioButtonSelect);
                break;
        }
    }
}





