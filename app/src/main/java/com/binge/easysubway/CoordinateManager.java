package com.binge.easysubway;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Binge on 2017/9/4.
 */

public class CoordinateManager {
    private static final String TAG = "CoordinateManager";
    private Context mContext;
    private static CoordinateManager manager;
    private LocationManager mLocationManager;

    public static CoordinateManager getInstance() {
        if (manager == null) {
            manager = new CoordinateManager();
        }
        return manager;
    }

    public void init(Context context) {
        mContext = context;
        if (mContext != null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }
    }


    public String[] getCoordinate() {
        String[] str = new String[2];
        if (mLocationManager != null) {
            String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
            Log.d(TAG, "getCoordinate: "+bestProvider);
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // TODO: 2017/9/4 请求权限
            } else {
                Location lastKnownLocation = mLocationManager.getLastKnownLocation(bestProvider);
                str[0] = String.valueOf(lastKnownLocation.getLongitude());
                str[1] = String.valueOf(lastKnownLocation.getLatitude());
            }

        }
        return str;
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(false);// 海拔要求
        criteria.setBearingRequired(true);// 方位要求
        criteria.setCostAllowed(true);// 允许有花费
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);// 低功耗

        return criteria;
    }
}
