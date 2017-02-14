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

public class UMailSetActivity extends AppCompatActivity {

    ImageView imageView=null;
    TextView textView=null;
    EditText editText=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umail_set);

        imageView= (ImageView) findViewById(R.id.set_umail_logo_back);
        textView= (TextView) findViewById(R.id.set_umail_done);
        editText= (EditText) findViewById(R.id.et_set_umail);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UMailSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 邮箱修改传值功能尚未设置
         */

        //邮箱修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //填入修改的邮箱
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
