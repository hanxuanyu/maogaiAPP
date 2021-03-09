package com.hxuanyu.tools.maogai.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.hxuanyu.tools.maogai.Activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static String getJson(Context mContext, String filename) {
        InputStream mInputStream = null;
        String resultString = "";
        try {
            mInputStream = mContext.getAssets().open(filename);
            byte[] buffer = new byte[mInputStream.available()];
            mInputStream.read(buffer);
            resultString = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                mInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString.toString();
    }

}
