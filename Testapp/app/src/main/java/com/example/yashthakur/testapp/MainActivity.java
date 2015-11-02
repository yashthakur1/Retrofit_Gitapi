package com.example.yashthakur.testapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashthakur.testapp.API.gitapi;
import com.example.yashthakur.testapp.MODEL.gitmodel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    String API = "https://api.github.com";// your base api url

    //variable declaration including view items
    Button test;
    EditText username;
    TextView tv;
    ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking ui elements with .java
        test = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.editText);
        tv=(TextView) findViewById(R.id.tv);
        pbar=(ProgressBar) findViewById(R.id.progressBar);

        test.setOnClickListener(new OnClickListener() {
            //onclick
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();

                RestAdapter restadapter =new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(API).build();

                gitapi git= restadapter.create(gitapi.class);
                git.getFeed(user, new Callback<gitmodel>() {
                    @Override
                    public void success(gitmodel gitmodel, Response response) {
                        Log.d("Response:" + response, "success");
                        //tv.setText("done");
                        tv.setText("Github Name :"+gitmodel.getName()+"\n"+"Repos:"+gitmodel.getPublicRepos());
                        pbar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //throws a toast message when retreiving fails
                        Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
                        tv.setText(error.getMessage());
                        pbar.setVisibility(View.INVISIBLE);
                        Log.d("error:"+error,"failure");

                    }
                });



            }
        });

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
}
