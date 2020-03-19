package com.tutors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.tutors.Adapter.MyCustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouritesList extends AppCompatActivity
        implements OnItemClickListener,AdapterView.OnItemSelectedListener {

    Firebase firebaseTutors;
    String s="";
    ListView myList;


    private ArrayList<String> arrayNames = new ArrayList<String>();
    private ArrayList<String> arrayEq = new ArrayList<String>();
    private ArrayList<String> arraySubject = new ArrayList<String>();
    private ArrayList<String> arrayImage = new ArrayList<String>();
    private ArrayList<String> arrayEmail = new ArrayList<String>();
    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    int i;
    String[] contactE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_list);

        myList =(ListView)findViewById(R.id.favouritesList);
        firebaseTutors=new Firebase("https://tutors-372f4.firebaseio.com/tutors");
        DatabaseHandler db = new DatabaseHandler(this);

        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getId()+" ,Email: " + cn.getEmailId();
            // Writing Contacts to log
            String email=cn.getEmailId();
            Log.e("Name: ", email);
        }

        for( final Contact cn : contacts){

            final String cnEmail=cn.getEmailId();
            Log.e("name",cnEmail);

            firebaseTutors.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Information information = childSnapshot.getValue(Information.class);


                        String email = information.getEmail();
                        Log.e("name", email);

                        if (cnEmail.equals(email)) {

                            String name = information.getName();
                            String eq = information.getEducationQualification();
                            String sub = information.getSubjectCanTeach();
                            String image = information.getImage();
                            String emailT = information.getEmail();
                            Log.e("name", name);
                            // Log.e("Link",information.getImage());

                            arrayNames.add(name);
                            arrayEq.add(eq);
                            arraySubject.add(sub);
                            arrayImage.add(image);
                            arrayEmail.add(emailT);

                        }
                        if (arrayNames.size() > 0) {
                            Log.e("Come", "Yes");
                            final MyCustomAdapter myadapter = new MyCustomAdapter(FavouritesList.this,
                                    arrayNames, arrayEq, arraySubject, arrayImage);

                            myList.setAdapter(myadapter);
                            myList.setOnItemClickListener(FavouritesList.this);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       String emailT = arrayEmail.get(i);
        Toast.makeText(getApplicationContext(), "" + arrayEmail.get(i), Toast.LENGTH_SHORT).show();

    Intent intent = new Intent(FavouritesList.this, FavListViewActivity.class);
    intent.putExtra("email", emailT);
    startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
