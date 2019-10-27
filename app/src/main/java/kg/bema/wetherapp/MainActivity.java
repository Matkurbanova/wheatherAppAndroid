package kg.bema.wetherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String appId = "c24c7ae6549134ef2db84c1269c13ef3";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    EditText editTextCityOne, editTextCityTwo;
    TextView textViewRes;

    private double firstCityTemp;
    private double secondCityTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextCityOne = findViewById(R.id.editTextCityOne);
        editTextCityTwo = findViewById(R.id.editTextCityTwo);
        textViewRes = findViewById(R.id.textViewResult);
    }

    public void onClick(View v) {
        String cityOneName = editTextCityOne.getText().toString();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<JsonObject> call = weatherService.getWeather(cityOneName, appId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject objectMain = response.body();
                    JsonObject main = objectMain.getAsJsonObject("main");
                    firstCityTemp = main.get("temp").getAsDouble();
                    System.out.println(main.get("temp").getAsDouble());
                    getSecondCityTemp();
                } else {
                    try {
                        System.err.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getSecondCityTemp() {
        String cityTwoName = editTextCityTwo.getText().toString();

        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<JsonObject> call = weatherService.getWeather(cityTwoName, appId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject objectMain = response.body();
                    JsonObject main = objectMain.getAsJsonObject("main");
                    secondCityTemp = main.get("temp").getAsDouble();
                    System.out.println(main.get("temp").getAsDouble());
                    if (firstCityTemp > secondCityTemp) {
                        textViewRes.setText(editTextCityOne.getText());
                    } else {
                        textViewRes.setText(editTextCityTwo.getText());
                    }

                } else {
                    try {
                        System.err.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
