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

import org.w3c.dom.Text;

public class UNameSetActivity extends AppCompatActivity {

    ImageView imageView=null;
    TextView textView=null;
    EditText editText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uname_set);

        imageView= (ImageView) findViewById(R.id.set_name_logo_back);
        textView= (TextView) findViewById(R.id.set_name_done);
        editText= (EditText) findViewById(R.id.et_set_uname);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UNameSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 名字修改传值功能尚未设置
         */

        //名字修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //填入修改的名字
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
