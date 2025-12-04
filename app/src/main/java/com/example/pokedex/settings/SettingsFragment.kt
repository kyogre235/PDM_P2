package com.example.pokedex.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.pokedex.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
