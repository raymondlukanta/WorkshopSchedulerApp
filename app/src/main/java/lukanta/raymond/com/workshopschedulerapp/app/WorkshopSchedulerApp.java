package lukanta.raymond.com.workshopschedulerapp.app;

import android.app.Application;

import lukanta.raymond.com.workshopschedulerapp.networking.ApiService;
import lukanta.raymond.com.workshopschedulerapp.networking.RestClient;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class WorkshopSchedulerApp extends Application {
    private ApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();

        RestClient restClient = new RestClient();
        mApiService =  restClient.getApiService();
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
