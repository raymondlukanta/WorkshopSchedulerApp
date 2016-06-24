package lukanta.raymond.com.workshopschedulerapp.bookingpage;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lukanta.raymond.com.workshopschedulerapp.R;
import lukanta.raymond.com.workshopschedulerapp.model.BookingTime;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookingActivityFragment extends Fragment {
    private SimpleDateFormat mSimpleDateFormat;
    private TextView mDateTextView;

    public BookingActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String myFormat = "EEE, d MMM yyyy";
        mSimpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(calendar);
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_booking, container, false);

        mDateTextView = (TextView) layout.findViewById(R.id.txt_booking_date);
        Calendar calendar = Calendar.getInstance();
        updateLabel(calendar);
        mDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(calendar.getTimeInMillis());
                calendar.add(Calendar.DATE, 7);
                datePicker.setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        Spinner dateSpinner = (Spinner)layout.findViewById(R.id.spinner_booking_time);
        List<BookingTime> bookingTimeList = generateDummyData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        BookingTimeAdapter bookingTimeAdapter = new BookingTimeAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item, bookingTimeList);

        dateSpinner.setAdapter(bookingTimeAdapter);

        return layout;
    }

    private List<BookingTime> generateDummyData(int year, int month, int day) {
        List<BookingTime> bookingTimeList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 10, 0);

        int i = 0;
        while (i < 4) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(year, month, day, 10 + (i*2), 0);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(year, month, day, 12 + (i*2), 0);
            bookingTimeList.add(new BookingTime(startCalendar.getTime(), endCalendar.getTime(), i%2 == 0));
            i++;
        }
        return bookingTimeList;
    }

    private void updateLabel(Calendar calendar) {
        mDateTextView.setText(mSimpleDateFormat.format(calendar.getTime()));
    }
}
