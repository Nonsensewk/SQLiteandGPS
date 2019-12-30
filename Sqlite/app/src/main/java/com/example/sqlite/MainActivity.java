package com.example.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    public String name = "Jerry(解密后)";
    public String password = "123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SHA sha = new SHA();
       // sha.addPwd(name,password);
        database=new Database(MainActivity.this,"TEST.db",null,1).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("password","123456");
       long result = 0;


       result = database.insert("test01",null,values);

       Log.i("info", String.valueOf(result));



       SQLiteStudioService.instance().start(this);

    }

}
