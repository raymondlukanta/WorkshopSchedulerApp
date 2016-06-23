package lukanta.raymond.com.workshopschedulerapp.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lukanta.raymond.com.workshopschedulerapp.R;
import lukanta.raymond.com.workshopschedulerapp.mappage.WorkshopDetailsActivity;
import lukanta.raymond.com.workshopschedulerapp.mappage.WorkshopDetailsActivityFragment;
import lukanta.raymond.com.workshopschedulerapp.model.Workshop;
import lukanta.raymond.com.workshopschedulerapp.ui.AbstractListAdapter;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class WorkshopAdapter extends AbstractListAdapter<Workshop, WorkshopAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mInflater;

    public WorkshopAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
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
        private final TextView workshopRatingTextView;
        private final ImageView workshopServiceTyreImageView;
        private final ImageView workshopServiceOilImageView;
        private final ImageView workshopServiceBatteryImageView;
        private final ImageButton workshopMapImageButton;

        public ViewHolder(View v) {
            super(v);
            workshopNameTextView = (TextView) v.findViewById(R.id.txt_workshop_name);
            workshopRatingTextView = (TextView) v.findViewById(R.id.txt_workshop_rating);
            workshopServiceTyreImageView = (ImageView) v.findViewById(R.id.img_workshop_services_tire);
            workshopServiceOilImageView = (ImageView) v.findViewById(R.id.img_workshop_services_oil);
            workshopServiceBatteryImageView = (ImageView) v.findViewById(R.id.img_workshop_services_battery);
            workshopMapImageButton = (ImageButton) v.findViewById(R.id.btn_workshop_map);
        }
    }

    public void bind(ViewHolder messagingViewHolder, int position) {
        final Workshop workshop = mData.get(position);

        messagingViewHolder.workshopNameTextView.setText(workshop.getWorkshopName());
        messagingViewHolder.workshopRatingTextView.setText(mContext.getString(R.string.rating, workshop.getRating()));
        messagingViewHolder.workshopMapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkshopDetailsActivity.class);
                intent.putExtra(WorkshopDetailsActivityFragment.WORKSHOP_EXTRA, workshop);
                mContext.startActivity(intent);
            }
        });

        int tyreChangeVisibility = workshop.getTyreChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceTyreImageView.setVisibility(tyreChangeVisibility);

        int oilChangeVisibility = workshop.getOilChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceOilImageView.setVisibility(oilChangeVisibility);

        int batteryChangeVisibility = workshop.getBatteryChange() == 0 ? View.GONE : View.VISIBLE;
        messagingViewHolder.workshopServiceBatteryImageView.setVisibility(batteryChangeVisibility);
    }
}