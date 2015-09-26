package com.alex.wechatmoments.Utils;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;


/**
 * Created by Chen on 2015/8/21.
 */
public class CommonUtils {

    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

    /**
     * dp convert to px
     * @param context
     * @param size
     * @return
     */
    public static int dpToPx(Context context,int size){
        float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (90 * scale + 0.5f);

        return  px;
    }


}
