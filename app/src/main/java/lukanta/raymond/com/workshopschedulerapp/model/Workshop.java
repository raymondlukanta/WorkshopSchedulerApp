package lukanta.raymond.com.workshopschedulerapp.model;

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
    private int rating;
    private int oilChange;
    private int batteryChange;
    private int tyreChange;

    public int getBatteryChange() {
        return batteryChange;
    }

    public int getOilChange() {
        return oilChange;
    }

    public String getPhone() {
        return phone;
    }

    public int getRating() {
        return rating;
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
        dest.writeInt(this.rating);
        dest.writeInt(this.oilChange);
        dest.writeInt(this.batteryChange);
        dest.writeInt(this.tyreChange);
    }

    public Workshop() {
    }

    protected Workshop(Parcel in) {
        this.workshopId = in.readInt();
        this.workshopName = in.readString();
        this.workshopCoordinates = in.readString();
        this.phone = in.readString();
        this.rating = in.readInt();
        this.oilChange = in.readInt();
        this.batteryChange = in.readInt();
        this.tyreChange = in.readInt();
    }

    public static final Parcelable.Creator<Workshop> CREATOR = new Parcelable.Creator<Workshop>() {
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
