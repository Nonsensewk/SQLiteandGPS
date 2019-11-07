package com.example.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocationDatabase extends SQLiteOpenHelper {

    // 数据库版本号
    private static final int DATABASE_VERSION = 1;
    // 数据库名
    private static final String DATABASE_NAME = "GPS01.db";
    // 数据表名
    public static final String TABLE_NAME = "LocationHistory";

    private static LocationDatabase locationDatabase = null;


  //构造方法
    public LocationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

   //获取数据库实例
    public static LocationDatabase getInstance(Context context) {

        if (locationDatabase == null) {

            locationDatabase = new LocationDatabase(context);

            Log.i("建立数据库=======》》》》", "获取数据库实例");
        }
        return locationDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("CREATE TABLE IF NOT EXISTS [" + TABLE_NAME + "] (");
        sBuffer.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");// id
        sBuffer.append("Time VARCHAR2(20),");// 时间
        sBuffer.append("Longitude VARCHAR2(200), ");// 经度
        sBuffer.append("Latitude VARCHAR2(200),");// 纬度
        sBuffer.append("Battery VARCHAR2(20))");// 纬度

        // 执行创建表的SQL语句
        db.execSQL(sBuffer.toString());
        // 即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
        Log.i("创建信息表", "=============>>>>创建操作信息表");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public ContentValues getCv(String time, double longitude, double latitude, String battery) {
        ContentValues cv = new ContentValues();
        cv.put("Time", time);
        cv.put("Longitude", longitude);
        cv.put("Latitude", latitude);
        cv.put("Battery", battery);
        return cv;
    }

   //插入数据
    public void Insert(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

}
