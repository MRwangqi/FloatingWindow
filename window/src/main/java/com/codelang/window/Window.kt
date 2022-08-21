package com.codelang.window

/**
 * @author wangqi
 * @since 2022/8/16.
 */

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.WindowManager

internal object Window {

    private val windowManagerClass by lazy(LazyThreadSafetyMode.NONE) {
        val className =
            "android.view.WindowManagerGlobal"
        try {
            Class.forName(className)
        } catch (ignored: Throwable) {
            Log.w("WindowManagerSpy", ignored)
            null
        }
    }

    private val windowManagerInstance by lazy(LazyThreadSafetyMode.NONE) {
        windowManagerClass?.let { windowManagerClass ->
            val methodName =
                "getInstance"
            windowManagerClass.getMethod(methodName).invoke(null)
        }
    }


    private val mViewsField by lazy(LazyThreadSafetyMode.NONE) {
        windowManagerClass?.let { windowManagerClass ->
            windowManagerClass.getDeclaredField("mViews").apply { isAccessible = true }
        }
    }


    private val mParams by lazy(LazyThreadSafetyMode.NONE) {
        windowManagerClass?.let { windowManagerClass ->
            windowManagerClass.getDeclaredField("mParams").apply { isAccessible = true }
        }
    }



    @SuppressLint("PrivateApi", "ObsoleteSdkInt", "DiscouragedPrivateApi")
    fun getViews(): List<View> {
        try {
            windowManagerInstance?.let { windowManagerInstance ->
                mViewsField?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<View>
                }
            }
        } catch (ignored: Throwable) {
            Log.w("WindowManagerSpy", ignored)
        }
        return arrayListOf()
    }

    @SuppressLint("PrivateApi", "ObsoleteSdkInt", "DiscouragedPrivateApi")
    fun getParams(): List<WindowManager.LayoutParams> {
        try {
            windowManagerInstance?.let { windowManagerInstance ->
                mParams?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<WindowManager.LayoutParams>
                }
            }
        } catch (ignored: Throwable) {
            Log.w("WindowManagerSpy", ignored)
        }
        return arrayListOf()
    }

}