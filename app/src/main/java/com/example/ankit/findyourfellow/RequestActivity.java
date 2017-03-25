package com.example.ankit.findyourfellow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ArrayList<String> allRequests = new ArrayList<String>();
    private ArrayList<String> allKeys = new ArrayList<String>();
    private ListView requestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Friend Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        requestView = (ListView) findViewById(R.id.requestList);




        final RequestAdapter adapter = new RequestAdapter(getApplicationContext(), R.layout.list_item);


        /*
        final ArrayAdapter<String> requestAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allRequests);
        */
        requestView.setAdapter(adapter);


        String thisUser = mAuth.getCurrentUser().getUid().toString();

        Firebase requestRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + thisUser + "/FriendRequest");

        //allRequests.add(thisUser);

        requestRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String friend = dataSnapshot.getValue(String.class);
                String id = dataSnapshot.getKey();

                //allKeys.add(id);
                adapter.add(friend, id);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                goToAccountActivity();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    void goToAccountActivity()
    {
        Intent intent = new Intent(RequestActivity.this, AccountActivity.class);
        Toast.makeText(getApplicationContext(), "Friend Managed", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    private void userSignOut()
    {
        mAuth.signOut();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //manage what happens when options on the toolbar are clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.signout:
                userSignOut();
                startActivity(new Intent(RequestActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
