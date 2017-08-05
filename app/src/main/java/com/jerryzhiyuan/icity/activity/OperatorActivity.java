package com.jerryzhiyuan.icity.activity;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;

import com.hundsun.jerryzhiyuan.R;
import com.jerryzhiyuan.icity.library.CircleImageView;

public class OperatorActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  NavigationView navigationView = null;
    CircleImageView circleImageView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //绑定侧滑界面头控件布局
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        /***点击头像即开始设置个人信息***/
        circleImageView = (CircleImageView) headerView.findViewById(R.id.head_imageView);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(OperatorActivity.this,PerInfoSetActivity.class);
                startActivity(i);
            }
        });
        //侧滑栏绑定
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.operator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.operator_map) {
            // 进入地图界面
            Intent i = new Intent();
            i.setClass(OperatorActivity.this,MapActivity.class);
            startActivity(i);

        } else if (id == R.id.opreator_message) {
            //
        } else if (id == R.id.operator_contacts) {
            //
        } else if (id == R.id.operator_setting) {
            //
        } else if (id == R.id.operator_quit) {
            //
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
