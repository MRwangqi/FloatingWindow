package com.codelang.window

import android.app.Activity
import android.app.Application
import android.view.View

/**
 * @author wangqi
 * @since 2022/8/16.
 */
object FloatingWindowManager {

    private val lifecycle by lazy {
        FloatingActivityLifecycle()
    }

    @JvmStatic
    fun init(context: Application) {
        context.registerActivityLifecycleCallbacks(lifecycle)
    }


    /**
     * WindowManagerGlobal mViews:
     *
     * 0、Ac:------
     * 1、-- Dialog
     * 2、-- Dialog
     * 3、Ac:------
     * 4、Ac:------
     */
    fun hasFloatingWindow(activity: Activity): Boolean {
        return getFloatWindowView(activity).isNotEmpty()
    }


    fun getFloatWindowView(activity: Activity): List<View> {
        val acDecorViews = lifecycle.getActivities().map { it.window.decorView }.toList()

        val winDecorViews = Window.getViews().map { it as View }.toList()

        val targetDecorView = activity.window.decorView

        val findDecorView = winDecorViews.first { it == targetDecorView }

        val index = winDecorViews.indexOf(findDecorView)

        val floatView = arrayListOf<View>()
        for (i in index + 1 until winDecorViews.size) {
            if (acDecorViews.contains(winDecorViews[i])) {
                break
            }
            floatView.add(winDecorViews[i])
        }
        return floatView
    }
}