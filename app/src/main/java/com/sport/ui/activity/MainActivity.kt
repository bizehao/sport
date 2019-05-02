package com.sport.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.sport.R
import com.sport.common.ui.BaseActivity
import com.sport.databinding.ActivityMainBinding
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.SportExecutors
import com.sport.utilities.USER_CURRENT_ITEM
import com.sport.viewmodels.IndexViewModel
import com.sport.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 */
class MainActivity : BaseActivity() {
    //控制每一个fragment的标题头
    private lateinit var appBarConfiguration: AppBarConfiguration
    //控制fragment的控制器
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止屏幕熄灭
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val index = SharePreferencesUtil.read(USER_CURRENT_ITEM, 0)
        viewModel.currentPosition.value = index
        viewModel.nextPosition.value = index+1
        viewModel.currentPosition.observe(this, Observer {
            SportExecutors.diskIO.execute {
                SharePreferencesUtil.save(USER_CURRENT_ITEM, it)
            }
        })

        val binding: ActivityMainBinding = setLayout(R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // 设置ActionBar
        //setSupportActionBar(binding.toolbar);
        //setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        binding.navigationView.setupWithNavController(navController)

        transparentStatusBar()

    }


    //加载fragment
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //重写返回按钮事件
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setMainActivityActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar);
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    companion object {

        private const val TAG = "MainActivity"

        /**
         * 跳转进入当前Activity
         */
        fun actionStart(content: Activity) {
            val intent = Intent(content, MainActivity::class.java)
            content.startActivity(intent)
        }
    }
}
