package com.example.shailendra.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Items implements Parcelable{

    // private variables
    public int _id;
    public String _name;
    public String _phone_number;


    public Items() {
    }

    // constructor
    public Items(int id, String name, String _phone_number) {
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;

    }

    // constructor
    public Items(String name, String _phone_number, String _email) {
        this._name = name;
        this._phone_number = _phone_number;

    }

    protected Items(Parcel in) {
        _id = in.readInt();
        _name = in.readString();
        _phone_number = in.readString();
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_name);
        dest.writeString(_phone_number);
    }
}