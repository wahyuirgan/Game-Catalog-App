package com.hokagelab.gamecatalogapp.setting.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.hokagelab.gamecatalogapp.setting.R
import com.hokagelab.gamecatalogapp.setting.broadcast.AlarmReceiver
import com.hokagelab.gamecatalogapp.setting.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarMain)
        supportActionBar?.title = getString(R.string.title_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.setting_container, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private var keyReminder: String = ""
        private var reminderActivePreference: SwitchPreference? = null
        private var alarmReceiver: AlarmReceiver? = null

        private var localizationChange: Preference? = null

        companion object {
            private const val reminderNine = "09:00"
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.preferences)
            alarmReceiver = AlarmReceiver()
            initSharedPreference()
            setSharedPreference()

            localizationChange = findPreference(getString(R.string.title_language))

            val localizationIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            localizationChange?.intent = localizationIntent
        }

        private fun initSharedPreference() {
            keyReminder = resources.getString(R.string.title_reminder)
            reminderActivePreference =
                findPreference<SwitchPreference>(keyReminder) as SwitchPreference
        }

        private fun setSharedPreference() {
            val sharedPreferences = preferenceManager.sharedPreferences
            reminderActivePreference?.isChecked = sharedPreferences.getBoolean(keyReminder, false)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            key: String
        ) {
            if (key == keyReminder) {
                if (reminderActivePreference?.isChecked == true) {
                    activity?.let {
                        alarmReceiver?.setActiveAlarm(
                            it, getString(R.string.reminder_title),
                            reminderNine, getString(R.string.reminder_message)
                        )
                    }
                } else {
                    activity?.let { alarmReceiver?.deactivatedAlarm(it) }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}