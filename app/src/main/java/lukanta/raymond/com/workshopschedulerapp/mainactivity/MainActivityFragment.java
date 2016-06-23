package lukanta.raymond.com.workshopschedulerapp.mainactivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

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
public class MainActivityFragment extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_CHECK_SETTINGS = 3000;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    private WorkshopSchedulerApp mApp;
    private WorkshopAdapter mWorkshopAdapter;
    private GoogleApiClient mGoogleApiClient;
    private RecyclerView mWorkshopRecyclerView;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApp = (WorkshopSchedulerApp)getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        mWorkshopRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view_workshop_list);
        mWorkshopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PERMISSIONS_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            continueOnCreateView();
        }
        return layout;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    continueOnCreateView();

                } else {
                    showPermissionRationaleDialog();
                }
                break;
            }
        }
    }

    private void showPermissionRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle(getString(R.string.alert_dialog_important))
                .setMessage(getString(R.string.alert_dialog_location_permission_denied))
                .setPositiveButton(getString(R.string.dialog_got_it), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermissions(PERMISSIONS_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                });
        builder.create().show();
    }

    private void continueOnCreateView() {
        setupGoogleApiClient();
    }

    private void setupGoogleApiClient() {
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        LocationRequest mLocationRequest = createLocationRequest();
        setupLocationSettingsAPI(mLocationRequest);
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void setupLocationSettingsAPI(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().setAlwaysShow(true)
                .addLocationRequest(locationRequest);
        setupLocationSettingsPendingResult(builder);
    }

    private void setupLocationSettingsPendingResult(LocationSettingsRequest.Builder builder) {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LatLng currentLocation = getLastKnownLocation();
        mWorkshopAdapter = new WorkshopAdapter(getActivity(), currentLocation);
        mWorkshopRecyclerView.setAdapter(mWorkshopAdapter);
        callGetWorkshopListAPI();
    }

    public LatLng getLastKnownLocation() throws IllegalStateException {
        if (mGoogleApiClient.isConnected()) {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (currentLocation != null) {
                return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("GoogleApiClient is not connected");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void callGetWorkshopListAPI() {
        showProgressDialog();

        final Call<List<Workshop>> workshopListCall = mApp.getApiService().getWorkshopList();
        workshopListCall.enqueue(
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
}
