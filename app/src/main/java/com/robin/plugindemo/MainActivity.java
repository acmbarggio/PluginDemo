package com.robin.plugindemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robin.com.ICommon;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    final static String APK_1 = "plugin1.apk";
    final static String APK_2 = "plugin2.apk";

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        move(APK_1, this);
        move(APK_2, this);


        AssetsUtils.extractInfo(this, APK_1);
        AssetsUtils.extractInfo(this, APK_2);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
    }


    private void move(String apkName, Context context) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = am.open(apkName);
            File extractFile = context.getFileStreamPath(apkName);
            fos = new FileOutputStream(extractFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(is);
            closeSilently(fos);
        }
    }

    private void closeSilently(Closeable is) {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPlugin(View view) {
        int i = view.getId();
        if (i == R.id.button2) {
            PluginInfo pluginInfo = AssetsUtils.getPluginInfo(APK_1);
            AssetsUtils.addAssetPath(this, APK_1);


            DexClassLoader classLoader = pluginInfo.getClassLoader();
            try {
                Class PluginResource = classLoader.loadClass("com.robin.plugin1.PluginResource");
                ICommon pluginObject = (ICommon) PluginResource.newInstance();

                textView.setText(pluginObject.getString());
                imageView.setImageResource(pluginObject.getDrawable());
            } catch (ClassNotFoundException e) {
                Log.v("robin", e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else if (i == R.id.button3) {
            PluginInfo pluginInfo = AssetsUtils.getPluginInfo(APK_2);
            AssetsUtils.addAssetPath(this, APK_2);


            DexClassLoader classLoader = pluginInfo.getClassLoader();
            try {
                Class PluginResource = classLoader.loadClass("com.robin.plugin2.PluginResource");
                ICommon pluginObject = (ICommon) PluginResource.newInstance();

                textView.setText(pluginObject.getString());
                imageView.setImageResource(pluginObject.getDrawable());
            } catch (ClassNotFoundException e) {
                Log.v("robin", e.getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @Override
    public AssetManager getAssets() {
        return AssetsUtils.sAssetManager == null ? super.getAssets() : AssetsUtils.sAssetManager;
    }

    @Override
    public Resources getResources() {
        return AssetsUtils.sResources == null ? super.getResources() : AssetsUtils.sResources;
    }
}
