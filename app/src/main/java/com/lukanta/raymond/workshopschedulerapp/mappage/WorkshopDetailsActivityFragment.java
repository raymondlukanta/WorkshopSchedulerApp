package com.lukanta.raymond.workshopschedulerapp.mappage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lukanta.raymond.workshopschedulerapp.R;
import com.lukanta.raymond.workshopschedulerapp.bookingpage.BookingActivity;
import com.lukanta.raymond.workshopschedulerapp.databinding.FragmentWorkshopDetailsBinding;
import com.lukanta.raymond.workshopschedulerapp.model.Workshop;
import com.lukanta.raymond.workshopschedulerapp.ui.BaseFragment;
import com.lukanta.raymond.workshopschedulerapp.util.IntentUtil;

import java.util.Locale;

/**
 placeholder fragment containing a simple view.
 */
public class WorkshopDetailsActivityFragment extends BaseFragment implements OnMapReadyCallback {
    public static String WORKSHOP_EXTRA = "workshopExtra";

    private GoogleMap mMap;
    private View mLayout;
    private Workshop mWorkshop;
    FragmentWorkshopDetailsBinding mBinding;
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
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_workshop_details, container, false);
            mLayout = mBinding.getRoot();
            mBinding.setWorkshop(mWorkshop);

        } catch (InflateException e) {
            //e.printStackTrace();
            /* map is already there, just return view as it is */
        }

        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        mBinding.btnDirection.setOnClickListener(new View.OnClickListener() {
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
        mBinding.btnCall.setOnClickListener(new View.OnClickListener() {
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

        mBinding.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                startActivity(intent);}
        });
        return mLayout;
    }
}
