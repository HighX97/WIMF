package moun.com.wimf.model;

import android.os.Parcel;
import android.os.Parcelable;


public class WIMF_UserItems implements Parcelable {

    private int id;
    private int itemImage;
    private String itemName;
    private double itemPrice;
    private String itemDescription;
    private int itemQuantity;

    public WIMF_UserItems() {
        super();
    }


    public WIMF_UserItems(String itemName, int itemImage) {
        this.itemName = itemName;
        this.itemImage = itemImage;
    }

    public WIMF_UserItems(String itemName, int itemImage, double itemPrice, String itemDescription) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    public WIMF_UserItems(Parcel parcel) {
        super();
        this.id = parcel.readInt();
        this.itemName = parcel.readString();
        this.itemImage = parcel.readInt();
        this.itemPrice = parcel.readDouble();
        this.itemDescription = parcel.readString();
        this.itemQuantity = parcel.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getItemName());
        parcel.writeInt(getItemImage());
        parcel.writeDouble(getItemPrice());
        parcel.writeString(getItemDescription());
        parcel.writeInt(getItemQuantity());
    }

    @Override
    public String toString() {
        return "MenuItems{" +
                "id=" + id +
                ", itemImage=" + itemImage +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemQuantity=" + itemQuantity +
                '}';
    }


    public static final Creator<WIMF_UserItems> CREATOR = new Creator<WIMF_UserItems>() {
        public WIMF_UserItems createFromParcel(Parcel in) {
            return new WIMF_UserItems(in);
        }

        public WIMF_UserItems[] newArray(int size) {
            return new WIMF_UserItems[size];
        }
    };


}
