package kg.bema.wetherapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather")
    Call<JsonObject> getWeather(@Query("q") String city, @Query("appid") String appId);
}
