package com.aibb.android.base.example.setup.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.startup.AppInitializer
import com.aibb.android.base.example.R
import com.aibb.android.base.example.setup.Initializer.RoomInitializer
import kotlinx.android.synthetic.main.setup_test_activity.*

class SetupTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_test_activity)

        manuallySetupBtn.setOnClickListener {
            AppInitializer.getInstance(this)
                .initializeComponent(RoomInitializer::class.java)
        }
    }
}