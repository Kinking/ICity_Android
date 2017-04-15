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

public class UQQSetActivity extends Activity {

    ImageView imageView=null;  //返回
    TextView textView=null;    //Done
    EditText editText=null;    //填写内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uqq_set);

        imageView= (ImageView) findViewById(R.id.set_uqq_logo_back);
        textView= (TextView) findViewById(R.id.set_uqq_done);
        editText= (EditText) findViewById(R.id.et_set_uqq);

        /******返回功能******/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UQQSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * QQ修改传值功能尚未设置
         */

        //QQ修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qqNum = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("qqNum",qqNum);
                intent.setClass(UQQSetActivity.this,PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

    }
}
