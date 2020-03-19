package com.tutors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tutors.Adapter.MyCustomAdapter;
import com.tutors.Adapter.MyCustomAdapter2;
import com.tutors.Fragments.ChangePassword;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.tutors.R.id.classes;


public class TutorsListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = TutorsListActivity.class.getSimpleName();
    Toolbar mtutorsListToolbar;
    Spinner mSubjectSpinner, mClassesSpinner;
    ListView myList;
    Firebase mFirebase, firebase;
    // ImageView mImageT;
    String uName;

    private ArrayList<String> arrayNames = new ArrayList<String>();
    private ArrayList<String> arrayEq = new ArrayList<String>();
    private ArrayList<String> arraySubject = new ArrayList<String>();
    private ArrayList<String> arrayImage = new ArrayList<String>();
    private ArrayList<String> arrayEmail = new ArrayList<String>();

    final ArrayList<String> arrayEmailSpinner = new ArrayList<String>();

    final ArrayList<String> arrayEmailSpinnerClass = new ArrayList<String>();

    String spinnerValue, userEmail, userUid;
    int total=0;

    private String[] subjects;
    private String[] classes;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_list);

        mtutorsListToolbar = (Toolbar) findViewById(R.id.tutorsListToolbar);

        myList = (ListView) findViewById(R.id.tutorListView);
        mFirebase = new Firebase("https://tutors-372f4.firebaseio.com/tutors");

        setSupportActionBar(mtutorsListToolbar);
        getSupportActionBar().setTitle("Tutors List");
        getSupportActionBar().setIcon(R.drawable.tutors);

        mSubjectSpinner = (Spinner) findViewById(R.id.spinnerSubjects);
        mClassesSpinner = (Spinner) findViewById(R.id.spinnerClasses);
        //mImageT = (ImageView) findViewById(R.id.profile_image);

        mSubjectSpinner.setOnItemSelectedListener(this);
        mClassesSpinner.setOnItemSelectedListener(this);

        subjects = getResources().getStringArray(R.array.Subjects_array);
        classes = getResources().getStringArray(R.array.classes_array);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Subjects_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mSubjectSpinner.setAdapter(adapter);
        mSubjectSpinner.setPrompt("Subjects");


        ArrayAdapter<CharSequence> adapter_classes = ArrayAdapter.createFromResource(this,
                R.array.classes_array, android.R.layout.simple_spinner_item);

        adapter_classes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mClassesSpinner.setAdapter(adapter_classes);
        mClassesSpinner.setPrompt("Classes");
        Stetho.initializeWithDefaults(this);
        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Information information = childSnapshot.getValue(Information.class);

                    String name = information.getName();
                    String eq = information.getEducationQualification();
                    String sub = information.getSubjectCanTeach();
                    String image = information.getImage();
                    String email = information.getEmail();
                    // Log.e("name",name);
                    // Log.e("Link",information.getImage());

                    arrayNames.add(name);
                    arrayEq.add(eq);
                    arraySubject.add(sub);
                    arrayImage.add(image);
                    arrayEmail.add(email);


                }
                if (arrayNames.size() > 0) {
                    final MyCustomAdapter myadapter = new MyCustomAdapter(TutorsListActivity.this,
                            arrayNames, arrayEq, arraySubject, arrayImage);

                    myList.setAdapter(myadapter);
                    myList.setOnItemClickListener(TutorsListActivity.this);
                    myadapter.notifyDataSetChanged();
                } else {

                    final MyCustomAdapter2 myadapter2 = new MyCustomAdapter2(TutorsListActivity.this,
                            arrayNames, arrayEq, arraySubject);


                    myList.setAdapter(myadapter2);
                    myList.setOnItemClickListener(TutorsListActivity.this);
                    myadapter2.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.passwordC) {
            ChangePassword changePassword = new ChangePassword();
            changePassword.show(getSupportFragmentManager(), "Change password");
            return true;
        }

        if (id == R.id.signOut) {

            FirebaseAuth.getInstance().signOut();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Key", "");
            editor.apply();

            startActivity(new Intent(TutorsListActivity.this, MainActivity.class));
            finish();
        }
        if (id == R.id.post) {
            Intent intent = new Intent(TutorsListActivity.this, UserTimeline.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.favourites) {
            Intent intent = new Intent(TutorsListActivity.this,FavouritesList.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String email;
        if(arrayEmailSpinner.size()>0){
             email = arrayEmailSpinner.get(i);
            Toast.makeText(getApplicationContext(), "" + arrayEmailSpinner.get(i), Toast.LENGTH_SHORT).show();

        }
        else{
             email = arrayEmail.get(i);
            Toast.makeText(getApplicationContext(), "" + arrayEmail.get(i), Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent(TutorsListActivity.this, ListItemView.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              final ArrayList<String> arrayNamesSpinner = new ArrayList<String>();
        final ArrayList<String> arrayEqSpinner = new ArrayList<String>();
        final ArrayList<String> arraySubjectSpinner = new ArrayList<String>();
        final ArrayList<String> arrayImageSpinner = new ArrayList<String>();


        final ArrayList<String> arrayNamesSpinnerClass = new ArrayList<String>();
        final ArrayList<String> arrayEqSpinnerClass = new ArrayList<String>();
        final ArrayList<String> arraySubjectSpinnerClass = new ArrayList<String>();
        final ArrayList<String> arrayImageSpinnerClass = new ArrayList<String>();



        arrayEmailSpinner.clear();
        if (parent.getId() == R.id.spinnerSubjects) {



            Log.e(TAG, subjects[position].toString());
            spinnerValue = subjects[position].toString();
            Toast.makeText(TutorsListActivity.this, spinnerValue, Toast.LENGTH_SHORT).show();

            if(!spinnerValue.equals("Subjects")){
                firebase = new Firebase("https://tutors-372f4.firebaseio.com/tutors");
                firebase.orderByChild("subjectCanTeach").equalTo(spinnerValue).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Information information = childSnapshot.getValue(Information.class);

                            String name = information.getName();
                            String eq = information.getEducationQualification();
                            String sub = information.getSubjectCanTeach();
                            String image = information.getImage();
                            String email = information.getEmail();
                            // Log.e("name",name);
                            // Log.e("Link",information.getImage());

                            arrayNamesSpinner.add(name);
                            arrayEqSpinner.add(eq);
                            arraySubjectSpinner.add(sub);
                            arrayImageSpinner.add(image);
                            arrayEmailSpinner.add(email);


                            Log.e("Subject", sub);
                            Log.e("Name", name);

                            if(arrayEmailSpinner.size()>0){
                                final MyCustomAdapter myAdapter = new MyCustomAdapter(TutorsListActivity.this,
                                        arrayNamesSpinner, arrayEqSpinner, arraySubjectSpinner, arrayImageSpinner);

                                myList.setAdapter(myAdapter);
                                myList.setOnItemClickListener(TutorsListActivity.this);
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            else{
                final MyCustomAdapter myAdapter = new MyCustomAdapter(TutorsListActivity.this,
                        arrayNames, arrayEq, arraySubject, arrayImage);

                myList.setAdapter(myAdapter);
                myList.setOnItemClickListener(TutorsListActivity.this);
                myAdapter.notifyDataSetChanged();
            }


        } else if (parent.getId() == R.id.spinnerClasses) {

            Log.e(TAG, classes[position].toString());
            spinnerValue = classes[position].toString();
            Toast.makeText(TutorsListActivity.this, spinnerValue, Toast.LENGTH_SHORT).show();
            if(!spinnerValue.equals("Classes")){
                firebase = new Firebase("https://tutors-372f4.firebaseio.com/tutors");
                firebase.orderByChild("classesCanTeach").equalTo(spinnerValue).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Information information = childSnapshot.getValue(Information.class);

                            String name = information.getName();
                            String eq = information.getEducationQualification();
                            String sub = information.getSubjectCanTeach();
                            String image = information.getImage();
                            String email = information.getEmail();
                            // Log.e("name",name);
                            // Log.e("Link",information.getImage());

                            arrayNamesSpinnerClass.add(name);
                            arrayEqSpinnerClass.add(eq);
                            arraySubjectSpinnerClass.add(sub);
                            arrayImageSpinnerClass.add(image);
                            arrayEmailSpinner.add(email);


                            Log.e("Class", sub);
                            Log.e("Name", name);

                            if(arrayEmailSpinner.size()>0){
                                final MyCustomAdapter myAdapter = new MyCustomAdapter(TutorsListActivity.this,
                                        arrayNamesSpinnerClass, arrayEqSpinnerClass, arraySubjectSpinnerClass, arrayImageSpinnerClass);

                                myList.setAdapter(myAdapter);
                                myList.setOnItemClickListener(TutorsListActivity.this);
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            else{
                final MyCustomAdapter myAdapter = new MyCustomAdapter(TutorsListActivity.this,
                        arrayNames, arrayEq, arraySubject, arrayImage);

                myList.setAdapter(myAdapter);
                myList.setOnItemClickListener(TutorsListActivity.this);
                myAdapter.notifyDataSetChanged();
            }


        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
