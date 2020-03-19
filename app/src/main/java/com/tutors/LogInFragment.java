package com.tutors;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import static android.content.ContentValues.TAG;

public class LogInFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener {

    View v;
    private Button mLogin;
    private EditText mEmail, mPassword;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroupL;
    private String radioGroupScelect;
    private Firebase firebaseTutors, firebaseOthers, firebase;
    private ProgressDialog progressDialog;

    LinearLayout mpasswordRecoverLayout;
    private String email, password;
    private String select = "empty";


    private FirebaseAuth.AuthStateListener authStateListener;
    String takeKey;
    String emailT, uid;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_log_in, container, false);


        firebaseTutors = new Firebase("https://tutors-372f4.firebaseio.com/tutors");
        firebaseOthers = new Firebase("https://tutors-372f4.firebaseio.com/others");
        firebase = new Firebase("https://tutors-372f4.firebaseio.com/");

        mEmail = (EditText) v.findViewById(R.id.email);
        mPassword = (EditText) v.findViewById(R.id.password);
        mLogin = (Button) v.findViewById(R.id.Login);
        radioGroupL = (RadioGroup) v.findViewById(R.id.radioGroupLogin);
        mAuth = FirebaseAuth.getInstance();

        radioGroupL.setOnCheckedChangeListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() != null) {

                    emailT = firebaseAuth.getCurrentUser().getEmail();
                    uid = firebaseAuth.getCurrentUser().getUid();
                    Log.e("email", emailT);


                        firebaseTutors.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    Information information = childSnapshot.getValue(Information.class);

                                    String db_email = information.getEmail();

                                    if (emailT.equals(db_email)) {
                                        takeKey = information.getuKey();
                                        Log.e("LogInActTeacher", "key" + takeKey);

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("Key", takeKey);
                                    editor.apply();
                                      select="tutor" ;
                                        startActivity(new Intent(getActivity(),TutorProfileActivity.class));

                                    }

                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        firebaseOthers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    Information information = childSnapshot.getValue(Information.class);

                                    String db_email = information.getEmail();

                                    if (emailT.equals(db_email)) {
                                        takeKey = information.getuKey();
                                        Log.e("LogInActTeacher", "key" + takeKey);

                                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("Key", takeKey);
                                    editor.apply();
                                        select="other" ;
                                        startActivity(new Intent(getActivity(),TutorsListActivity.class));

                                    }


                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }

                        });

                    Log.e("select",select);
                   /* if (select.equals("tutor")){
                        startActivity(new Intent(getActivity(),TutorProfileActivity.class));
                    }

                    else   if (select.equals("other")){
                        startActivity(new Intent(getActivity(),TutorsListActivity.class));
                    }*/

                }

            }
        };

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logInFunction();
            }
        });

        mpasswordRecoverLayout=(LinearLayout) v.findViewById(R.id.passwordRecoverLayout);
        mpasswordRecoverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email;
               // Toast.makeText(getActivity(),"Email: "+emailAddress,Toast.LENGTH_LONG).show();

                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(getActivity(),"Check email for reset your password",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        return v;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_tutors_login:
                //if (checked)
                radioGroupScelect = "tutor";
                Log.e("signUp", radioGroupScelect);
                break;
            case R.id.radio_others_login:
                // if (checked)
                radioGroupScelect = "other";
                Log.e("signUp", radioGroupScelect);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        mAuth.addAuthStateListener(authStateListener);


    }

    private void updateUI(FirebaseUser currentUser) {

    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void logInFunction() {
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (email.equals("") && password.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Email & Password", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();

        } else if (radioGroupScelect.equals("")) {
            Toast.makeText(getActivity(), "Please Select Student Or Teacher", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(),
                            new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");

                                        mEmail.setText("");
                                        mPassword.setText("");


                                       // Toast.makeText(getActivity(), "uKey" + takeKey, Toast.LENGTH_LONG).show();
                                        if (radioGroupScelect.equals("tutor")) {

                                            final String currentUserEmail = mAuth.getCurrentUser().getEmail();
                                            Log.e("currentUserEmail", currentUserEmail);

                                            firebaseTutors.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                        Information information = childSnapshot.getValue(Information.class);

                                                        String db_email = information.getEmail();

                                                        if (currentUserEmail.equals(db_email)) {
                                                            takeKey = information.getuKey();
                                                            Log.e("LogInActTeacher", "key" + takeKey);

                                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("Key", takeKey);
                                                            editor.apply();
                                                        }


                                                    }

                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT);
                                            Intent intent = new Intent(getActivity(), TutorProfileActivity.class);
                                            startActivity(intent);

                                        }
                                        else if (radioGroupScelect.equals("other")) {

                                            final String currentUserEmail = mAuth.getCurrentUser().getEmail();
                                            //Log.e("currentUserEmail",  currentUserEmail);

                                            firebaseOthers.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                        Information information = childSnapshot.getValue(Information.class);

                                                        String db_email = information.getEmail();
                                                        //  Log.e("db_email",db_email);
                                                        if (currentUserEmail.equals(db_email)) {

                                                            takeKey = information.getuKey();

                                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("Key", takeKey);
                                                            editor.apply();
                                                            Log.e("LogInActOther", "key" + takeKey);
                                                        }


                                                    }

                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                            Toast.makeText(getActivity(), "Other Activity", Toast.LENGTH_SHORT);
                                            startActivity(new Intent(getActivity(), TutorsListActivity.class));
                                        }

                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Fail" + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

}
