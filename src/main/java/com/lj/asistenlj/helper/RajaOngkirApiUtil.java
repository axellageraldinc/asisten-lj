package com.lj.asistenlj.helper;

import com.lj.asistenlj.service.fitur.RajaOngkirService;

public class RajaOngkirApiUtil {
    public static final String BASE_URL = "https://api.rajaongkir.com/starter/";

    public static RajaOngkirService getRajaOngkirService() {
        return RetrofitClient.getClient(BASE_URL).create(RajaOngkirService.class);
    }
}
