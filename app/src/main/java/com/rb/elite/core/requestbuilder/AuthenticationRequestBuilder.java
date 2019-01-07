package com.rb.elite.core.requestbuilder;

import com.rb.elite.core.RetroRequestBuilder;

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
