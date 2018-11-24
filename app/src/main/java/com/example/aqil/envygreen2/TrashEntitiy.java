package com.example.aqil.envygreen2;

import android.os.Parcel;
import android.os.Parcelable;

public class TrashEntitiy implements Parcelable {
    int producrId;
    String productName;
    Double price;
    int stock;
    String description;
    Double Rating;
    String salesBankName;
    String thumbnail_path;
    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TrashEntitiy(int producrId, String productName, Double price, int stock, String description, Double rating, String salesBankName, String thumbnail_path, String category) {

        this.producrId = producrId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.description = description;
        Rating = rating;
        this.salesBankName = salesBankName;
        this.thumbnail_path = thumbnail_path;
        this.category = category;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public int getProducrId() {
        return producrId;
    }

    public void setProducrId(int producrId) {
        this.producrId = producrId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public String getSalesBankName() {
        return salesBankName;
    }

    public void setSalesBankName(String salesBankName) {
        this.salesBankName = salesBankName;
    }

    public TrashEntitiy() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.producrId);
        dest.writeString(this.productName);
        dest.writeValue(this.price);
        dest.writeInt(this.stock);
        dest.writeString(this.description);
        dest.writeValue(this.Rating);
        dest.writeString(this.salesBankName);
        dest.writeString(this.thumbnail_path);
        dest.writeString(this.category);
    }

    protected TrashEntitiy(Parcel in) {
        this.producrId = in.readInt();
        this.productName = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.stock = in.readInt();
        this.description = in.readString();
        this.Rating = (Double) in.readValue(Double.class.getClassLoader());
        this.salesBankName = in.readString();
        this.thumbnail_path = in.readString();
        this.category = in.readString();
    }

    public static final Creator<TrashEntitiy> CREATOR = new Creator<TrashEntitiy>() {
        @Override
        public TrashEntitiy createFromParcel(Parcel source) {
            return new TrashEntitiy(source);
        }

        @Override
        public TrashEntitiy[] newArray(int size) {
            return new TrashEntitiy[size];
        }
    };
}
