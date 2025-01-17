package com.pieroyendilotto.testyendilotto;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String url = "https://randomuser.me/api/?results=50";
    public static String data;
    public ArrayList<Person> persons;
    private PersonAdapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getXML();

        Search();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////


    public void getXML(){

        listview = (ListView) findViewById(R.id.listview);

    }

    public void Search() {
        //CONSULTA LA API Y DEVUELVE EL CONTENIDO:


        if (isOnline()) {

            new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... params) {
                    StringBuilder content = new StringBuilder();
                    try {
                        URL u = new URL(url);
                        HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                        if (uc.getResponseCode() == HttpURLConnection.HTTP_OK) {

                            InputStream is = uc.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                            String line;
                            while ((line = br.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        } else {

                            throw new IOException(uc.getResponseMessage());
                        }
                    } catch (StackOverflowError | Exception s) {
                        //INFORMAR LOS ERRORES AL PRESENTER
                        s.printStackTrace();
                    } catch (Error e) {
                        e.printStackTrace();
                    }

                    return content.toString();
                }

                @Override
                protected void onPostExecute(String aVoid) {
                    super.onPostExecute(aVoid);
                    //PARSEO EL RESULTADO:
                    Log.v("POSTEXE","RESULTADO: "+aVoid);
                    Log.v("POSTEXE","RESULTADO PARSE: "+parseJSON(aVoid));
                    Main2Activity.persons = parseJSON(aVoid);
                    adapter = new PersonAdapter(MainActivity.this, parseJSON(aVoid));
                    listview.setAdapter(adapter);


                }
            }.execute();

        }//if is online
        else
        {
            Log.v("PIERO","NO HAY CONEXION.");

            Toast toast1 = Toast.makeText(getApplicationContext(),
                    getString(R.string.no_connection), Toast.LENGTH_LONG);

            toast1.show();

        }

    }

    //////////////////////////////////////////////////////////////////////

    public ArrayList<Person> parseJSON(String result) {

        Log.v("PIERO","RESULT parse: "+result);

        ArrayList<Person> persons = new ArrayList<Person>();

        JSONObject jObj = null;
        try {
            jObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArry = null;

            jsonArry = jObj.getJSONArray("results");

            Log.v("PIERO","lenght: "+jsonArry.length());

            for(int i=0;i<jsonArry.length();i++){
                Person person = new Person(jObj.getJSONArray("results").getJSONObject(i).getJSONObject("picture").getString("thumbnail"),
                                           jObj.getJSONArray("results").getJSONObject(i).getJSONObject("picture").getString("large"),
                                           jObj.getJSONArray("results").getJSONObject(i).getJSONObject("login").getString("username"),
                                           jObj.getJSONArray("results").getJSONObject(i).getJSONObject("name").getString("first"),
                                           jObj.getJSONArray("results").getJSONObject(i).getJSONObject("name").getString("last"),
                                           jObj.getJSONArray("results").getJSONObject(i).getString("email")
                                           );


                persons.add(person);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return persons;

    }

    //////////////////////////////////////////////////////////////////////

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    //////////////////////////////////////////////////////////////////////

}
