package com.codelang.floatingwindow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun onNextPage(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun onShow(view: View) {
        postFloatingWindow(view)
    }


    private fun onShowDialog(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Dialog1")
            .setNegativeButton("确定") { _, _ ->
            }.setPositiveButton("取消"){ dialog, _ ->
            }
            .setCancelable(false)
            .create()
            .show()


        AlertDialog.Builder(this)
            .setTitle("Dialog2")
            .setNegativeButton("确定") { _, _ ->
            }.setPositiveButton("取消"){ dialog, _ ->
            }
            .setCancelable(false)
            .create()
            .show()

        postFloatingWindow(view)
    }

    private fun onShowPopWindow(view: View) {
        val contentView: View =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.popuplayout, null)
        val popWnd = PopupWindow(this)
        popWnd.contentView = contentView
        popWnd.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popWnd.showAsDropDown(view)

        postFloatingWindow(view)
    }


    private fun postFloatingWindow(view: View) {
        view.postDelayed({
            val hasFloatingWindow = FloatingWindowManager.hasFloatingWindow(this)

            Log.i("FloatingWindowManager", "hasFloatingWindow ---->${hasFloatingWindow}")
            val views = FloatingWindowManager.getFloatWindowView(this)

            views.forEach {
                Log.i("FloatingWindowManager", "FloatWindowView = ${it}")
            }

        }, 500)

    }
}