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
    fun hasFloatingWindowByView(activity: Activity): Boolean {
        return getFloatWindowViewByView(activity).isNotEmpty()
    }


    fun getFloatWindowViewByView(activity: Activity): List<View> {
        val targetDecorView = activity.window.decorView
        val acDecorViews = lifecycle.getActivities().map { it.window.decorView }.toList()
        val mView = Window.getViews().map { it }.toList()
        val targetIndex = mView.first { it == targetDecorView }
        val index = mView.indexOf(targetIndex)
        val floatView = arrayListOf<View>()
        for (i in index + 1 until mView.size) {
            if (acDecorViews.contains(mView[i])) {
                break
            }
            floatView.add(mView[i])
        }
        return floatView
    }


    fun hasFloatWindowByToken(activity: Activity): Boolean {
        // 获取目标 Activity 的 decorView
        val targetDecorView = activity.window.decorView
        // 获取目标 Activity 的 子窗口的 token
        val targetSubToken = targetDecorView.windowToken


        //  拿到 mView 集合，找到目标 Activity 所在的 index 位置
        val mView = Window.getViews().map { it }.toList()
        val targetIndex = mView.indexOfFirst { it == targetDecorView }

        // 获取 mParams 集合
        val mParams = Window.getParams()
        // 根据目标 index 从 mParams 集合中找到目标 token
        val targetToken = mParams[targetIndex].token


        // 遍历判断时，目标 Activity 自己不能包括,所以 size 需要大于 1
        return mParams
            .map { it.token }
            .filter { it == targetSubToken || it == null || it == targetToken }
            .size > 1
    }


    fun getFloatWindowViewByToken(activity: Activity): List<View> {
        // 获取目标 Activity 的 decorView
        val targetDecorView = activity.window.decorView
        // 获取目标 Activity 的 子窗口的 token
        val targetSubToken = targetDecorView.windowToken


        //  拿到 mView 集合，找到目标 Activity 所在的 index 位置
        val mView = Window.getViews().map { it }.toList()
        val targetIndex = mView.indexOfFirst { it == targetDecorView }

        // 获取 mParams 集合
        val mParams = Window.getParams()
        // 根据目标 index 从 mParams 集合中找到目标 token
        val targetToken = mParams[targetIndex].token


        val floatView = arrayListOf<View>()

        mParams.forEachIndexed { index, params ->
            val token = params.token
            // Activity 自身不参与
            if (index != targetIndex) {
                if (token == targetSubToken || token == null || token == targetToken) {
                    // 根据 index 拿到 mView 中的 View
                    floatView.add(mView[index])
                }
            }
        }
        return floatView
    }


}