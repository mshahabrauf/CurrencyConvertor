package com.mobitribe.currencyconvertor.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mobitribe.currencyconvertor.R;
import com.mobitribe.currencyconvertor.listeners.CustomTextWatcher;
import com.mobitribe.currencyconvertor.model.response.Conversion;
import com.mobitribe.currencyconvertor.model.UserConversion;
import com.mobitribe.currencyconvertor.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private int menuCheck = 0;

    public enum ST {Source,Target};
    private String[] currencies;
    int sourceCheck = 0,targetCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currencies = getResources().getStringArray(R.array.currency_arrays);
        super.onCreate(savedInstanceState);
        bindViews();

        /*fetch data from server if doesn't have cache value*/
        if (UserConversion.getInstance().getConversionRate()==0)
            getTheResult();
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.sourceEdittext.addTextChangedListener(new CustomTextWatcher(ST.Source,binding));
        binding.targetEdittext.addTextChangedListener(new CustomTextWatcher(ST.Target,binding));
    }

    /**
     * @usage It binds view with the object
     */
    private void bindViews() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*method call to load previously saved data*/
        loadPreviouslySavedData();

        /*set listeners*/
        binding.source.setOnItemSelectedListener(this);
        binding.target.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parents, View view, int pos, long id) {
        Log.d(TAG,
                "OnItemSelectedListener : " + parents.getItemAtPosition(pos).toString());
        /*Theme Selection spinner*/
        if (parents.getId()==R.id.spinner) {
            if (++menuCheck>1)
                restartActivity(pos);
        }
        else
        {
            /*Validating the input and avoiding first selection call*/
            if (validateTheInput()&&++sourceCheck>1&&++targetCheck>1) {

                /*Source value selection spinner*/
                if (view.getId() == R.id.source) {
                    Log.d(TAG, "onItemSelected: SourceValue: " + pos);
                    UserConversion.getInstance().setSource(pos);
                }
                /*Target value selection spinner*/
                else {
                    Log.d(TAG, "onItemSelected: TargetValue: " + pos);
                    UserConversion.getInstance().setTarget(pos);
                }
                getTheResult();
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        /*useless*/
    }

    /**
     * @usage Api call to fetch the converison rate
     */
    private void getTheResult() {

        showProgress();
        RestClient.getRestAdapter().getConverisonResult(currencies[UserConversion.getInstance().getSource()],
                currencies[UserConversion.getInstance().getTarget()])
                .enqueue(new Callback<Conversion>() {
                    @Override
                    public void onResponse(Call<Conversion> call, Response<Conversion> response) {
                        hideProgress();
                        if (response.isSuccessful()&&response.body()!=null)
                        {
                            UserConversion.getInstance()
                                    .setConversionRate(response.body()
                                            .getRates().get(currencies[UserConversion.getInstance()
                                                    .getTarget()]));
                            sharedPreferences.saveJobSeeker();
                            calculateConvertion();
                        }
                    }

                    @Override
                    public void onFailure(Call<Conversion> call, Throwable t) {
                        hideProgress();
                        onFailureResponse(binding.getRoot(),t);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(MainActivity.this );
        return true;
    }


}
