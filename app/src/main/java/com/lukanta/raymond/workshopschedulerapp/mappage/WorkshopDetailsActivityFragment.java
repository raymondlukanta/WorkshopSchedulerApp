package com.lukanta.raymond.workshopschedulerapp.mappage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import com.lukanta.raymond.workshopschedulerapp.bookingpage.BookingActivity;
import com.lukanta.raymond.workshopschedulerapp.R;
import com.lukanta.raymond.workshopschedulerapp.model.Workshop;
import com.lukanta.raymond.workshopschedulerapp.ui.BaseFragment;
import com.lukanta.raymond.workshopschedulerapp.util.IntentUtil;

/**
 placeholder fragment containing a simple view.
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
        mMap.addMarker(new MarkerOptions().position(workshopLatLng).title(mWorkshop.getWorkshopName()));
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
        RatingBar workshopRatingBar = (RatingBar) mLayout.findViewById(R.id.rating_bar_workshop_rating);
        TextView workshopServiceTyreTextView = (TextView) mLayout.findViewById(R.id.img_workshop_services_tyre);
        TextView workshopServiceOilTextView = (TextView) mLayout.findViewById(R.id.img_workshop_services_oil);
        TextView workshopServiceBatteryTextView = (TextView) mLayout.findViewById(R.id.img_workshop_services_battery);
        TextView workshopServiceDistanceTextView = (TextView) mLayout.findViewById(R.id.txt_workshop_distance);;

        workshopNameTextView.setText(mWorkshop.getWorkshopName());
        workshopRatingBar.setRating(mWorkshop.getCustomerRating());

        workshopServiceDistanceTextView.setText(getActivity().getString(R.string.distance, mWorkshop.getDistance()));

        if (mWorkshop.getTyreChange() == 0) {
            workshopServiceTyreTextView.setVisibility(View.GONE);
        }

        if (mWorkshop.getOilChange() == 0) {
            workshopServiceOilTextView.setVisibility(View.GONE);
        }

        if (mWorkshop.getBatteryChange() == 0) {
            workshopServiceBatteryTextView.setVisibility(View.GONE);
        }

        ImageButton directionButton = (ImageButton) mLayout.findViewById(R.id.btn_direction);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s, %s", mWorkshop.getWorkshopCoordinates().latitude, mWorkshop.getWorkshopCoordinates().longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                if (IntentUtil.isIntentAvailable(getActivity(), intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_navigation_app_not_exist), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageButton callButton = (ImageButton) mLayout.findViewById(R.id.btn_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String callUri = String.format(Locale.ENGLISH, "tel:%s", mWorkshop.getPhone());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(callUri));
                if (IntentUtil.isIntentAvailable(getActivity(), intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_call_app_not_exist), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button bookAppointmentButton = (Button) mLayout.findViewById(R.id.btn_book_appointment);
        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                startActivity(intent);}
        });
        return mLayout;
    }
}
