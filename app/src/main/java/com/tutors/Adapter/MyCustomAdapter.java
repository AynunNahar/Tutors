package com.tutors.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutors.R;
import com.tutors.TutorProfileActivity;
import com.tutors.TutorsListActivity;

import java.util.ArrayList;

import static com.tutors.R.id.image;
import static com.tutors.R.id.profileImage;

/**
 * Created by Princess on 9/26/2017.
 */

public class MyCustomAdapter extends BaseAdapter {

    ArrayList<String> adaptaer_names;
    ArrayList<String> adaptaer_eq;
    ArrayList<String> adapter_sub;
    ArrayList<String> adapter_image;
    Context ct;

    public static LayoutInflater inflater = null;

    //constructor:
    public MyCustomAdapter(Activity tutorList, ArrayList<String> names, ArrayList<String> eq,
                           ArrayList<String> subjects, ArrayList<String> image) {
        adaptaer_names = names;
        adaptaer_eq = eq;
        adapter_sub = subjects;
        ct = tutorList;
        adapter_image = image;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adaptaer_names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Myholder {
        TextView holder_names;
        TextView holder_eq;
        TextView holder_sub;
        ImageView holder_image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Myholder myh = new Myholder();
        View myview;

        myview = inflater.inflate(R.layout.customlistview, null);

        myh.holder_names = (TextView) myview.findViewById(R.id.profileName);
        myh.holder_eq = (TextView) myview.findViewById(R.id.educationQ);
        myh.holder_image = (ImageView) myview.findViewById(R.id.profile_image);
        myh.holder_sub = (TextView) myview.findViewById(R.id.InterestedSubjectName);

        myh.holder_names.setText(adaptaer_names.get(position));
        myh.holder_eq.setText(adaptaer_eq.get(position));
        myh.holder_sub.setText(adapter_sub.get(position));

        if(!adapter_image.get(position).equals("empty")){
            Glide.with(ct)
                    .load(adapter_image.get(position))
                    .into(myh.holder_image);

        }

        return myview;
    }
}
