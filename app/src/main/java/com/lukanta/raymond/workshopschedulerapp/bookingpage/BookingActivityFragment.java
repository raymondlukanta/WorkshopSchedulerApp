package com.lukanta.raymond.workshopschedulerapp.bookingpage;

import android.app.DatePickerDialog;
import android.app.admin.DeviceAdminInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.lukanta.raymond.workshopschedulerapp.R;
import com.lukanta.raymond.workshopschedulerapp.model.BookingTime;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookingActivityFragment extends Fragment {
    private static int NO_MORE_TIME_SLOT = -1;
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

        final Spinner dateSpinner = (Spinner)layout.findViewById(R.id.spinner_booking_time);
        List<BookingTime> bookingTimeList = generateDummyData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        BookingTimeAdapter bookingTimeAdapter = new BookingTimeAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item, bookingTimeList);

        dateSpinner.setAdapter(bookingTimeAdapter);

        if (bookingTimeList.get(0).isFullyBooked()) {
            int nextAvailableSlot = getNextAvailableSlot(bookingTimeList);
            if (nextAvailableSlot == NO_MORE_TIME_SLOT) {
                dateSpinner.setEnabled(false);
                Toast.makeText(getActivity(), "This workshop is fully booked. Try another day.", Toast.LENGTH_SHORT).show();

            } else {
                dateSpinner.setSelection(nextAvailableSlot);
            }
        }

        final EditText nameTextView = (EditText) layout.findViewById(R.id.txt_booking_name);
        final EditText phoneTextView = (EditText) layout.findViewById(R.id.txt_booking_email);
        final EditText emailTextView = (EditText) layout.findViewById(R.id.txt_booking_phone);

        final CheckBox tyreCheckBox = (CheckBox) layout.findViewById(R.id.cb_booking_change_tyre);
        final CheckBox oilCheckBox = (CheckBox) layout.findViewById(R.id.cb_booking_change_oil);
        final CheckBox batteryCheckBox = (CheckBox) layout.findViewById(R.id.cb_booking_change_battery);

        Button submitButton = (Button)layout.findViewById(R.id.btn_submit_booking);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = nameTextView.getText().toString().trim();
                String phoneString = phoneTextView.getText().toString().trim();
                String emailString = emailTextView.getText().toString().trim();

                String message;
                if (dateSpinner.isEnabled()
                        && !nameString.isEmpty()
                        && !phoneString.isEmpty()
                        && !emailString.isEmpty()
                        && (tyreCheckBox.isChecked()
                            || oilCheckBox.isChecked()
                            || batteryCheckBox.isChecked())) {
                    message = "Sending data to server";
                } else {
                    message = "Please fill in all the fields";
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

            }
        });
        return layout;
    }

    private int getNextAvailableSlot(List<BookingTime> bookingTimeList) {
        for (int i = 1; i< bookingTimeList.size(); i++) {
            if (!bookingTimeList.get(i).isFullyBooked()) {
                return i;
            }
        }
        return NO_MORE_TIME_SLOT;
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
