package cn.devshare.knowweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import cn.devshare.knowweather.model.IWeatherModel;
import cn.devshare.knowweather.presenter.IWeatherPresenter;
import cn.devshare.knowweather.presenter.WeatherPresenter;
import cn.devshare.knowweather.service.AutoUpdateService;
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
import cn.devshare.knowweather.view.IWeatherView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements IWeatherView {

    public static final String Activity="WeatherActivity";

    private ScrollView weatherLayout;

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

    public SwipeRefreshLayout swipeRefreshLayout;

    public DrawerLayout drawerLayout;
    private Button navButton;

    private IWeatherPresenter weatherPresenter;

    String weatherId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        weatherId=getIntent().getStringExtra("weather_id");
        initView();
        getWeatherData(weatherId);
    }

    public void getWeatherData(String weatherId) {
        Log.i("the wether id is",weatherId);
        weatherPresenter.requestWeather(weatherId,this);
    }


    private void initView() {
        weatherPresenter=new WeatherPresenter(this);

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

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton= (Button) findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        weatherLayout.setVisibility(View.INVISIBLE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherPresenter.requestWeather(weatherId,WeatherActivity.this);

            }
        });

        Intent intent=new Intent(WeatherActivity.this, AutoUpdateService.class);
        startService(intent);
    }



    /**
     *  该方法调用在presenter层
     *  作用：显示数据到界面上
     */
    @Override
    public void showWeater(Weather weather) {
        String cityName=weather.getBasic().getCity();
        String updateTime=weather.getBasic().getUpdate().getLoc().split(" ")[1];//获取时分
        String degree=weather.getNow().getTmp()+"℃";
        String weatherInfo=weather.getNow().getCond().getTxt();
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


    @Override
    public void showBingPic(final String bing) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(WeatherActivity.this).load(bing).into(backgroudImg);
            }
        });

    }


    //刷新
    @Override
    public void refreshPic() {

    }

    @Override
    public void closeRefresh(String info) {
        swipeRefreshLayout.setRefreshing(false);
        //Toast.makeText(this,info,Toast.LENGTH_LONG).show();
    }

    @Override
    public void toast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
