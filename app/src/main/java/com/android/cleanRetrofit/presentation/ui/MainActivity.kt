package com.android.cleanRetrofit.presentation.ui

import android.os.Bundle
import com.android.cleanRetrofit.R
import com.android.cleanRetrofit.presentation.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
