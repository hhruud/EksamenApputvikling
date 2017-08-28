package no.hit.a107733.eksamen;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Håkon on 31.05.2017.
 */

public class RegTur extends AsyncTask<String, Void, String> {

    Context ctx;
    RegTur(Context ctx) {
        this.ctx=ctx;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://itfag.usn.no/~107733/api.php/Turer";
        String method = params[0];
        if(method.equals("register")) {
            String Navn = params[1];
            String Type = params[2];
            String Beskrivelse = params[3];
            String Lengde = params[4];
            String Breddegrad = params[5];
            String Lengdegrad = params[6];
            String Moh = params[7];
            String Regav = params[8];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("Navn","UTF-8")+"="+URLEncoder.encode(Navn,"UTF-8")+"&"+
                              URLEncoder.encode("Type","UTF-8")+"="+URLEncoder.encode(Type,"UTF-8")+"&"+
                              URLEncoder.encode("Beskrivelse","UTF-8")+"="+URLEncoder.encode(Beskrivelse,"UTF-8")+"&"+
                              URLEncoder.encode("Lengde","UTF-8")+"="+URLEncoder.encode(Lengde,"UTF-8")+"&"+
                              URLEncoder.encode("Breddegrad","UTF-8")+"="+URLEncoder.encode(Breddegrad,"UTF-8")+"&"+
                              URLEncoder.encode("Lengdegrad","UTF-8")+"="+URLEncoder.encode(Lengdegrad,"UTF-8")+"&"+
                              URLEncoder.encode("Moh","UTF-8")+"="+URLEncoder.encode(Moh,"UTF-8")+"&"+
                              URLEncoder.encode("Regav", "UTF-8")+"="+URLEncoder.encode(Regav,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registrering Fullført";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }
}
