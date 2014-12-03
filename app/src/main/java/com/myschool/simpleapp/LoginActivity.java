package com.myschool.simpleapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myschool.SimpleApp.R;

import org.json.JSONObject;


public class LoginActivity extends Activity implements OnClickListener {
    Button button;
    public static String TAG = "xxxxxxxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO if logged then start main activity and finish this one
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.act_login_button_go);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.act_login_button_go) {
            Log.d(TAG, "Je suis passé par le test du onClick");
            String email = ((EditText) findViewById(R.id.act_login_email)).getText().toString();
            String passwd = ((EditText) findViewById(R.id.act_login_password)).getText().toString();
            loginRequest(email, passwd);
            ///startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void loginRequest(String email, String passwd) {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        // TODO Vérif de la réponse du serveur. Si ok on démarrre l'activité suivante.
                        // Si pas ok envoi d'un toast d'erreur
                        Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
