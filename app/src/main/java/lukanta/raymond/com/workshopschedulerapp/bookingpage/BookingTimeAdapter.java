package lukanta.raymond.com.workshopschedulerapp.bookingpage;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lukanta.raymond.com.workshopschedulerapp.R;
import lukanta.raymond.com.workshopschedulerapp.model.BookingTime;

/**
 * Created by raymondlukanta on 24/06/16.
 */
public class BookingTimeAdapter extends ArrayAdapter {
    private final int mTextColorEnableResourceId;
    private final int mTextColorDisableResourceId;
    Context mContext;

    public BookingTimeAdapter(Context context, int resource, List<BookingTime> data) {
        super(context, resource, data);
        mContext = context;
        mTextColorEnableResourceId = ContextCompat.getColor(mContext, R.color.default_text_color);
        mTextColorDisableResourceId = ContextCompat.getColor(mContext, R.color.list_divider);

    }

    @Override
    public boolean isEnabled(int position) {
        return ((BookingTime) getItem(position)).isFullyBooked();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        BookingTime bookingTime = (BookingTime) getItem(position);

        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view;

        String dateString = formatTime(bookingTime.getStartDate(), bookingTime.getEndDate());

        if (isEnabled(position)) {
            textView.setTextColor(mTextColorEnableResourceId);
        } else {
            textView.setTextColor(mTextColorDisableResourceId);
            dateString += mContext.getString(R.string.fully_booked);
        }
        textView.setText(dateString);

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookingTime bookingTime = (BookingTime) getItem(position);
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view;

        String dateString = formatTime(bookingTime.getStartDate(), bookingTime.getEndDate());
        textView.setText(dateString);
        return view;
    }

    private String formatTime(Date startDate, Date EndDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(EndDate);
        return String.format(Locale.ENGLISH, "%d.00 - %d.00", startCalendar.get(Calendar.HOUR_OF_DAY), endCalendar.get(Calendar.HOUR_OF_DAY));
    }
}
