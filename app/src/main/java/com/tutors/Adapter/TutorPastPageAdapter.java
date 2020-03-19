package com.tutors.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutors.R;
import com.tutors.TutorsPostPageAllActivity;

import java.util.ArrayList;


/**
 * Created by Princess  on 10/19/2017.
 */

public class TutorPastPageAdapter extends BaseAdapter {

    ArrayList<String> adaptaer_names=new ArrayList<String>();
    ArrayList<String> adaptaer_time=new ArrayList<String>();
    ArrayList<String> adapter_post=new ArrayList<String>();
    ArrayList<String> adapter_mobileNumber=new ArrayList<String>();
    Context ct;


    public TutorPastPageAdapter(ArrayList<String> adaptaer_names, ArrayList<String>
            adaptaer_time, ArrayList<String> adapter_post,
                                ArrayList<String> adapter_mobileNumber, Context ct) {
        this.adaptaer_names = adaptaer_names;
        this.adaptaer_time = adaptaer_time;
        this.adapter_post = adapter_post;
        this.adapter_mobileNumber = adapter_mobileNumber;
        this.ct = ct;
    }

    @Override
    public int getCount() {
        return this.adaptaer_names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public class Myholder {
        TextView holder_names;
        TextView holder_time;
        TextView holder_post;
        TextView holder_call;
        LinearLayout holder_LL;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Myholder myh = new Myholder();
        View myview2;

        myview2=View.inflate(this.ct,R.layout.tutor_post_list,null);

        myh.holder_names = (TextView) myview2.findViewById(R.id.username);
        myh.holder_time = (TextView) myview2.findViewById(R.id.time);
        myh.holder_post = (TextView) myview2.findViewById(R.id.post);
        myh.holder_call = (TextView) myview2.findViewById(R.id.call);
        myh.holder_LL=(LinearLayout)myview2.findViewById(R.id.actionCall);

        myh.holder_names.setText(this.adaptaer_names.get(i));
        myh.holder_time.setText(this.adaptaer_time.get(i));
        myh.holder_post.setText(this.adapter_post.get(i));
        myh.holder_call.setText(this.adapter_mobileNumber.get(i));

        final String number=this.adapter_mobileNumber.get(i);
        myh.holder_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number+""));
                ct.startActivity(intent);
            }
        });
        return myview2;
    }
}
