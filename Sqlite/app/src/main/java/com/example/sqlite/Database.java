package com.example.sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//注意：一定要继承SQLiteOpenHelper类
public class Database extends SQLiteOpenHelper {
    private int version;
    //创建表的语句
    private String CREATE_TABLE="CREATE TABLE test01 (id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(50),password varchar(50))";

    public Database(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version){

        super(context,dbName,factory,version);

        this.version=version;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行创建表，只执行一次
        sqLiteDatabase.execSQL(CREATE_TABLE);

        Log.d("onCreate", "被调用");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新使用的方法
    }
}
