package com.sport.common.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sport.common.callback.PermissionListener
import com.sport.utilities.ActivityCollector
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.ArrayList
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.view.Window.ID_ANDROID_CONTENT
import android.opengl.ETC1.getHeight
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.R
import android.graphics.Color
import androidx.core.view.marginTop
import com.sport.utilities.AndroidVersion
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


/**
 * 应用程序中所有Activity的基类。
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    /**
     * 判断当前Activity是否在前台
     */
    protected var isActive: Boolean = false

    /**
     * 当前Activity的实例。
     */
    protected var activity: Activity? = null;

    /**
     * 当前activity的弱引用
     */
    private var weakRefActivity: WeakReference<Activity>? = null

    /**
     * 权限监听器
     */
    private var mListener: PermissionListener? = null

    /**
     * 用来判断是否重复添加了layout
     */
    private var onlyLayoutSet = 0;

    /**
     * 是否已经设置了跟布局监听器
     */
    private var keyboardListenersAttached: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        weakRefActivity = WeakReference(this)
        //添加进仓库里
        ActivityCollector.add(weakRefActivity)
    }

    override fun setContentView(layoutResID: Int) {
        if (++onlyLayoutSet == 3) {
            throw Exception("setContentView() and setLayout() only use one")
        }
        super.setContentView(layoutResID)
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.remove(weakRefActivity)
    }

    /**
     * 设置布局,获取 binding
     */
    fun <T : ViewDataBinding> setLayout(layout: Int): T {
        if (++onlyLayoutSet == 3) {
            throw Exception("setContentView() and setLayout() only use one")
        }
        return DataBindingUtil.setContentView(this, layout)
    }

    /**
     * 将状态栏设置成透明。(只适配Android 5.0以上系统的手机)?。
     */
    protected fun transparentStatusBar() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

    }

//    protected open fun setupViews() {
//
//    }

    /**
     * 检查和处理运行时权限，并将用户授权的结果通过PermissionListener进行回调。
     *
     * @param permissions
     * 要检查和处理的运行时权限数组
     * @param listener
     * 用于接收授权结果的监听器
     */
    protected fun handlePermissions(permissions: Array<String>?, listener: PermissionListener) {
        if (permissions == null || activity == null) {
            return
        }
        mListener = listener
        val requestPermissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(permission)
            }
        }
        if (!requestPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity!!, requestPermissionList.toTypedArray(), 1)
        } else {
            listener.onGranted()
        }
    }
}