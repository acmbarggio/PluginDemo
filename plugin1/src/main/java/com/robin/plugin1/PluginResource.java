package com.robin.plugin1;

import com.robin.com.ICommon;

/**
 * Created by robin on 11/16/18.
 */
public class PluginResource implements ICommon {
    @Override
    public String getString() {
        return "plugin1";
    }

    @Override
    public int getDrawable() {
        return R.mipmap.ic_launcher_round;
    }
}
