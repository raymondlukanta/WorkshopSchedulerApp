package lukanta.raymond.com.workshopschedulerapp.bookingpage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lukanta.raymond.com.workshopschedulerapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookingActivityFragment extends Fragment {

    public BookingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }
}
