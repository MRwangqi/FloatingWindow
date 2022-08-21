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

        val winDecorViews = Window.getViews().map { it }.toList()

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


    fun hasFloatWindowParams(activity: Activity): Boolean {
        val tokens = Window.getParams().map { it.token }


        // todo 拿到 Activity 的 token
        val winDecorViews = Window.getViews().map { it }.toList()
        val targetDecorView = activity.window.decorView
        val findDecorViewIndex = winDecorViews.indexOfFirst { it == targetDecorView }
        val token = tokens[findDecorViewIndex]


        // todo 子窗口的 token
        val subToken = targetDecorView.windowToken

        println("Activity token---->" + token)
        println("Activity sub token---->" + subToken)

        tokens.forEach {
            println("mParams token---->" + it)
        }

        // Activity 自己不能包括
        return tokens.filter { it != null && (it == token || it == subToken) }.size > 1
    }
}