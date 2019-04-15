package com.sport.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.sport.utilities.InjectorUtils
import com.sport.databinding.FragmentSportBinding
import com.sport.ui.activity.MainActivity
import com.sport.viewmodels.SportViewModel
import kotlin.collections.ArrayList

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午1:34
 * Description: 运动界面
 */
class SportFragment : Fragment() {

    private lateinit var viewModel: SportViewModel

    private lateinit var pagerAdapter: Adapter

    private lateinit var mTabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSportBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideSportViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(SportViewModel::class.java)
        subscribeUi(binding)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentSportBinding) {
        val activity = activity as MainActivity
        activity.setMainActivityActionBar(binding.toolbar)
        pagerAdapter = Adapter(childFragmentManager)
        pagerAdapter.addFragment(DataFragment(),"数据")
        pagerAdapter.addFragment(IndexFragment(),"主页")
        pagerAdapter.addFragment(PlanFragment(),"计划")
        binding.viewpager.adapter = pagerAdapter
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.currentItem = 1
        binding.tabs.setupWithViewPager(binding.viewpager)
        binding.tabs.addOnTabSelectedListener(object : TabLayout.ViewPagerOnTabSelectedListener(binding.viewpager){
            override fun onTabSelected(tab: TabLayout.Tab?) {
                super.onTabSelected(tab)
            }
        })
    }

    internal class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragments = ArrayList<Fragment>()
        private val mFragmentTitles = java.util.ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }
}