package com.example.aqil.envygreen2;

import android.os.Parcel;
import android.os.Parcelable;

public class SalesBankEntity implements Parcelable {
    int bankEntity;
    String name;
    String location;
    String nomorHp;
    String description;


    public SalesBankEntity(int bankEntity, String name, String location, String nomorHp, String description) {

        this.bankEntity = bankEntity;
        this.name = name;
        this.location = location;
        this.nomorHp = nomorHp;
        this.description = description;
    }

    public int getBankEntity() {

        return bankEntity;
    }

    public void setBankEntity(int bankEntity) {
        this.bankEntity = bankEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public void setNomorHp(String nomorHp) {
        this.nomorHp = nomorHp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<SalesBankEntity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bankEntity);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeString(this.nomorHp);
        dest.writeString(this.description);
    }

    public SalesBankEntity() {
    }

    protected SalesBankEntity(Parcel in) {
        this.bankEntity = in.readInt();
        this.name = in.readString();
        this.location = in.readString();
        this.nomorHp = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<SalesBankEntity> CREATOR = new Parcelable.Creator<SalesBankEntity>() {
        @Override
        public SalesBankEntity createFromParcel(Parcel source) {
            return new SalesBankEntity(source);
        }

        @Override
        public SalesBankEntity[] newArray(int size) {
            return new SalesBankEntity[size];
        }
    };
}
