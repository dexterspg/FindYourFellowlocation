package com.example.ankit.findyourfellow;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class testLocationActivity extends AppCompatActivity {


    TextView latitude;
    TextView longitude;
    TextView timeLocation;
    Button showLocation;

    private LocationHelper location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        latitude = (TextView) findViewById(R.id.Latitude);
        longitude = (TextView) findViewById(R.id.Longitude);
        showLocation = (Button) findViewById(R.id.locationButton);
        timeLocation = (TextView) findViewById(R.id.timeTextView);

        location = new LocationHelper(testLocationActivity.this);
        location.permissionToGoogleServices();

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                location.connectToGPS();
                updateLocation(location);

        }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

           }

    @Override
    public void onResume() {
        super.onResume();
        updateLocation(location);
    }

    @Override
    protected void onPause() {
        super.onPause();
        location.stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();

       location.terminateConnection();

    }

    public void updateLocation(LocationHelper location){

        if (null != location) {

            latitude.setText(String.valueOf(location.getLatitude()));
            longitude.setText(String.valueOf( location.getLongitude()));
            timeLocation.setText(String.valueOf(location.getTimeWhenLocationIsAccessed()));

            Toast.makeText(this, "Location Updated", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Location is NULL", Toast.LENGTH_SHORT).show();
        }


    }
}


