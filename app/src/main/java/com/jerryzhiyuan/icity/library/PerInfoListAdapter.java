package com.jerryzhiyuan.icity.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hundsun.jerryzhiyuan.R;
import com.jerryzhiyuan.icity.bean.WidgetBean.PerInfoListItemBean;

import java.util.List;

/**
 * Created by huangzhiyuan on 2017/2/13.
 */
public class PerInfoListAdapter extends BaseAdapter{

    List mList;
    Context mContext;

    public PerInfoListAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater 视图构造器
        //每一个元素的视图
        widget w = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.perinfo_list_item,
                    null);
            //得到控件
            w = new widget();
            w.tv_option = (TextView) convertView.findViewById(R.id.item_option);
            w.tv_value = (TextView) convertView.findViewById(R.id.item_setting);
            convertView.setTag(w);//getTag:得到对象里面的控件

        } else {
            w = (widget) convertView.getTag();
        }

        //获得实体类
        PerInfoListItemBean pi = (PerInfoListItemBean) mList.get(position);
        //填充数据

        w.tv_option.setText(pi.getItemOption());
        w.tv_value.setText(pi.getItemValue());

        return convertView;
    }


    //定义自定义列表中的各项
    public class widget{
        TextView tv_option;
        TextView tv_value;
    }
}
