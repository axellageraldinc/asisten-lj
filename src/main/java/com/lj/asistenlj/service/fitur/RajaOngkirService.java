package com.lj.asistenlj.service.fitur;

import com.lj.asistenlj.model.cekongkir.RajaOngkirResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RajaOngkirService {

    @FormUrlEncoded
    @POST("cost")
    Call<RajaOngkirResponse> postCekOngkir(@Header("key") String apiKey,
                                           @Field("origin") int originId,
                                           @Field("destination") int destinationId,
                                           @Field("courier") String courier,
                                           @Field("weight") int weight);

}
