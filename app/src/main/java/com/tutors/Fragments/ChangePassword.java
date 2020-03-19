package com.tutors.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tutors.MainActivity;
import com.tutors.R;
import com.tutors.TutorProfileActivity;

import java.util.zip.Inflater;

import static android.R.attr.width;
import static com.tutors.R.attr.height;

/**
 * Created by Princess on 9/21/2017.
 */

public class ChangePassword extends DialogFragment {
    private TextInputLayout testInputPassword;
    private TextInputLayout textInputRePassword;
    private TextInputEditText etPassword, etRePassword;
    private Button savePassword;
    private ProgressDialog progressDialog;
    private String password, rePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_password, container, false);

        testInputPassword = (TextInputLayout) v.findViewById(R.id.layout_password);
        textInputRePassword = (TextInputLayout) v.findViewById(R.id.layout_rePassword);
        etPassword = (TextInputEditText) v.findViewById(R.id.enterPassword);
        etRePassword = (TextInputEditText) v.findViewById(R.id.rePassword);
        savePassword = (Button) v.findViewById(R.id.passwordSave);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setLayout(2500,300);
        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                boolean isEmptyPassword = isEmptyPassword();
                boolean isEmptyRePassword = isEmptyRePassword();
                if (isEmptyPassword && isEmptyRePassword) {
                    Toast.makeText(getActivity(), "One Or More Fields Are Blank", Toast.LENGTH_SHORT).show();
                } else if (isEmptyPassword && !isEmptyRePassword) {
                    testInputPassword.setError("Password Cannot Be Empty");
                    Toast.makeText(getActivity(), "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    textInputRePassword.setError(null);
                } else if (!isEmptyPassword && isEmptyRePassword) {
                    textInputRePassword.setError("Re-Password Cannot Be Empty");
                    testInputPassword.setError(null);
                    Toast.makeText(getActivity(), "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(getActivity(), "Password do not matched", Toast.LENGTH_SHORT).show();

                } else {
                    //All Code Here
                    Log.e("rePassword", rePassword);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updatePassword(rePassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.hide();
                                        Toast.makeText(getActivity(), "Your password updated.", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                        firebaseAuth.signOut();
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        //finish();
                                    }
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.hide();
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });
        return v;
    }

    private boolean isEmptyPassword() {
        password = etPassword.getText().toString();
        return etPassword.getText() == null
                || etPassword.getText().toString() == null
                || etPassword.getText().toString().isEmpty();
    }

    private boolean isEmptyRePassword() {
        rePassword = etRePassword.getText().toString();
        return etRePassword.getText() == null
                || etRePassword.getText().toString() == null
                || etRePassword.getText().toString().isEmpty();
    }
    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
       // progressDialog.show();
        window.setAttributes(lp);

    }
}
