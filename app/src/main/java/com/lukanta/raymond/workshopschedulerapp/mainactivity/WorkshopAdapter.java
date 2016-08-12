package com.lukanta.raymond.workshopschedulerapp.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import com.lukanta.raymond.workshopschedulerapp.R;
import com.lukanta.raymond.workshopschedulerapp.bookingpage.BookingActivity;
import com.lukanta.raymond.workshopschedulerapp.mappage.WorkshopDetailsActivity;
import com.lukanta.raymond.workshopschedulerapp.mappage.WorkshopDetailsActivityFragment;
import com.lukanta.raymond.workshopschedulerapp.model.Workshop;
import com.lukanta.raymond.workshopschedulerapp.ui.AbstractListAdapter;
import com.lukanta.raymond.workshopschedulerapp.util.LatLngUtil;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class WorkshopAdapter extends AbstractListAdapter<Workshop, WorkshopAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final LatLng mCurrentLocation;

    public WorkshopAdapter(Context context, LatLng currentLocation) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mCurrentLocation = currentLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.row_workshop, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder routeViewHolder, int position) {
        bind(routeViewHolder, position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView workshopNameTextView;
        private final TextView workshopServiceTyreTextView;
        private final TextView workshopServiceOilTextView;
        private final TextView workshopServiceBatteryTextView;
        private final TextView workshopServiceDistanceTextView;
        private final RatingBar workshopRatingBar;
        private final ImageButton workshopMapImageButton;
        private final Button workshopBookButton;

        public ViewHolder(View v) {
            super(v);
            workshopNameTextView = (TextView) v.findViewById(R.id.txt_workshop_name);
            workshopRatingBar = (RatingBar) v.findViewById(R.id.rating_bar_workshop_rating);
            workshopServiceTyreTextView = (TextView) v.findViewById(R.id.img_workshop_services_tyre);
            workshopServiceOilTextView = (TextView) v.findViewById(R.id.img_workshop_services_oil);
            workshopServiceBatteryTextView = (TextView) v.findViewById(R.id.img_workshop_services_battery);
            workshopMapImageButton = (ImageButton) v.findViewById(R.id.btn_workshop_map);
            workshopServiceDistanceTextView = (TextView) v.findViewById(R.id.txt_workshop_distance);
            workshopBookButton = (Button)v.findViewById(R.id.btn_workshop_book);
        }
    }

    public void bind(ViewHolder messagingViewHolder, int position) {
        final Workshop workshop = mData.get(position);

        messagingViewHolder.workshopNameTextView.setText(workshop.getWorkshopName());
        messagingViewHolder.workshopRatingBar.setRating(workshop.getCustomerRating());
        messagingViewHolder.workshopMapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkshopDetailsActivity.class);
                intent.putExtra(WorkshopDetailsActivityFragment.WORKSHOP_EXTRA, workshop);
                mContext.startActivity(intent);
            }
        });

        int tyreChangeVisibility = workshop.getTyreChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceTyreTextView.setVisibility(tyreChangeVisibility);

        int oilChangeVisibility = workshop.getOilChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceOilTextView.setVisibility(oilChangeVisibility);

        int batteryChangeVisibility = workshop.getBatteryChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceBatteryTextView.setVisibility(batteryChangeVisibility);

        double distance = 0;
        if (mCurrentLocation != null) {
            distance = LatLngUtil.distance(mCurrentLocation, workshop.getWorkshopCoordinates()) / 1000;
        }
        messagingViewHolder.workshopServiceDistanceTextView.setText(mContext.getString(R.string.distance, distance));
        workshop.setDistance(distance);

        messagingViewHolder.workshopBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookingActivity.class);
                mContext.startActivity(intent);}
        });
    }
}