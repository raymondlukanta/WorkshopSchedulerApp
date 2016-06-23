package lukanta.raymond.com.workshopschedulerapp.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class LatLngUtil {
    public static double distance(LatLng StartLatLng, LatLng EndLatLng) {
        double lat1 = StartLatLng.latitude;
        double lat2 = EndLatLng.latitude;
        double lon1 = StartLatLng.longitude;
        double lon2 = EndLatLng.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6366000 * c;
    }
}
