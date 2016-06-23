package lukanta.raymond.com.workshopschedulerapp.mappage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import lukanta.raymond.com.workshopschedulerapp.R;
import lukanta.raymond.com.workshopschedulerapp.model.Workshop;
import lukanta.raymond.com.workshopschedulerapp.ui.BaseFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class WorkshopDetailsActivityFragment extends BaseFragment implements OnMapReadyCallback {
    public static String WORKSHOP_EXTRA = "workshopExtra";

    private GoogleMap mMap;
    private View mLayout;
    private Workshop mWorkshop;

    public WorkshopDetailsActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getActivity().getIntent().getExtras();
        mWorkshop = b.getParcelable(WORKSHOP_EXTRA);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng workshopLatLng = mWorkshop.getWorkshopCoordinates();
        mMap.addMarker(new MarkerOptions().position(workshopLatLng).title("The workshop location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workshopLatLng, 18));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mLayout != null) {
            ViewGroup parent = (ViewGroup) mLayout.getParent();
            if (parent != null)
                parent.removeView(mLayout);
        }
        try {
            mLayout = inflater.inflate(R.layout.fragment_workshop_details, container, false);
        } catch (InflateException e) {
            //e.printStackTrace();
            /* map is already there, just return view as it is */
        }

        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        TextView workshopNameTextView = (TextView) mLayout.findViewById(R.id.txt_workshop_name);
        TextView workshopRatingTextView = (TextView) mLayout.findViewById(R.id.txt_workshop_rating);
        ImageView workshopServiceTyreImageView = (ImageView) mLayout.findViewById(R.id.img_workshop_services_tire);
        ImageView workshopServiceOilImageView = (ImageView) mLayout.findViewById(R.id.img_workshop_services_oil);
        ImageView workshopServiceBatteryImageView = (ImageView) mLayout.findViewById(R.id.img_workshop_services_battery);

        workshopNameTextView.setText(mWorkshop.getWorkshopName());
        workshopRatingTextView.setText(getActivity().getString(R.string.rating, mWorkshop.getRating()));

        if (mWorkshop.getTyreChange() == 0) {
            workshopServiceTyreImageView.setVisibility(View.GONE);
        }

        if (mWorkshop.getOilChange() == 0) {
            workshopServiceOilImageView.setVisibility(View.GONE);
        }

        if (mWorkshop.getBatteryChange() == 0) {
            workshopServiceBatteryImageView.setVisibility(View.GONE);
        }

        ImageButton directionButton = (ImageButton) mLayout.findViewById(R.id.btn_direction);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s, %s", mWorkshop.getWorkshopCoordinates().latitude, mWorkshop.getWorkshopCoordinates().longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        ImageButton callButton = (ImageButton) mLayout.findViewById(R.id.btn_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callUri = String.format(Locale.ENGLISH, "tel:%s", mWorkshop.getPhone());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(callUri));
                startActivity(intent);
            }
        });
        return mLayout;
    }
}
