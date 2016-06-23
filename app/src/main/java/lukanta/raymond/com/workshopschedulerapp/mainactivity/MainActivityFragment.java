package lukanta.raymond.com.workshopschedulerapp.mainactivity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import lukanta.raymond.com.workshopschedulerapp.R;
import lukanta.raymond.com.workshopschedulerapp.app.WorkshopSchedulerApp;
import lukanta.raymond.com.workshopschedulerapp.model.Workshop;
import lukanta.raymond.com.workshopschedulerapp.ui.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private WorkshopSchedulerApp mApp;
    private WorkshopAdapter mWorkshopAdapter;
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mApp = (WorkshopSchedulerApp)getActivity().getApplicationContext();
        mWorkshopAdapter = new WorkshopAdapter(getActivity());
        callGetWorkshopListAPI();
        super.onCreate(savedInstanceState);
    }

    private void callGetWorkshopListAPI() {
        showProgressDialog();

        final Call<List<Workshop>> citiesRawJson = mApp.getApiService().getWorkshopList();
        citiesRawJson.enqueue(
                new Callback<List<Workshop>>() {
                    @Override
                    public void onResponse(Call<List<Workshop>> call, Response<List<Workshop>> response) {
                        if (response.isSuccessful()) {
                            List<Workshop> workshopList = response.body();
                            mWorkshopAdapter.setData(workshopList);
                            mWorkshopAdapter.notifyDataSetChanged();
                        }

                        hideProgressDialog();
                    }

                    @Override
                    public void onFailure(Call<List<Workshop>> call, Throwable t) {
                        Toast.makeText(getActivity(), getString(R.string.error_common), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                        hideProgressDialog();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView workshopRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view_workshop_list);
        workshopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        workshopRecyclerView.setAdapter(mWorkshopAdapter);
        return layout;
    }
}
