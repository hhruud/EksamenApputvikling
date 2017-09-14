package no.hit.a107733.eksamen;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public final static String ENDPOINT =
            "http://itfag.usn.no/~107733/api.php";
    public final static String turer_URI =
            ENDPOINT + "/Turer?order=Navn,asc&transform=1";

    private ArrayList<Turer> turerArrayList = new ArrayList<Turer>();
    private ArrayAdapter<Turer> turerArrayAdapter = null;
    private ListView turListen;



    Turer entur = new Turer();
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        turListen = (ListView) findViewById(R.id.test1);
        turListen.setAdapter(turerArrayAdapter);


        turListen.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entur = turerArrayAdapter.getItem(position);
                setContentView(R.layout.tur_detalj);
                final TextView navnid = (TextView) findViewById(R.id.NavnId);
                String navn = entur.navn;
                navnid.setText(navn);
                final TextView beskrivelseid = (TextView) findViewById(R.id.beskrivelse);
                String beskrivelse = entur.beskrivelse;
                beskrivelseid.setText(beskrivelse);




                FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.fab1);
                myFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tilbakeKnapp1(v);
                    }
                });




            }
        } );

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilRegKnapp(v);
            }
        });



        lesAlleTurerAsynkront();
        //oppdaterTurListView(TurArrayList);
    }


    // tilbakeknapp
    public void tilbakeKnapp1(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // tilbakeknapp
    public void tilRegKnapp(View view){
        Intent i = new Intent(this, RegTurActivity.class);
        startActivity(i);
    }

    // Henter alle turer fra REST-tjeneste
    public void lesAlleTurerAsynkront() {
        if (isOnline()){
            LastTurListe turListeLaster = new LastTurListe();
            turListeLaster.execute();
        }else{
            Toast.makeText(this, "Ingen nettverkstilgang. Kan ikke laste turer.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    // Bygger nytt adapter og oppdaterer ListViewet
    public void oppdaterTurListView(ArrayList<Turer> nyTurListe) {
        turerArrayAdapter = new ArrayAdapter<Turer>(this, android.R.layout.simple_list_item_1, nyTurListe);
        turListen.setAdapter(turerArrayAdapter);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Legger til markør på kartet
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    // Indre asynkron klasse som henter alle varer fra databasen
    private class LastTurListe extends AsyncTask<String , String , Long > {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL turListeURL = new URL(turer_URI);
                connection = (HttpURLConnection) turListeURL.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("HTTP GET", "status " + status);
                if (status == HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String turData = sb.toString();
                    turerArrayList = Turer.lagTurListe(turData);
                    Log.d("HTTP GET response", turData);
                    return (0l);
                }else{
                    return (1l);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return (1l);
            }
            catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
                return (1l);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return (1l);
            } catch (Exception e) {
                e.printStackTrace();
                return (1l);
            } finally {
                connection.disconnect();
            }
        }  

        @Override
        protected void onPostExecute(Long result) {
            if (result==0){
                // Oppdaterer GUIet etter at alle turer er lest fra databasen
                oppdaterTurListView(turerArrayList);
            }else{
                Toast.makeText(getParent(),
                        "Noe gikk galt under lasting av turer fra database.", Toast.LENGTH_SHORT).show();
            }
        }  
    }



    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
