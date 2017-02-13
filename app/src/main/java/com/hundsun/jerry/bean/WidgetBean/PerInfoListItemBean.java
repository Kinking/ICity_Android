package com.hundsun.jerry.bean.WidgetBean;

/**
 * Created by huangzhiyuan on 2017/2/13.
 */
public class PerInfoListItemBean {
    String itemOption;
    String itemValue;

    public PerInfoListItemBean(String itemOption, String itemValue) {
        this.itemOption = itemOption;
        this.itemValue = itemValue;
    }

    public String getItemOption() {
        return itemOption;
    }

    public void setItemOption(String itemOption) {
        this.itemOption = itemOption;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }


}
