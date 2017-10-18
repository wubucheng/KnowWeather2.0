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
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
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
public class ChooseFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("start ","fragment1");
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        Log.i("start ","fragment2");
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("start ","activity");
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

    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        proviceList = DataSupport.findAll(Provice.class);
        if (proviceList.size() > 0) {
            dataList.clear();
            for (Provice provice : proviceList) {
                dataList.add(provice.getProviceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVICE;
        } else {
            String url = "http://guolin.tech/api/china";
            queryFromServer(url, "province");
        }
    }

    private void queryCities() {
        titleText.setText(selectedProvice.getProviceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid=?", String.valueOf(selectedProvice.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int proviceCode = selectedProvice.getProviceCode();
            String url = "http://guolin.tech/api/china/" + proviceCode;
            Log.i("the city url is",url);
            queryFromServer(url, "city");
        }
    }

    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int proviceCode = selectedProvice.getProviceCode();
            int cityCode = selectedCity.getCityCode();
            String url = "http://guolin.tech/api/china/" + proviceCode + "/" + cityCode;
            Log.i("the couny url is",url);
            queryFromServer(url, "county");
        }


    }

    private void queryFromServer(String url, final String type) {
        showProgressDialog();
        OkHttpUtil.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                Log.i("province",responseText);
                boolean result=false;
                if("province".equals(type)){
                    result=GsonUtil.handProviceResponse(responseText);
                }else if("city".equals(type)){
                    result=GsonUtil.handleCityResponse(responseText,selectedProvice.getId());
                }else if("county".equals(type)){
                    GsonUtil.handleCountyResponse(responseText,selectedCity.getId());
                }
                if(result){
                    Log.i("result","is true");
                }

                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中");

        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
