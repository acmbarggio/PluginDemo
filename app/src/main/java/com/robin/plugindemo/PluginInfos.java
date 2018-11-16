package com.robin.plugindemo;

import android.content.Context;
import android.util.Log;

import dalvik.system.DexClassLoader;

/**
 * Created by robin on 11/16/18.
 */
class PluginInfos {
    private String dexPath;
    private DexClassLoader classLoader;

    public PluginInfos(String dexPath, DexClassLoader dexClassLoader) {
        this.dexPath = dexPath;
        this.classLoader = dexClassLoader;
    }


    public String getDexPath() {
        return dexPath;
    }

    public DexClassLoader getClassLoader() {
        return classLoader;
    }

}
