package com.robin.plugindemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File[] files = getFilesDir().listFiles();
        for (File file : files) {
            AssetsUtils.pluginInfos.put(file.getAbsolutePath(),new PluginInfos(file.getAbsolutePath(), DexClassLoader.getSystemClassLoader()));
        }


        move("plugin1.apk", this);
        move("plugin2.apk", this);

        AssetsUtils.addAssetPath(this, "plugin1.apk");
        AssetsUtils.addAssetPath(this, "plugin2.apk");
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


        } else if (i == R.id.button3) {

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
