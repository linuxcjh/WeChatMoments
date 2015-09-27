package com.alex.wechatmoments;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.alex.wechatmoments.presenter.BasePresenter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chen on 25/9/15.
 */
public class TestClass extends InstrumentationTestCase {


    String TAG = "TestClass";
    BasePresenter basePresenter = new BasePresenter();

    public void testGetUserInfo() throws Exception {
        final int expected = 2;
        basePresenter.apiHttp.getUserInfo("jsmith", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, s);
                final int reality = 2;

                assertEquals(expected,reality);
            }

            @Override
            public void failure(RetrofitError error) {
                final int reality = 1;
                Log.d(TAG, error.getMessage());
                assertEquals(expected,reality);
            }
        });

    }


}
