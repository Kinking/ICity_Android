package com.hundsun.jerry.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hundsun.jerry.R;
import com.hundsun.jerry.bean.WidgetBean.PerInfoListItemBean;

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
    public PerInfoListItemBean getItem(int position) {
        return (PerInfoListItemBean) mList.get(position);
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
        if (w == null){
            w = new widget();
            if(convertView==null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.perinfo_list_item,
                        null);
            }
            w.tv_option= (TextView) convertView.findViewById(R.id.item_option);
            w.tv_option= (TextView) convertView.findViewById(R.id.item_setting);
            convertView.setTag(w);
        }else {
            w = (widget) convertView.getTag();
        }

        //获得实体类
        PerInfoListItemBean perInfoListItemBean = (PerInfoListItemBean) mList.get(position);

        //填充数据
        w.tv_option.setText(perInfoListItemBean.getItemOption());
        w.tv_value.setText(perInfoListItemBean.getItemValue());
        return convertView;
    }

    //定义自定义列表中的各项
    public class widget{
        TextView tv_option;
        TextView tv_value;
    }
}
