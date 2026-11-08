/*
 * Copyright (C) 2026 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.myghi63.pockettweaks;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.provider.Settings;

public class MainActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_POCKET_JUDGE = "pocket_judge";

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        final SwitchPreference pocketJudge = (SwitchPreference) findPreference(KEY_POCKET_JUDGE);
        pocketJudge.setChecked(isPocketJudgeEnabled());
        pocketJudge.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (KEY_POCKET_JUDGE.equals(preference.getKey())) {
            setPocketJudgeEnabled((Boolean) newValue);
            preference.setChecked((Boolean) newValue);
            return true;
        }
        return false;
    }

    private boolean isPocketJudgeEnabled() {
        return Settings.System.getInt(getContentResolver(), KEY_POCKET_JUDGE, 0) == 1;
    }

    private void setPocketJudgeEnabled(boolean enabled) {
        Settings.System.putInt(getContentResolver(), KEY_POCKET_JUDGE, enabled ? 1 : 0);
    }
}
