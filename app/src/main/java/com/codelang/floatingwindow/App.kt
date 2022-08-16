package com.codelang.floatingwindow

import android.app.Application
import com.codelang.window.FloatingWindowManager

/**
 * @author wangqi
 * @since 2022/8/16.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FloatingWindowManager.init(this)
    }
}