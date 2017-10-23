package cn.devshare.knowweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.devshare.knowweather.MainActivity;
import cn.devshare.knowweather.R;
import cn.devshare.knowweather.WeatherActivity;
import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;
import cn.devshare.knowweather.presenter.AreaPresenter;
import cn.devshare.knowweather.presenter.IAreaPresenter;
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
import cn.devshare.knowweather.view.IAreaView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.fragment
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/17 20:59
 */
public class ChooseFragment extends Fragment implements IAreaView {

    public static final int LEVEL_PROVICE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    public static final String PROVICE="province";
    public static final String CITY="city";
    public static final String COUNTY="county";

    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    private List<Provice> proviceList;
    private List<City> cityList;
    private List<County> countyList;

    private Provice selectedProvice;
    private City selectedCity;
    private County selectedCounty;

    private int currentLevel;


    private IAreaPresenter areaPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);

        areaPresenter=new AreaPresenter(this);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVICE) {
                    selectedProvice = proviceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                }else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countyList.get(position).getCountyName();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity weatherActivity= (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.swipeRefreshLayout.setRefreshing(true);
                        weatherActivity.getWeatherData(weatherId);
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();

    }



    @Override
    public void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        areaPresenter.queryProvinces();
    }

    @Override
    public void queryCities() {
        titleText.setText(selectedProvice.getProviceName());
        backButton.setVisibility(View.VISIBLE);
        int proviceCode = selectedProvice.getProviceCode();
        areaPresenter.queryCities(selectedProvice);
    }

    @Override
    public void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        int proviceCode = selectedProvice.getProviceCode();
        int cityCode = selectedCity.getCityCode();
        areaPresenter.queryCounties(selectedProvice,selectedCity);
    }

    @Override
    public void showProgressDialog() {
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中");

        }
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showProvince(List<Provice> provices) {
        dataList.clear();
        for (Provice provice : proviceList) {
            dataList.add(provice.getProviceName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_PROVICE;

    }

    @Override
    public void showCities(List<City> cities) {
        dataList.clear();
        for (City city : cityList) {
            dataList.add(city.getCityName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_CITY;
    }

    @Override
    public void showCounties(List<County> counties) {
        dataList.clear();
        for (County county : countyList) {
            dataList.add(county.getCountyName());
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
        currentLevel = LEVEL_COUNTY;
    }
}
