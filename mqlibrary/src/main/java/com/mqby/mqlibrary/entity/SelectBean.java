package com.mqby.mqlibrary.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author MaQiang
 * @time 2018/6/27 0027 10:37
 * @class 选择类
 */
public class SelectBean implements Parcelable {
    public String id;
    public String name;
    public boolean select;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    public SelectBean() {
    }

    public SelectBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected SelectBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.select = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SelectBean> CREATOR = new Parcelable.Creator<SelectBean>() {
        @Override
        public SelectBean createFromParcel(Parcel source) {
            return new SelectBean(source);
        }

        @Override
        public SelectBean[] newArray(int size) {
            return new SelectBean[size];
        }
    };
}
