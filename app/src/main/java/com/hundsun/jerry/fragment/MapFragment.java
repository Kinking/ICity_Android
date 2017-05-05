package com.hundsun.jerry.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.hundsun.jerry.R;
import com.hundsun.jerry.activity.OperatorActivity;


public class MapFragment extends Fragment {

    private static MapFragment fragment = null;
    public static final int POSITION = 0;

    private MapView mapView;
    private AMap aMap;
    private View mapLayout;

    public static Fragment newInstance() {
        if(fragment==null){
            synchronized (MapFragment.class){
                if(fragment==null){
                    fragment = new MapFragment();
                }
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mapLayout == null){
            Log.i("sys","MF onCreateView() null");
            mapLayout = inflater.inflate(R.layout.fragment_map,null);
            mapView = (MapView) mapLayout.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            if(aMap==null){
                aMap = mapView.getMap();
            }
        }else {
            if(mapLayout.getParent()!=null){
                ((ViewGroup)mapLayout.getParent()).removeView(mapLayout);
            }
        }
        return mapLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
