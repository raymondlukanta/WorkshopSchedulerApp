package com.lukanta.raymond.workshopschedulerapp.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import com.lukanta.raymond.workshopschedulerapp.BR;
import com.lukanta.raymond.workshopschedulerapp.R;
import com.lukanta.raymond.workshopschedulerapp.bookingpage.BookingActivity;
import com.lukanta.raymond.workshopschedulerapp.databinding.RowWorkshopBinding;
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
        RowWorkshopBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_workshop, viewGroup, false);
        binding.setEventHandlers(this);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder routeViewHolder, int position) {
        ViewDataBinding viewDataBinding = routeViewHolder.getViewDataBinding();
        Workshop currentWorkshop = mData.get(position);
        double distance = 0;
        if (mCurrentLocation != null) {
            distance = LatLngUtil.distance(mCurrentLocation, currentWorkshop.getWorkshopCoordinates()) / 1000;
        }
//        messagingViewHolder.workshopServiceDistanceTextView.setText(mContext.getString(R.string.distance, distance));
        currentWorkshop.setDistance(distance);

        viewDataBinding.setVariable(BR.workshop, currentWorkshop);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mViewDataBinding;

//        private final TextView workshopNameTextView;
//        private final TextView workshopServiceTyreTextView;
//        private final TextView workshopServiceOilTextView;
//        private final TextView workshopServiceBatteryTextView;
//        private final TextView workshopServiceDistanceTextView;
//        private final RatingBar workshopRatingBar;
//        private final ImageButton workshopMapImageButton;
//        private final Button workshopBookButton;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

//    public void bind(ViewHolder messagingViewHolder, int position) {

//
//        messagingViewHolder.workshopBookButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, BookingActivity.class);
//                mContext.startActivity(intent);}
//        });
//    }

    public void onClickWorkshopMapImageButton(Workshop workshop) {
        Intent intent = new Intent(mContext, WorkshopDetailsActivity.class);
        intent.putExtra(WorkshopDetailsActivityFragment.WORKSHOP_EXTRA, workshop);
        mContext.startActivity(intent);
    }

    public void onClickWorkshopBookButton() {
        Intent intent = new Intent(mContext, BookingActivity.class);
        mContext.startActivity(intent);
    }
}