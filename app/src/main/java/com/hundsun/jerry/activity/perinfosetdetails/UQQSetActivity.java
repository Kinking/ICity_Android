package com.hundsun.jerry.activity.perinfosetdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hundsun.jerry.R;
import com.hundsun.jerry.activity.PerInfoSetActivity;

public class UQQSetActivity extends AppCompatActivity {

    ImageView imageView=null;
    TextView textView=null;
    EditText editText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uqq_set);

        imageView= (ImageView) findViewById(R.id.set_uqq_logo_back);
        textView= (TextView) findViewById(R.id.set_uqq_done);
        editText= (EditText) findViewById(R.id.et_set_uqq);

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

            }
        });


        //填入修改的QQ
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
