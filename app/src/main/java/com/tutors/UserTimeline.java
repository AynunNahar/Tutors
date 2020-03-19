package com.tutors;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tutors.Adapter.PostPageAdapter;
import com.tutors.Fragments.PostInformation;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserTimeline extends AppCompatActivity {

    EditText et_userPost, etComment;
    TextView postTime, userName, mViewPost, postManName, postTimeShow;
    Firebase mFirebasePost, mFirebaseOthers;
    Button mPostButton;
    String userKey,otherKey,mobileNumber;
    String formattedDate;
    String uName, postItem, userEmail, userUid;
    LinearLayout myLinearLayout, myLinearLayout2;

    ListView lv;
    final ArrayList<String> arrayUserNames = new ArrayList<String>();
    final ArrayList<String> arrayPostTime = new ArrayList<String>();
    final ArrayList<String> arrayPost = new ArrayList<String>();

    final ArrayList<String> arrayUserNamesReverse = new ArrayList<String>();
    final ArrayList<String> arrayPostTimeReverse  = new ArrayList<String>();
    final ArrayList<String> arrayPostReverse  = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_timeline);

        //put post
        postTime = (TextView) findViewById(R.id.postDatePicker);
        userName = (TextView) findViewById(R.id.post_userName);
        mPostButton = (Button) findViewById(R.id.post);
        lv = (ListView) findViewById(R.id.userListView);
        et_userPost = (EditText) findViewById(R.id.et_post);
        mFirebasePost = new Firebase("https://tutors-372f4.firebaseio.com/post");
        mFirebaseOthers = new Firebase("https://tutors-372f4.firebaseio.com/others");


        //Date currentTime = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy");
        formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        postTime.setText(formattedDate);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserTimeline.this);
        userKey = preferences.getString("Key", "");
        Log.e("userKey", userKey);

        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

            // Setting on Touch Listener for handling the touch inside ScrollView
        });

        mFirebaseOthers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Information information = childSnapshot.getValue(Information.class);
                    if (userKey.equals(information.getuKey())) {
                        uName = information.getName();
                        Log.e("name", uName);
                        userName.setText(uName);
                        otherKey=information.getuKey();
                        mobileNumber=information.getMobile();
                    }
                }


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        Log.e("name",uName);


        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postItem = et_userPost.getText().toString();
                //arrayPost.add(postItem);

                Map<String, String> info = new HashMap<String, String>();

                info.put("post", postItem);
                info.put("username", uName);
                info.put("time", formattedDate);
                info.put("usersKey", otherKey);
                info.put("mobileNumber", mobileNumber);

                String uKey = mFirebasePost.push().getKey();
                info.put("uKey", uKey);

                mFirebasePost.child(uKey).setValue(info);

                et_userPost.setText("");
            }
        });

        mFirebasePost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    PostInformation postInformation = childSnapshot.getValue(PostInformation.class);

                    if (userKey.equals(postInformation.getUsersKey())) {

                        String post = postInformation.getPost();
                        Log.e("post", post);
                        String postTime = postInformation.getTime();
                        Log.e("postTime", postTime);
                        Log.e("posterName", uName);

                        arrayPost.add(post);
                        arrayPostTime.add(postTime);
                        arrayUserNames.add(uName);

                    }

                }

                if (arrayPost.size() > 0) {
                    PostPageAdapter postPageAdapter = new PostPageAdapter(UserTimeline.this, arrayUserNames,
                            arrayPostTime , arrayPost );
                    // lv.getChildAt(1);
                    lv.setAdapter(postPageAdapter);
                    //lv.setSelection(lv.getAdapter().getCount()-1);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });


    }
}
