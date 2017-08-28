package no.hit.a107733.eksamen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegTurActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    EditText ET_NAVN, ET_BESKRIVELSE, ET_REGAV, ET_LENGDE;
    Spinner ET_TYPE;
    SeekBar ET_MOH;
    String Navn, Type, Beskrivelse, Lengdegrad, Breddegrad, Moh, Regav, Lengde;





    TextView Mohtext;
    TextView ET_BREDDEGRAD = null;
    TextView ET_LENGDEGRAD = null;


    private Location myLocation = null;

    // Klient som kommuniserer med Google Play API
    private GoogleApiClient mGoogleApiClient = null;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_tur);

        ET_NAVN = (EditText) findViewById(R.id.Navn);
        ET_TYPE = (Spinner) findViewById(R.id.Type);
        ET_BESKRIVELSE = (EditText) findViewById(R.id.Beskrivelse);
        ET_LENGDE = (EditText) findViewById(R.id.Lengde) ;
        ET_BREDDEGRAD   = (TextView) findViewById(R.id.breddeGrad);
        ET_LENGDEGRAD   = (TextView) findViewById(R.id.lengdeGrad);
        ET_MOH = (SeekBar) findViewById(R.id.seekBar);
        Mohtext = (TextView) findViewById(R.id.mohText);
        ET_REGAV = (EditText) findViewById(R.id.Regav);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            GoogleApiClient.Builder apiBuilder = new GoogleApiClient.Builder(this);
            apiBuilder.addConnectionCallbacks(this);        /* ConnectionCallbacks-objekt */
            apiBuilder.addOnConnectionFailedListener(this); /* OnConnectionFailedListener-objekt */
            apiBuilder.addApi(LocationServices.API);        /* Velg Play Service API */
            mGoogleApiClient = apiBuilder.build();
        }

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilbakeKnapp(v);
            }
        });



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_TYPE.setAdapter(adapter);

        seekbar();

    }




    // tilbakeknapp
    public void tilbakeKnapp(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public void seekbar(){
        ET_MOH = (SeekBar) findViewById(R.id.seekBar);
        Mohtext = (TextView) findViewById(R.id.mohText);
        Mohtext.setText("Meter over havet: " + ET_MOH.getProgress() + " / " + ET_MOH.getMax());

        ET_MOH.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        Mohtext.setText("Meter over havet: " + progress);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {


                    }
                }
        );
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        visPosisjon(myLocation);
    }
    @Override
    protected void onPause() {
        if (mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mRequestingLocationUpdates = false;
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mRequestingLocationUpdates=true;
            } catch (SecurityException e) { e.printStackTrace(); }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Connect the ApiClient to Google Services
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnect the ApiClient
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public final static int REQUEST_LOCATION = 1;

    @Override
    public void onConnected(Bundle connectionHint) {
        // Denne fungerer også før API 23 med AppCompatActivity:
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        // Denne fungerer bare fra og med  API 23 med Activity:
        //int permissionCheck = this.getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getPackageName());
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            //** Spør bruker om å gi appen tillatelsen ACCESS_FINE_LOCATION
            // Denne fungerer også før API 23 med AppCompatActivity:
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            // Denne fungerer bare fra og med  API 23 med Activity:
            //this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // OK: Appen har tillatelsen ACCESS_FINE_LOCATION. Finn siste posisjon
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mRequestingLocationUpdates=true;
        }
    }





    @Override
    public void onConnectionFailed(ConnectionResult result) {
      
        Toast.makeText(getApplicationContext(),
                "Klarte ikke å koble til Google Play Services", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    public void visPosisjon(Location posisjon) {
        if (posisjon != null) {

            String breddeGradString = Location.convert(posisjon.getLatitude(), Location.FORMAT_DEGREES);
            String lengdeGradString = Location.convert(posisjon.getLongitude(), Location.FORMAT_DEGREES);


            ET_BREDDEGRAD.setText(breddeGradString + " Øst");
            ET_LENGDEGRAD.setText(lengdeGradString + " Nord");

        }
    }

    public void visIKart(View v) {
        if (myLocation != null) {
            String geoURL = "geo:" + myLocation.getLatitude() + ","
                    + myLocation.getLongitude() + "?z=14" ;
            Uri geoURI = Uri.parse(geoURL);
            Intent geoMap = new Intent(Intent.ACTION_VIEW, geoURI);
            startActivity(geoMap);
        }
    }
    public void turReg(View view) {
        startActivity(new Intent(this,RegTurActivity.class));

        Navn = ET_NAVN.getText().toString();
        Type = ET_TYPE.getSelectedItem().toString();

        Beskrivelse = ET_BESKRIVELSE.getText().toString();
        Lengde = ET_LENGDE.getText().toString();
        Breddegrad = ET_BREDDEGRAD.getText().toString();
        Lengdegrad = ET_LENGDEGRAD.getText().toString();
        Moh = String.valueOf(ET_MOH.getProgress());
        Regav = ET_REGAV.getText().toString();



        String method = "register";
        RegTur RegTur = new RegTur(this);
        RegTur.execute(method, Navn, Type, Beskrivelse, Lengde, Breddegrad, Lengdegrad, Moh, Regav);

    }

    // Callbackmetode som kalles etter at bruker har svart på spørsmål om rettigheter
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    this.visPosisjon(myLocation);
                }
                catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
              
                Toast.makeText(getApplicationContext(),
                        "Kan ikke vise posisjon uten tillatelse", Toast.LENGTH_LONG).show();
            }
        }
    }


}
