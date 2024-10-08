package com.threethan.launcher.activity.chainload;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.threethan.launchercore.lib.DelayLib;
import com.threethan.launchercore.util.Platform;

import java.util.Objects;

// These activities are used for advanced custom window sizes

public class ChainLoadActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        ApplicationInfo launchApp = Objects.requireNonNull(extras.getParcelable("app"));

        // Get normal launch intent
        PackageManager pm = getPackageManager();
        final Intent normalIntent = pm.getLaunchIntentForPackage(launchApp.packageName);
        if (normalIntent != null)
            normalIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
                    | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(normalIntent);
        if (Platform.supportsNewVrOsMultiWindow()) {
            Intent relaunch = pm.getLaunchIntentForPackage(getPackageName());
            DelayLib.delayed(() -> startActivity(relaunch), 1050);
        }
        finishAffinity();
    }
}