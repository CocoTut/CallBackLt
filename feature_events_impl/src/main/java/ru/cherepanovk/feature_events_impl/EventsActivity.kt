package ru.cherepanovk.feature_events_impl

import android.os.Bundle
import ru.cherepanovk.core.di.ComponentManager
import ru.cherepanovk.core.platform.BaseActivity

class EventsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)


    }

    override fun inject(componentManager: ComponentManager) {

    }
}
