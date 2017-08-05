package com.jerryzhiyuan.icity.activity.perinfosetdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hundsun.jerryzhiyuan.R;
import com.jerryzhiyuan.icity.activity.PerInfoSetActivity;


public class UNameSetActivity extends Activity {

    ImageView imageView=null; //返回
    TextView textView=null;   //Done
    EditText editText=null;   //填写内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uname_set);

        imageView= (ImageView) findViewById(R.id.set_name_logo_back);
        textView= (TextView) findViewById(R.id.set_name_done);
        editText= (EditText) findViewById(R.id.et_set_uname);

        /******返回功能******/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UNameSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 名字修改传值功能
         */

        //名字修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("userName",userName);
                intent.setClass(UNameSetActivity.this,PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

    }
}
