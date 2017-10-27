package com.example.giphy;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by muehlemann on 10/25/17.
 *
 */
public class ChainBuilder {

    private Request mRequest;
    private Response mResponse;

    public ChainBuilder() {
        mRequest = new Request.Builder()
                .url("http://www.potatotrain.com/")
                .build();
    }

    public ChainBuilder withRequest(Request request) {
        mRequest = request;
        return this;
    }

    public ChainBuilder withResponse(Response response) {
        mResponse = response;
        return this;
    }

    public Interceptor.Chain build() {
        return new TestChain(mRequest, mResponse);
    }

    private class TestChain implements Interceptor.Chain {

        private Request mRequest;
        private Response mResponse;

        TestChain(Request request, Response response) {
            mRequest = request;
            mResponse = response;
        }

        @Override
        public Request request() {
            return mRequest;
        }

        @Override
        public Response proceed(Request request) throws IOException {
            if (mResponse != null) {
                return mResponse;
            } else {
                return new Response.Builder()
                        .protocol(Protocol.HTTP_2)
                        .request(request)
                        .code(200)
                        .build();
            }
        }

        @Override
        public Connection connection() {
            return null;
        }
    }
}