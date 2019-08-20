package com.cherepanovky.callbackit.features.settings

import android.os.Bundle
import com.cherepanovky.callbackit.R
import com.cherepanovky.callbackit.core.platform.BaseFragment

class SettingsFragment : BaseFragment() {


    override fun layoutId() = R.layout.fragment_settings
    override fun navigationLayoutId() = R.id.nav_host_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}
