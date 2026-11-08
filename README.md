## pocket_lock

Pocket detection (pocket lock) for LineageOS, ported from crDroid 16.0.
A pocket-mode safe-door long press on power dismisses the overlay.

Contains:

- `patches/frameworks/base/0001-base-introduce-pocket-lock.patch`
  PocketService + PhoneWindowManager/KeyguardUpdateMonitor hooks + resources.
  Device-agnostic: squashes crDroid's 5 pocket commits
  (a59752dac, 6f91f4c30, ca1a3586d, a746d7c9f, d290f141d), dropping their
  unrelated PA `safeMode` refactor and commented-out dead code. Applied as
  commits by `vendorsetup.sh` whenever `build/envsetup.sh` is sourced
  (`repo sync` discards them, so re-source it after syncing).
- `apps/PocketTweaks/`
  Platform-signed system app (launcher icon) toggling `Settings.System.pocket_judge`.
  Independent of LineageParts/Settings sources, so it survives upstream syncs.

### Install

    <!-- local_manifests -->
    <project name="YOUR/pocket_lock" path="vendor/pocketlock" />

In the device mk:

    $(call inherit-product-if-exists, vendor/pocketlock/pocket.mk)

### Device-specific configuration

The framework default judges pocket state with the proximity sensor plus
the light sensor (`config_pocketUseLightSensor`). Devices whose sensor HAL
exposes a vendor pocket stream (wake-up capable, `isWakeUpSensor()`) should
prefer it via the device frameworks overlay — that is far more reliable in
-pocket/doze:

    <!-- Xiaomi (SLPI/QSH sensors HAL): -->
    <string name="config_pocketJudgeVendorSensorName" translatable="false">xiaomi.sensor.nonui</string>
    <item name="config_pocketJudgeVendorSensorValue" format="float" type="dimen">2.0</item>

Check what the device exposes with:

    adb shell dumpsys sensorservice | grep -iE "pocket|nonui|wake"

### Enable / debug

Toggle: Pocket Tweaks app, or

    adb shell settings put system pocket_judge 1

State dump (enabled flag, sensor states, which path is in use):

    adb shell dumpsys pocket

All original code stays Apache-2.0 with crDroid/PA authorship preserved in
the patch metadata.
