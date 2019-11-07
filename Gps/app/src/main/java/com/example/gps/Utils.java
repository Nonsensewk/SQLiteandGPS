package com.example.gps;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.location.Criteria;

public class Utils {

	
//时间
	public static String formatDate(Long date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd hh:mm:ss");
		return dateFormat.format(date);
	}
	
	
	public static Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//精准查询
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}
	
	
}
