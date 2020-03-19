package com.tutors.Fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.tutors.R;

/**
 * Created by Princess  on 10/13/2017.
 */

public class MessageOptions extends DialogFragment {

    private Button mMobile,mEmail,mChat;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_options, container, false);

        mMobile=(Button) v.findViewById(R.id.message_mobile);
        mEmail=(Button) v.findViewById(R.id.message_email);
        mChat=(Button) v.findViewById(R.id.message_online_chat);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //String mobileNumber="01728335953";
        mMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "01728335953");
                // smsIntent.putExtra("sms_body", text);
                startActivity(Intent.createChooser(smsIntent, "SMS:"));
            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
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
