package com.tutors.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutors.R;

import java.util.ArrayList;

/**
 * Created by Princess  on 10/1/2017.
 */

public class PostPageAdapter extends BaseAdapter {

    ArrayList<String> adaptaer_names=new ArrayList<String>();
    ArrayList<String> adaptaer_time=new ArrayList<String>();
    ArrayList<String> adapter_post=new ArrayList<String>();
    Context ct;
    int cnt=0;

    public static LayoutInflater inflater = null;

    public PostPageAdapter(Activity postList, ArrayList<String> names, ArrayList<String>time,
                           ArrayList<String> post) {
        adaptaer_names = names;
        adaptaer_time = time;
        adapter_post = post;
        ct = postList;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adaptaer_names.size();
    }

    @Override
    public Object getItem(int i) {
        return adaptaer_names.get(getCount() - i - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Myholder {
        TextView holder_names;
        TextView holder_time;
        TextView holder_post;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PostPageAdapter.Myholder myh = new PostPageAdapter.Myholder();
        View myview;

        myview = inflater.inflate(R.layout.inner_layout, null);

        myh.holder_names = (TextView) myview.findViewById(R.id.postMan);
        myh.holder_time = (TextView) myview.findViewById(R.id.date);
        myh.holder_post = (TextView) myview.findViewById(R.id.viewPost);

        myh.holder_names.setText(adaptaer_names.get(i));
        myh.holder_time.setText(adaptaer_time.get(i));
        myh.holder_post.setText(adapter_post.get(i));

        //Log.e("postHolder",adapter_post.get(i));
        return myview;
    }
}
