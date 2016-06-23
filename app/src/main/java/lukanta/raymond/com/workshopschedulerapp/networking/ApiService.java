package lukanta.raymond.com.workshopschedulerapp.networking;

import java.util.List;

import lukanta.raymond.com.workshopschedulerapp.model.Workshop;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by raymondlukanta on 28/05/16.
 */
public interface ApiService {
    @GET("u/2014613/workshop/workshoplist")
    Call<List<Workshop>> getWorkshopList();
}
