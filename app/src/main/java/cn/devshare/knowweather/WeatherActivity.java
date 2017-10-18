package cn.devshare.knowweather;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import cn.devshare.knowweather.config.Api;
import cn.devshare.knowweather.entity.Weather;
import cn.devshare.knowweather.entity.dailyforecast.DailyForecast;
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public static final String Activity="WeatherActivity";

    private ScrollView weatherLayout;

    private Button navButton;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView backgroudImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        getWeatherData();
    }

    private void getWeatherData() {
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=preferences.getString("weather",null);
        String bingPic=preferences.getString("bing_pic",null);
        if(weatherString!=null){
            Weather weather=GsonUtil.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
            //解析数据
        }else {
            //请求数据
            String weatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        if(bingPic!=null){
            Glide.with(this).load(bingPic).into(backgroudImg);
        }else{
            loadBingPic();
        }
    }

    private void loadBingPic() {
        OkHttpUtil.get(Api.BingUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(backgroudImg);
                    }
                });
            }
        });
    }

    private void requestWeather(String weatherId) {
        String url=Api.WeatherBaseUrl+weatherId+"&key="+Api.WeatherKey;
        Log.i(Activity,url);
        OkHttpUtil.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resposeText=response.body().string();
                Log.i("the weather info",resposeText.toString());
                final Weather weather=GsonUtil.handleWeatherResponse(resposeText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("is ok?",weather.getStatus());
                        if(weather!=null&& "ok".equals(weather.getStatus())){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",resposeText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName=weather.getBasic().getCity();
        String updateTime=weather.getBasic().getUpdate().getLoc().split(" ")[1];//获取时分
        String degree=weather.getNow().getTmp()+"℃";
        String weatherInfo=weather.getNow().getCond().getTxt();
        Log.i(Activity,updateTime);
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();

        for(DailyForecast forecast:weather.getDailyForecast()){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.getDate());
            infoText.setText(forecast.getCond().getTxtD());
            maxText.setText(forecast.getTmp().getMax());
            minText.setText(forecast.getTmp().getMin());
            forecastLayout.addView(view);
        }
        if(weather.getAqi()!=null){
            Log.i(Activity,weather.getAqi().toString());
            if(weather.getAqi().getCity()!=null){
                Log.i(Activity,weather.getAqi().getCity().getAqi());

            }
            if(weather.getAqi().getCity().getPm25()!=null){
                Log.i(Activity,weather.getAqi().getCity().getAqi());
            }
            aqiText.setText(weather.getAqi().getCity().getAqi());
            pm25Text.setText(weather.getAqi().getCity().getPm25());
        }
        String comfort = "舒适度：" + weather.getSuggestion().getComf().getTxt();
        String carWash = "洗车指数：" + weather.getSuggestion().getCw().getTxt();
        String sport = "运动建议：" + weather.getSuggestion().getSport().getTxt();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);



    }
    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        backgroudImg= (ImageView) findViewById(R.id.bing_pic_img);
    }
}
