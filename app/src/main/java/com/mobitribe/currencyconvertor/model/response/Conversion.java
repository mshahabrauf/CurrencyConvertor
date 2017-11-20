package com.mobitribe.currencyconvertor.model.response;

import java.util.Map;

/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 11/18/17
 * Description: Conversion POJO
 */

public class Conversion {

    private String base;
    private String date;
    private Map<String,Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

}
