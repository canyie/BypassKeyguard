package top.canyie.bypasskeyguard;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author canyie
 */
public class LockTaskActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        if (dpm.isDeviceOwnerApp(getPackageName())) {
            Set<String> pkgs = new HashSet<>();
            List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(0);
            for (ApplicationInfo app : apps) {
                pkgs.add(app.packageName);
            }
            dpm.setLockTaskPackages(new ComponentName(this, DeviceAdmin.class),
                    pkgs.toArray(new String[pkgs.size()]));
        } else finish();
    }

    @Override protected void onResume() {
        super.onResume();
        startLauncher();
    }

    void startLauncher() {
        try {
            startLockTask();
            startActivity(new Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_HOME)
                    .addCategory(Intent.CATEGORY_DEFAULT));
            return;
        } catch (Exception e) {
            // try again in 100ms
        }
        getWindow().getDecorView().postDelayed(this::startLauncher, 100);
    }
}
