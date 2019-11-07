package com.example.gps;

import java.util.Iterator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Button start_btn, close_btn;
    private TextView Longitude, Latitude, time_tv, location_state_tv;
    private LocationManager lm;
    private LocationDatabase locationDatabase;
    private BatteryStateReciver receiver;
    private static final String TAG = "GpsActivity";

    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        registerBatteryStateReceiver();
        initView();
        info.setMovementMethod(ScrollingMovementMethod.getInstance());
        setListener();

    }

    private void setListener() {
        start_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);

    }

    private void initView() {

        locationDatabase = LocationDatabase.getInstance(this);// 获取数据库实例
        start_btn =  findViewById(R.id.start_btn);
        close_btn =  findViewById(R.id.close_btn);
        Longitude =  findViewById(R.id.longitude_tv);
        Latitude =  findViewById(R.id.latitude_tv);
        time_tv =  findViewById(R.id.time_tv);
        location_state_tv =  findViewById(R.id.location_state_tv);
        info = findViewById(R.id.tv_location);
    }
//观察手机电量
    private void registerBatteryStateReceiver() {
        receiver = new BatteryStateReciver();
        //intentFilter 获取组件信息
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

        Log.i("电池状态读取", "读取成功");
    }


    private void getLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// 获取定位服务
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {// GPS未开启
            Toast.makeText(getApplicationContext(), "请开启GPS", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//未开启  跳转设置界面
            startActivityForResult(intent, 0);
            return;
        }

        // 为获取地理位置信息时设置查询条件
        String bestProvider = lm.getBestProvider(Utils.getCriteria(), true);

        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(bestProvider);

        updateView(location);

        lm.addGpsStatusListener(listener);

        Log.i(TAG, "GPS状态监听");

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1*1000, 0, locationListener);
        location_state_tv.setText("定位已开启");
    }

    // 位置监听
    private LocationListener locationListener = new LocationListener() {
        //位置变化时触发
        public void onLocationChanged(Location location) {

            updateView(location);
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
        }

        //gps状态触发
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");

                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");

                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");

                    break;
            }
        }

       //开启GPS
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "gps开启");
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = lm.getLastKnownLocation(provider);
            updateView(location);
        }

       //gps被禁用
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "gps禁用");
            updateView(null);
        }
    };

    // 状态监听
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i(TAG, "第一次定位");
                    break;


                // 卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                     Log.i(TAG, "卫星状态改变");

                    // 获取当前状态
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GpsStatus gpsStatus = lm.getGpsStatus(null);
                    // 获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    // 创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;

                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                     Log.i(TAG, "共搜索到"+count+"颗卫星");
                    break;
                // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.i(TAG, "定位启动");
                    break;
                // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i(TAG, "定位结束");
                    break;
            }
        }
    };

   //更新信息
    private void updateView(Location location) {
        if (location != null) {
            String time = String.valueOf(Utils.formatDate(location.getTime()));
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            String currBattery = Constants.currLevel + "%";
            locationDatabase.Insert(locationDatabase.getCv(time, longitude, latitude, currBattery));

            Log.i(TAG, "数据插入SQlite数据库成功！");

            time_tv.setText(time);
            Longitude.setText(String.valueOf(longitude));
            Latitude.setText(String.valueOf(latitude));

            info.append("精度："+longitude+"纬度"+latitude+"时间"+time+"\n");

        }
        else {
            Latitude.setText("暂未获取到坐标");
        }
    }


    public void unRegisterLocationChangeListener() {

        Log.i(TAG, "取消定位");

        if (lm != null) {
            lm.removeUpdates(locationListener);
            location_state_tv.setText("定位已关闭");
        } else {
            location_state_tv.setText("定位未开启，无法关闭");
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                getLocation();
                break;

            case R.id.close_btn:
                unRegisterLocationChangeListener();
                break;
        }

    }
}
