package com.robin.plugindemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import dalvik.system.DexClassLoader;

/**
 * Created by robin on 11/16/18.
 */
public class AssetsUtils {
    public static Map<String, PluginInfo> pluginInfos = new HashMap<>();
    public static AssetManager sAssetManager;
    public static Resources sResources;

    public static void addAssetPath(Context context, String apkName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, pluginInfos.get(apkName).getDexPath());

            sAssetManager = assetManager;
            sResources = new Resources(assetManager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    /**
     * 构造apk对应的classLoader
     *
     * @param context
     * @param apkName
     */
    public static void extractInfo(Context context, String apkName) {
        File apkPath = context.getFileStreamPath(apkName);
        DexClassLoader dexClassLoader = new DexClassLoader(
                apkPath.getAbsolutePath(),
                context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(),
                null,
                context.getClassLoader());
        PluginInfo pluginInfo = new PluginInfo(apkPath.getAbsolutePath(), dexClassLoader);
        pluginInfos.put(apkName, pluginInfo);
    }

    public static PluginInfo getPluginInfo(String apkName){
        return pluginInfos.get(apkName);
    }
}
