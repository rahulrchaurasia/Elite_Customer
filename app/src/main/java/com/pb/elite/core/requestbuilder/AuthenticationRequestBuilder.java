package com.pb.elite.core.requestbuilder;

import com.pb.elite.core.RetroRequestBuilder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rajeev Ranjan on 25/01/2018.
 */

public class AuthenticationRequestBuilder extends RetroRequestBuilder {

    public AuthenticationRequestBuilder.AuthenticationNetworkService getService() {

        return super.build().create(AuthenticationRequestBuilder.AuthenticationNetworkService.class);
    }

    public interface AuthenticationNetworkService {

    }
}
