package com.hundsun.jerry.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hundsun.jerry.R;
import com.hundsun.jerry.bean.Moment;
import com.hundsun.jerry.service.MapOperation;
import com.hundsun.jerry.util.json.JsonUtil;
import com.hundsun.jerry.util.json.WriteJson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapActivity extends Activity implements AMapLocationListener,LocationSource{

    private AMap aMap = null;  //定义地图对象
    private MapView mapView;  //一个用于显示地图的视图，从服务端获取数据，捕捉屏幕触控手势事件
    private Button button = null;
    private Button initBtn = null;
    private EditText et = null;

    double Latitude;
    double Longitude;


    String jsonMomentString=null;



    /**
     * 定位当前位置
     */
    private LocationSource.OnLocationChangedListener mListener = null;
    public AMapLocationClient mapLocationClient = null;
    public AMapLocationClientOption mapLocationClientOption = null;
    private AMapLocation aMapLocation=null;
    /*****/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        button= (Button) findViewById(R.id.bt_test2);
        initBtn= (Button) findViewById(R.id.bt_init);
        et = (EditText) findViewById(R.id.et_state);
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.map);
        //创建地图
        mapView.onCreate(savedInstanceState);
        init();
        aMap.setLocationSource((LocationSource) this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        //设置为true表示显示定位层并可触发定位,false表示隐藏定位层并不可触发定位,默认是false
        aMap.setMyLocationEnabled(true);
        //设置定位的类型为定位模式,可以有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//        markerInit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //设置一个表示经纬度地理位置的对象
                LatLng latLngLocation = new LatLng(Latitude,Longitude);
                LatLng latLngSH = new LatLng(31.270000,121.520000);
                String momentContent = et.getText().toString().trim();

                /**
                 * addMarker 在地图上添加一个Marker
                 * 相关参数：
                 *   1.MarkerOptions  设置marker覆盖物的锚点图标
                 *   2.position       设置放锚点的坐标
                 *   3.title          设置放锚点的标题文字
                 *   4.snippet        电表记得内容
                 *   5.draggable      点是否可拖拽
                 *   6.visible        点标记是否可见
                 *   7.anchor         点标记的锚点
                 *   8.alpha          点的透明度
                 */

                final Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLngSH).title("上海").snippet("DefaultMarker"));
                marker1.setVisible(true);
                marker1.showInfoWindow();


                final Marker marker = aMap.addMarker(new MarkerOptions().position(latLngLocation).snippet(momentContent));
                marker.setVisible(true);
                marker.showInfoWindow();


                try{

                    //获取当前时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String date = simpleDateFormat.format(new java.util.Date());
                    /**
                     * 接下来的逻辑要将moment转换为json字符串传送到服务器
                     */
                    String momentContentInner = et.getText().toString().trim();
                    Moment moment = new Moment(1,1,"卷",momentContentInner,date,Latitude,Longitude);
                    //构造一个moment对象
                    List<Moment> list = new ArrayList<Moment>();
                    list.add(moment);
                    WriteJson writeJson = new WriteJson();
                    jsonMomentString = writeJson.getJsonData(list);
                    System.out.println(jsonMomentString+"------------------------------");
                    /**
                     * 下面要写的是async-http框架负责发送请求的内容
                     */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MapOperation operation = new MapOperation();
                            String result = operation.uploadContent("MomentController",jsonMomentString);
                            Message msg = new Message();
                            msg.obj = result;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }catch (Exception e){
                    Toast.makeText(MapActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            /******************* Handler **************************/
            android.os.Handler handler=new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    try {

                        String msgobj=msg.obj.toString();
                        super.handleMessage(msg);

                    }catch (Exception e){
                        Toast.makeText(MapActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            };
        });

        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            markerInit();
                            Looper.loop();
                        }
                    }).start();

                }catch (Exception e){
                    Toast.makeText(MapActivity.this, "加载出现异常", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        });
    }


    //地图初始化函数
    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
        }
    }

    /**
     * 初始化地图上的所有marker
     */
    public void markerInit(){
        //定义请求字符串
        String request = "jsonMomentRequest";
        //创建异步请求
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
//       String url = "http://172.20.10.7:8080/ICity/GetMomentInfoController";   //本机测试url
        String url = "http://119.29.69.123:8080/ICity/GetMomentInfoController";   //云服务器url
        //请求的参数对象
        RequestParams params = new RequestParams();
        //将参数加入到参数对象中去
        params.put("jsonMomentRequest",request);
        //进行post请求
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //i表示状态码
                if(i == 200){
                    //从bytes中获取marker信息
                    String jsonMomentInfoList = new String(bytes);
//                   System.out.println(jsonMomentInfoList);
                    //测试是否收到了字符串
                    JsonUtil jsonUtil = new JsonUtil();
                    /*** 将收到的jsonString解析为List<Moment>***/
                    List<Moment> momentList = jsonUtil.momentStringFromJson(jsonMomentInfoList);
                    //测试是否将回来的Json字符串解析成功
                    System.out.println(momentList.get(0).getUserNickName()+":"+momentList.get(0).getMomentContent()+"\n"
                            +momentList.get(1).getUserNickName()+":"+momentList.get(1).getMomentContent()+"\n"
                            +momentList.get(2).getUserNickName()+":"+momentList.get(2).getMomentContent()+"\n"
                            +momentList.get(3).getUserNickName()+":"+momentList.get(3).getMomentContent()+"\n"
                            +momentList.get(4).getUserNickName()+":"+momentList.get(4).getMomentContent()+"\n"
                            +momentList.get(5).getUserNickName()+":"+momentList.get(5).getMomentContent()+"\n"
                            +momentList.get(6).getUserNickName()+":"+momentList.get(6).getMomentContent()+"\n"
                            +momentList.get(7).getUserNickName()+":"+momentList.get(7).getMomentContent()+"\n");
                    /***声明一个MarkerOptions类型数数组***/
                    MarkerOptions []mar = new MarkerOptions[momentList.size()];
                    for(int j=0;j<momentList.size();j++){
                        mar[j] = new MarkerOptions();
                    }
                    /***用for循环给每个MarkerOptions对象赋值***/
                    for(int j = 0;j<momentList.size();j++){
                        //获取list中对象的地理位置
                        LatLng lat = new LatLng(momentList.get(j).getLatitude(),momentList.get(j).getLongitude());
                        //给MarkerOptions数组赋地理位置值和moment内容
                        mar[j].position(lat).snippet(momentList.get(j).getMomentContent()).title(momentList.get(j).getUserNickName());
                    }
                    for(int j = 0;j<mar.length;j++){
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("MarkerOption",mar[j]);
                        message.setData(bundle);
                        handler1.sendMessage(message);//更新Map操作，发送消息到消息队列
                    }
                }

            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MapActivity.this,"网络超时，请稍后重试",Toast.LENGTH_LONG).show();
                throwable.printStackTrace();

            }
            android.os.Handler handler1=new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //发送过来的Message包含MarkerOptions对象，取出后add就可以了。
                    Bundle bundle = msg.getData();
                    MarkerOptions mar = new MarkerOptions();
                    mar = (MarkerOptions) bundle.get("MarkerOption");
                    Marker marker = aMap.addMarker(mar);
                    marker.setVisible(true);
                    marker.showInfoWindow();
                }
            };
        });
    }


    /***地图生命周期***/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行map的destroy,实现生命周期的管理
        mapView.onDestroy();
        mapLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop(){
        super.onStop();
        mapLocationClient.stopLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null){
            if(aMapLocation.getErrorCode()==0){

                mListener.onLocationChanged(aMapLocation);//显示系统小蓝点

                StringBuilder stringBuilder= new StringBuilder();

                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果
                this.Latitude=aMapLocation.getLatitude();//获取纬度
                this.Longitude=aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                //成功回调消息，设置相关信息
                int type = aMapLocation.getLocationType();
                String address = aMapLocation.getAddress();
                stringBuilder.append(type+address);
                Toast.makeText(this,stringBuilder.toString(),Toast.LENGTH_SHORT).show();
            }else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("Error Info",aMapLocation.getErrorCode()+"---" + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if(mapLocationClient == null){
            //初始化AMapLocationClient，并绑定监听
            mapLocationClient = new AMapLocationClient(getApplicationContext());

            //初始化定位参数
            mapLocationClientOption = new AMapLocationClientOption();
            //设置定位精度
            mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是否返回地址
            mapLocationClientOption.setNeedAddress(true);
            //是否只定位一次
            mapLocationClientOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
            mapLocationClientOption.setWifiActiveScan(true);
            //是否允许模拟位置
            mapLocationClientOption.setMockEnable(false);
            //定位时间间隔
            mapLocationClientOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            mapLocationClient.setLocationOption(mapLocationClientOption);
            //绑定监听
            mapLocationClient.setLocationListener(this);
            //开启监听
            mapLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if(mapLocationClient!=null){
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient = null;
    }
    /***地图生命周期***/
}
