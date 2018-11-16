package com.robin.plugin2;

import com.robin.com.ICommon;

/**
 * Created by robin on 11/16/18.
 */
public class PluginResource implements ICommon {
    @Override
    public String getString() {
        return "plugin2";
    }

    @Override
    public int getDrawable() {
        return R.mipmap.dong;
    }
}
