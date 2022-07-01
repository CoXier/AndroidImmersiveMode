package com.example.androidimmersivemode

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv).setOnClickListener {
           val dialog =  AlertDialog.Builder(this)
                .setTitle("Dialog")
                .setMessage("This is a dialog") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
               .show()
            hideSystemBars(dialog.window!!.decorView, WindowInsetsCompat.Type.navigationBars())
        }

        hideStatusBarAndNavigationBar()
//        transparentStatusBarAndExtend()
//        hideSystemBars(window.decorView, WindowInsetsCompat.Type.navigationBars())
    }

    private fun transparentStatusBarAndExtend() {
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { view, windowInsets ->
            // 给整体内容设置一个 paddingTop，paddingTop 的值是 statusBar 的高度
            val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, insets.top, 0, 0)
            WindowInsetsCompat.CONSUMED
        }
    }

private fun hideStatusBarAndNavigationBar() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    // 设置全屏
    hideSystemBars(window.decorView, WindowInsetsCompat.Type.systemBars())
    // 处理异形屏
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lp = window.attributes
        lp.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes = lp
    }
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { view, windowInsets ->
        // 给整体内容设置一个 paddingTop，paddingTop 的值是 statusBar 的高度
        val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.statusBars())
        view.setPadding(0, insets.top, 0, 0)
        WindowInsetsCompat.CONSUMED
    }
}


    private fun hideSystemBars(decorView: View, @WindowInsetsCompat.Type.InsetsType types: Int) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(decorView) ?: return
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(types)
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

}