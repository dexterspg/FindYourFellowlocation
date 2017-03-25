package com.example.ankit.findyourfellow;

import android.*;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class LocationHelper extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    protected GoogleApiClient mGoogleApiClient;

    final private int REQUEST_LOCATION_CODE_ASK_PERMISSION = 1;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private String mLastUpdateTime;


    public LocationHelper(Context context){
                        }
    protected void buildGoogleApiClient() {

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void permissionToGoogleServices(){

            connectGoogleAPI();
            createLocationRequest();
            buildGoogleApiClient();

    }


    public void terminateConnection(){

        stopLocationUpdates();
        disconnectGoogleAPI();
    }

    public void connectGoogleAPI(){

        mGoogleApiClient.connect();
           }

    public void disconnectGoogleAPI(){

        mGoogleApiClient.disconnect();
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


    @Override
    public void onLocationChanged(Location location) { //called when location has changed
        mCurrentLocation = location;
        mLastUpdateTime = getTimeWhenLocationIsAccessed();
        // updateUI();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_CODE_ASK_PERMISSION);
        }
        else
            fusedLocationProviderApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE_ASK_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    Toast.makeText(this, "Permission Accepted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    //////Connection Setup//////////////////////////////////////////

    public void connectToGPS() {

        final LocationManager GPSsetup = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //check if GPS is enabled
        if (!GPSsetup.isProviderEnabled(LocationManager.GPS_PROVIDER) ){
            buildAlertMessageNoGps();
        }
         }

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isConnectedtoGoogleAPI() {
        if (!mGoogleApiClient.isConnected())
            return true;
        else return false;
    }


    public boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, status, 0);
            dialog.show();
            return false;
        }
    }

//////////////GET METHODS ///////////////////////////////////////
    public double getLatitude(){
        return(mCurrentLocation.getLatitude());
    }
    public double getLongitude(){
        return (mCurrentLocation.getLatitude());
    }
    public String getTimeWhenLocationIsAccessed(){ return(DateFormat.getTimeInstance().format(new Date()));
    }



}
