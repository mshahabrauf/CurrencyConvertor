package com.mobitribe.currencyconvertor.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mobitribe.currencyconvertor.R;
import com.mobitribe.currencyconvertor.databinding.ActivityMainBinding;
import com.mobitribe.currencyconvertor.extras.ProgressLoader;
import com.mobitribe.currencyconvertor.extras.SharedPreferences;
import com.mobitribe.currencyconvertor.listeners.CustomTextWatcher;
import com.mobitribe.currencyconvertor.model.UserConversion;

import java.io.IOException;
import java.text.ParseException;

/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 11/18/17
 * Description:
 */

class BaseActivity extends AppCompatActivity {

    protected ActivityMainBinding binding;
    public SharedPreferences sharedPreferences;
    private ProgressLoader progressLoader;
    private String TAG = BaseActivity.class.getSimpleName();
    protected int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = new SharedPreferences(this);
        position = sharedPreferences.getTheme();
        selectTheme(position);
        UserConversion.getInstance(sharedPreferences.getJobSeeker());
        super.onCreate(savedInstanceState);
    }

    /**
     * @usage It use to show any message provided by the caller
     * @param view
     * @param message
     */
    public void showMessage(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * @usage it handles onFailure Response of retrofit
     * @param throwable
     * @param view
     */
    public void onFailureResponse(View view,Throwable throwable)
    {
        if (throwable instanceof IOException)
        {
            showMessage(view,getString(R.string.internet_connectivity));
        }
        else
        {
            showMessage(view,getString(R.string.some_thing_went_wrong));
        }
    }

    /**
     * @usage It calculates the conversion between currencies
     *        by conversion rate
     */
    protected void calculateConvertion() {

        double sourceValue;
        try {
            sourceValue = Double.parseDouble(binding.sourceEdittext.getText().toString());
            if (sourceValue==0)
                sourceValue=1;
        }
        catch (NumberFormatException e)
        {
            sourceValue = 1;
        }
        Double convertionRate = UserConversion.getInstance().getConversionRate();

        CustomTextWatcher.isEnable = false;
        binding.sourceEdittext.setText(sourceValue+"");
        binding.targetEdittext.setText((sourceValue*convertionRate)+"");
        CustomTextWatcher.isEnable = true;
    }

    /**
     * @usage It validates the input whether conversion between same currencies or not
     * @return boolean value
     */
    protected boolean validateTheInput() {
        if (binding.source.getSelectedItemPosition()==binding.target.getSelectedItemPosition())
        {
            showMessage(binding.getRoot(),"Conversion wouldn't take place between same currencies");
            return false;
        }
        return true;
    }

    public void showProgress()
    {
        try {
            if (progressLoader == null)
            {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(getSupportFragmentManager(),TAG);
        }
        catch (IllegalStateException e)
        {
            Log.e(TAG, "showProgress:" + e.getMessage());
        }

    }

    public void hideProgress() {
        if (progressLoader != null) {
            try {
                progressLoader.dismissAllowingStateLoss();
            } catch (Exception e) {
                Log.e(TAG, "hideProgress:" + e.getMessage());
            }
        }
    }

    /**
     * @usage It apply the theme which user select
     * @param pos
     */
    protected void selectTheme(int pos) {
        Log.d(TAG, "selectTheme: " + pos);
        if (pos==0)
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.AppThemeDark);
    }

    /**
     * @usage It restarts the activity with different theme
     * @param position
     */
    protected void restartActivity(int position) {
        sharedPreferences.putTheme(position);
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        /*Save previous information*/
        try {
            UserConversion.getInstance().setSourceValue(Double.parseDouble(binding.sourceEdittext.getText().toString()));
            UserConversion.getInstance().setTargetValue(Double.parseDouble(binding.targetEdittext.getText().toString()));
            sharedPreferences.saveJobSeeker();
        }
        catch (NumberFormatException e)
        {
            Log.e(TAG, "onPause: " + e.getMessage() );
        }
    }


    /**
     * @usage It load the view with previously saved data
     */
    protected void loadPreviouslySavedData() {

         /*Set different selection of both spinner to avoid in validation */
        binding.source.setSelection(UserConversion.getInstance().getSource());
        binding.target.setSelection(UserConversion.getInstance().getTarget());


        CustomTextWatcher.isEnable = false;
        binding.sourceEdittext.setText(String.format("%s", UserConversion.getInstance().getSourceValue()));
        binding.targetEdittext.setText(String.format("%s", UserConversion.getInstance().getTargetValue()));
        CustomTextWatcher.isEnable = true;
    }
}
