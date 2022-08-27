package com.codelang.floatingwindow

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.codelang.window.FloatingWindowManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPage).setOnClickListener {
            onNextPage(it)
        }


        findViewById<Button>(R.id.btnShow).setOnClickListener {
            onShow(it)
        }
        findViewById<Button>(R.id.btnDialog).setOnClickListener {
            onShowDialog(it)
        }

        findViewById<Button>(R.id.btnPopWindow).setOnClickListener {
            onShowPopWindow(it)
        }

        findViewById<Button>(R.id.btnSysWindow).setOnClickListener {
            onSysWindow()
        }

        findViewById<Button>(R.id.btnSysWindowPermission).setOnClickListener {
            onSysWindowPermission()
        }


    }


    private fun onNextPage(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun onShow(view: View) {
        getFloatingWindow()
    }


    private fun onShowDialog(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Dialog1")
            .setNegativeButton("确定") { _, _ ->
            }.setPositiveButton("取消") { dialog, _ ->
            }
            .setCancelable(false)
            .create()
            .show()


        AlertDialog.Builder(this)
            .setTitle("Dialog2")
            .setNegativeButton("确定") { _, _ ->
            }.setPositiveButton("取消") { dialog, _ ->
            }
            .setCancelable(false)
            .create()
            .show()

        getFloatingWindow()
    }

    private fun onShowPopWindow(view: View) {
        val contentView: View =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.popuplayout, null)
        val popWnd = PopupWindow(this)
        popWnd.contentView = contentView
        popWnd.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.showAsDropDown(view)

        getFloatingWindow()
    }


    private fun onSysWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }

    private fun onSysWindow() {
        val wm = getSystemService(
            WINDOW_SERVICE
        ) as WindowManager

        //设置弹窗的宽高
        val para = WindowManager.LayoutParams().apply {
            //设置大小 自适应
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
        }

        //获取要显示的View
        val mView: View = LayoutInflater.from(this).inflate(
            R.layout.popuplayout, null
        )
        //单击 View 关闭弹窗
        mView.setOnClickListener {
            wm.removeView(mView)
        }
        //显示弹窗
        wm.addView(mView, para)

        getFloatingWindow()
    }


    private fun getFloatingWindow() {
        // todo 1、是否有浮窗 window，通过 DecorView 来判断
        val hasFloatingWindow = FloatingWindowManager.hasFloatingWindowByView(this)
        Log.i("window", "hasFloatingWindowByView ---->${hasFloatingWindow}")
        FloatingWindowManager.getFloatWindowViewByView(this).forEach {
            Log.i("window", "floatWindowViewByView =====> $it")
        }

        // todo 2、是否有浮窗 window，通过 token 来判断
        val hasFloatingToken = FloatingWindowManager.hasFloatWindowByToken(this)
        Log.i("window", "hasFloatingWindowByToken ---->${hasFloatingToken}")
        FloatingWindowManager.getFloatWindowViewByToken(this).forEach {
            Log.i("window", "floatWindowViewByToken =====> $it")
        }

    }


    override fun onStop() {
        super.onStop()

//        // text
//        AlertDialog.Builder(this)
//            .setTitle("Dialog2")
//            .setNegativeButton("确定") { _, _ ->
//            }.setPositiveButton("取消") { dialog, _ ->
//            }
//            .setCancelable(false)
//            .create()
//            .show()
//
//        println("-----------Main------------")
//        getFloatingWindow()
    }
}