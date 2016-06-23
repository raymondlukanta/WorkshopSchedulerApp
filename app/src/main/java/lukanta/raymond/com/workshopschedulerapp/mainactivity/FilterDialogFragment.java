package lukanta.raymond.com.workshopschedulerapp.mainactivity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lukanta.raymond.com.workshopschedulerapp.R;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class FilterDialogFragment extends DialogFragment {

    public FilterDialogFragment() {
        // Required empty public constructor
    }

    public static FilterDialogFragment newInstance() {
        return new FilterDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_fragment_filter, container, false);
    Button filterButton = (Button)layout.findViewById(R.id.btn_filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return layout;
    }

}
