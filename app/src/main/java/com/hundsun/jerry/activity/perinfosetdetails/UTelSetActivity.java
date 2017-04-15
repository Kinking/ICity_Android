package com.hundsun.jerry.activity.perinfosetdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hundsun.jerry.R;
import com.hundsun.jerry.activity.PerInfoSetActivity;

public class UTelSetActivity extends Activity {

    ImageView imageView=null; //返回
    TextView textView=null;   //Done
    EditText editText=null;   //填写内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utel_set);

        imageView= (ImageView) findViewById(R.id.set_tel_logo_back);
        textView= (TextView) findViewById(R.id.set_tel_done);
        editText= (EditText) findViewById(R.id.et_set_utel);

        /******返回功能******/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UTelSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 电话修改传值功能尚未设置
         */

        //电话修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("tel",tel);
                intent.setClass(UTelSetActivity.this,PerInfoSetActivity.class);
                startActivity(intent);

            }
        });

    }
}
