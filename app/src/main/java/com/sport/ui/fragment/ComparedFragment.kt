package com.sport.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sport.databinding.FragmentComparedBinding
import com.sport.ui.activity.MainActivity
import com.sport.utilities.InjectorUtils
import com.sport.viewmodels.ComparedViewModel

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午1:34
 * Description: 运动数据的展示,对比
 */
class ComparedFragment : Fragment() {

    private lateinit var viewModel: ComparedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentComparedBinding.inflate(inflater, container, false);
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideComparedViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(ComparedViewModel::class.java)
        var i = 0
        viewModel.getSportInfos().observe(viewLifecycleOwner, Observer { sportInfos ->

        })
        subscribeUi(binding)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentComparedBinding) {
        val activity = activity as MainActivity
        activity.setMainActivityActionBar(binding.toolbar)
    }
}