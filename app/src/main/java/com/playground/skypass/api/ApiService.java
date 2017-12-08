package com.playground.skypass.api;

import com.google.gson.JsonObject;
import com.playground.skypass.model.ModelBase;
import com.playground.skypass.model.ModelCheckIn;
import com.playground.skypass.model.ModelEvent;
import com.playground.skypass.model.ModelMonster;
import com.playground.skypass.model.ModelPoiAll;
import com.playground.skypass.model.ModelPoiDetail;
import com.playground.skypass.model.ModelPoiSearch;
import com.playground.skypass.model.ModelProfile;
import com.playground.skypass.model.ModelPromo;
import com.playground.skypass.model.ModelReward;
import com.playground.skypass.model.ModelTransaction;
import com.playground.skypass.model.ModelTransactionDetail;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public interface ApiService {

    /**
     * END POINT
     */
    String ADD_MEMBER = "user/add";
    String GET_REWARD = "reward/get";
    String GET_EVENT = "event/get";
    String UPLOAD_PHOTO = "promo/get";
    String GET_PROMO = "promo/get";
    String GET_MONSTER = "monster/get";
    String CHECK_IN = "user/add/event";
    String UPDATE_POINT = "user/add/point";
    String CATCH_MONSTER = "user/add/monster";
    String MY_MONSTER = "user/{user_id}/monster";
    String MY_EVENT = "user/{user_id}/event";
    String MY_REDEEMED = "user/{user_id}/reward";
    String REDEEM = "user/add/reward";
    String PROFILE = "user/{user_id}/get";
    String BUY = "transaction/buy";
    String LOCATION_UPDATE = "location/update";
    String REGISTER_NOTIFICATION = "fcm";
    String MY_TRANSACTION = "transaction/{user_id}";
    String TRANSACTION_DETAIL = "transaction/{transaksi_id}/detail";

    String POI_ALL = "poi";
    String POI_DETAIL = "poi/{id}";
    String POI_SEARCH = "poi/{q}";

    @Headers("Content-Type: application/json")
    @POST(ADD_MEMBER)
    Call<ModelBase> addMember(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(REGISTER_NOTIFICATION)
    Call<ModelBase> registerPushNotif(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(LOCATION_UPDATE)
    Call<ModelBase> locationUpdate(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(BUY)
    Call<ModelBase> buy(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(REDEEM)
    Call<ModelBase> redeem(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @GET(MY_EVENT)
    Call<ModelEvent> myCheckIn(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET(MY_TRANSACTION)
    Call<ModelTransaction> myTransaction(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET(TRANSACTION_DETAIL)
    Call<ModelTransactionDetail> myTransactionDetail(@Path("transaksi_id") String transaksi_id);

    @Headers("Content-Type: application/json")
    @GET(MY_MONSTER)
    Call<ModelMonster> myMonster(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET(MY_REDEEMED)
    Call<ModelReward> myRedeem(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET(PROFILE)
    Call<ModelProfile> myProfile(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @POST(CATCH_MONSTER)
    Call<ModelBase> catchMonster(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(UPDATE_POINT)
    Call<ModelBase> updatePoint(@Body JsonObject jsonPost);

    @Headers("Content-Type: application/json")
    @POST(CHECK_IN)
    Call<ModelCheckIn> checkIn(@Body JsonObject jsonPost);

    @Multipart
    @POST(UPLOAD_PHOTO)
    Call<ModelBase> uploadIMage(@Part MultipartBody.Part file);

    @Headers("Content-Type: application/json")
    @GET(GET_EVENT)
    Call<ModelEvent> getEvent();

    @Headers("Content-Type: application/json")
    @GET(GET_REWARD)
    Call<ModelReward> getReward();

    @Headers("Content-Type: application/json")
    @GET(GET_PROMO)
    Call<ModelPromo> getPromo();

    @Headers("Content-Type: application/json")
    @GET(GET_MONSTER)
    Call<ModelMonster> getMonster();

    @Headers("Content-Type: application/json")
    @GET(POI_DETAIL)
    Call<ModelPoiDetail> poiDetail(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET(POI_SEARCH)
    Call<ModelPoiSearch> poiSearch(@Path("q") String q);

    @Headers("Content-Type: application/json")
    @GET(POI_ALL)
    Call<ModelPoiAll> getPoiAll(@Body JsonObject jsonPost);

}
