package com.example.yashthakur.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashthakur.testapp.api.gitapi;
import com.example.yashthakur.testapp.model.gitmodel;

import com.example.yashthakur.testapp.utils.Book;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    String API = "https://api.github.com";// your base api url

    //variable declaration including view items
    Button sugar;

    Button detailbtn;
    EditText username;
    TextView tv;
    ProgressBar pbar;
    ListView listView;

    private Context context;


    @Bind(R.id.textview)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        ButterKnife.bind(this);

        setupUI(findViewById(R.id.parent));
        //  Typeface face = Typeface.createFromAsset(getAssets(), "font/Roboto-Black.ttf");

        // linking ui elements with .java

        username = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.tv);
        pbar = (ProgressBar) findViewById(R.id.progressbar);

        detailbtn = (Button) findViewById(R.id.detailbtn);
        sugar = (Button) findViewById(R.id.sugarbtn);

        //tv.setTypeface(face);
        final Intent dintent = new Intent(context, Details.class);
        final Book book = new Book("Title 1", "Edition 1");
        book.save();
       /* List<Book> list = Book.listAll(Book.class);
        ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(MainActivity.this, android.R.layout.simple_list_item_1, Integer.parseInt(list.toString()));

        listView.setAdapter(adapter);*/
        sugar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Book book=Book.findWithQuery(Book.class, "Select DISTINCT from Book Where id=?", "1");
                // Book book = Book.findById(Book.class, 1L);
                Log.d("book: ", "book string "+ book.title );//+ Book.findWithQuery(Book.class, "select * from Book where id = 1", "1").toString()
                title.setText(book.toString());

                pbar.setVisibility(View.INVISIBLE);// just to reset the pbar (remove this code later in final build)
                Toast.makeText(getApplicationContext(), "Data stored", Toast.LENGTH_LONG).show();


            }
        });

        detailbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();

                RestAdapter restadapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint(API).build();

                gitapi git = restadapter.create(gitapi.class);
                git.getFeed(user, new Callback<gitmodel>() {
                    @Override
                    public void success(gitmodel gitmodel, Response response) {
                        Log.d("Response:" + response, "success");
                        //tv.setText("done");
                        dintent.putExtra("contentcall", "Github Name :" + gitmodel.getName().toString() + "\n" + "Repos:" + gitmodel.getPublicRepos().toString());

                        pbar.setVisibility(View.VISIBLE);
                        MainActivity.this.startActivity(dintent);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //throws a toast message when retreiving fails
                        Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
//                        tv.setText(error.getMessage());
                        title.setText(error.getMessage());
//                        pbar.setVisibility(View.INVISIBLE);
                        Log.d("error:" + error, "failure");

                    }
                });
            }
        });

    }

    //below is a method to hide softkeyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //below is the method to hide keyboard on view touch other than edit text
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    @OnClick(R.id.butterbtn)
    public void clicky() {
        Toast.makeText(MainActivity.this, "butterknife successfull", Toast.LENGTH_LONG).show();
        title.setText("butter successful");
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
