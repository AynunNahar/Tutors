package com.tutors;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tutors.Adapter.PostPageAdapter;
import com.tutors.Adapter.TutorPastPageAdapter;
import com.tutors.Fragments.PostInformation;

import java.util.ArrayList;

public class TutorsPostPageAllActivity extends AppCompatActivity {

    ListView lv;
    Firebase mPostFirebase;
    LinearLayout mLinearLayoutCall;
    TextView callTv;

    final ArrayList<String> arrayUserNames = new ArrayList<String>();
    final ArrayList<String> arrayPostTime = new ArrayList<String>();
    final ArrayList<String> arrayPost = new ArrayList<String>();
    final ArrayList<String> arrayMobileNumber = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_post_page_all);

        lv = (ListView) findViewById(R.id.tutors_post_listView);
        mLinearLayoutCall=(LinearLayout) findViewById(R.id.actionCall);
        mPostFirebase = new Firebase("https://tutors-372f4.firebaseio.com/post");

        mPostFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PostInformation postInformation = childSnapshot.getValue(PostInformation.class);
                    String userName=postInformation.getUsername();
                    String postTime=postInformation.getTime();
                    String post=postInformation.getPost();
                    String mobileNumber=postInformation.getMobileNumber();

                    arrayUserNames.add(userName);
                    arrayPostTime.add(postTime);
                    arrayPost.add(post);
                    arrayMobileNumber.add(mobileNumber);

                }


                    TutorPastPageAdapter tutorPastPageAdapter = new TutorPastPageAdapter(arrayUserNames,
                            arrayPostTime , arrayPost,arrayMobileNumber , TutorsPostPageAllActivity.this);
                    lv.setAdapter(tutorPastPageAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

       /* mLinearLayoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTv=(TextView)findViewById(R.id.call);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mobileNumber+""));
                startActivity(intent);
            }
        });*/


    }
}
