package com.mobitribe.currencyconvertor.model;

/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 11/18/17
 * Description: This class is used to save data in SharedPreferences
 */

public class UserConversion {


    private static UserConversion userConversion;
    private Integer source;
    private Integer target;
    private Double sourceValue;
    private Double targetValue;
    private Double conversionRate;

    public Integer getSource() {
        return source==null?0:source;
    }

    public static UserConversion getInstance() {
        if (userConversion == null)
            userConversion = new UserConversion();
        return userConversion;
    }
    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target==null?1:target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Double getTargetValue() {
        return targetValue==null?0:targetValue;
    }

    public void setTargetValue(Double targetValue) {
        this.targetValue = targetValue;
    }

    public static void getInstance(UserConversion userConversion1) {
        if (userConversion==null)
        {
            userConversion = userConversion1;
        }

    }

    public Double getSourceValue() {
        return sourceValue == null?0:sourceValue;
    }

    public void setSourceValue(Double sourceValue) {
        this.sourceValue = sourceValue;
    }

    public Double getConversionRate() {
        return conversionRate==null?0:conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
