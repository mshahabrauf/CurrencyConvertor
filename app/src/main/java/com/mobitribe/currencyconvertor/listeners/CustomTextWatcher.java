package com.mobitribe.currencyconvertor.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.mobitribe.currencyconvertor.main.MainActivity;
import com.mobitribe.currencyconvertor.databinding.ActivityMainBinding;
import com.mobitribe.currencyconvertor.model.UserConversion;

/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 11/18/17
 * Description:
 */

public class CustomTextWatcher implements TextWatcher {

    private static final String TAG = "TextWatcher";
    public static boolean isEnable = true;
    private MainActivity.ST st;
    private ActivityMainBinding binding;

    public CustomTextWatcher(MainActivity.ST st, ActivityMainBinding binding)
    {
        this.st = st;
        this.binding = binding;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        charSequence = charSequence.toString().isEmpty()?"0":charSequence;
        if (isEnable) {
            if (this.st == MainActivity.ST.Source) {
                Double sourceValue = Double.parseDouble(charSequence.toString());
                Double targetValue = sourceValue * UserConversion.getInstance().getConversionRate();
                Log.d(TAG, "onTextChanged: TargetValue " + targetValue);

                /*boolean value false to avoid onTextChanged call when setting value*/
                isEnable = false;

                binding.targetEdittext.setText(String.format("%s", targetValue));


                /*boolean value true to allow onTextChanged call*/
                isEnable = true;
            } else {
                Double targetValue = Double.parseDouble(charSequence.toString());
                Double sourceValue = targetValue/UserConversion.getInstance().getConversionRate();
                Log.d(TAG, "onTextChanged: SourceValue " + sourceValue);

                /*boolean value false to avoid onTextChanged call when setting value*/
                isEnable = false;
                
                binding.sourceEdittext.setText(String.format("%s", sourceValue));

                 /*boolean value true to allow onTextChanged call*/
                isEnable = true;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}
