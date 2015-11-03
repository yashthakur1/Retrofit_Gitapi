package com.example.yashthakur.testapp.utils;

import android.util.Log;

import com.orm.SugarRecord;

/**
 * Created by yashthakur on 11/3/15.
 */
public class Book extends SugarRecord<Book>{

   public String title;
   public String edition;
    public Book()
    {

        Log.d("constructor is active","Constructor");


    }


    public Book(String title, String edition) {
        this.title = title;
        this.edition = edition;
       /* String queryexecuted = Book.findWithQuery(Book.class, "Select DISTINCT from Book where title = Title 1").toString();
        Log.d(queryexecuted,"show query");*/
    }

    @Override
    public void save() {
        super.save();

        Log.v("Book","Save");

    }
}
