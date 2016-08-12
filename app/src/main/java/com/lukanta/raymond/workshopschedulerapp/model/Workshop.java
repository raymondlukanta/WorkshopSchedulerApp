package com.lukanta.raymond.workshopschedulerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class Workshop implements Parcelable {
    private int workshopId;
    private String workshopName;
    private String workshopCoordinates;
    private String phone;
    private int customerRating;
    private int oilChange;
    private int batteryChange;
    private int tyreChange;
    private double distance;

    public int getBatteryChange() {
        return batteryChange;
    }

    public int getOilChange() {
        return oilChange;
    }

    public String getPhone() {
        return phone;
    }

    public int getCustomerRating() {
        return customerRating;
    }

    public int getTyreChange() {
        return tyreChange;
    }

    public LatLng getWorkshopCoordinates() {
        String [] coordinateSplit = workshopCoordinates.split(",");
        double latitude = Double.parseDouble(coordinateSplit[1]);
        double longitude = Double.parseDouble(coordinateSplit[0]);
        return new LatLng(latitude, longitude);
    }

    public int getWorkshopId() {
        return workshopId;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.workshopId);
        dest.writeString(this.workshopName);
        dest.writeString(this.workshopCoordinates);
        dest.writeString(this.phone);
        dest.writeInt(this.customerRating);
        dest.writeInt(this.oilChange);
        dest.writeInt(this.batteryChange);
        dest.writeInt(this.tyreChange);
        dest.writeDouble(this.distance);
    }

    public Workshop() {
    }

    protected Workshop(Parcel in) {
        this.workshopId = in.readInt();
        this.workshopName = in.readString();
        this.workshopCoordinates = in.readString();
        this.phone = in.readString();
        this.customerRating = in.readInt();
        this.oilChange = in.readInt();
        this.batteryChange = in.readInt();
        this.tyreChange = in.readInt();
        this.distance = in.readDouble();
    }

    public static final Creator<Workshop> CREATOR = new Creator<Workshop>() {
        @Override
        public Workshop createFromParcel(Parcel source) {
            return new Workshop(source);
        }

        @Override
        public Workshop[] newArray(int size) {
            return new Workshop[size];
        }
    };
}
