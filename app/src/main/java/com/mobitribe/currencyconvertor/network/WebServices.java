package com.mobitribe.currencyconvertor.network;

import com.mobitribe.currencyconvertor.model.response.Conversion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Muhammad Shahab
 * Date: 5/5/17.
 * Description: Interface that contains the services
 */

public interface WebServices {

    @GET(ApiEndPoints.convert)
    Call<Conversion> getConverisonResult(@Query("base") String base,@Query("symbols") String symbol);
}
