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

public class UTruenameSetActivity extends Activity {

    ImageView imageView=null;  //返回
    TextView textView=null;    //完成
    EditText editText=null;    //填入名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utruename);

        imageView= (ImageView) findViewById(R.id.set_truename_logo_back);
        textView= (TextView) findViewById(R.id.set_truename_done);
        editText= (EditText) findViewById(R.id.et_set_truename);

        /******返回功能******/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UTruenameSetActivity.this, PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 真实姓名修改传值功能尚未设置
         */

        //真实姓名修改确定
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTrueName = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("userTrueName",userTrueName);
                intent.setClass(UTruenameSetActivity.this,PerInfoSetActivity.class);
                startActivity(intent);
            }
        });

    }
}
