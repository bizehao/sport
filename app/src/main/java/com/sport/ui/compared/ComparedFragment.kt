package com.sport.ui.compared

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.sport.databinding.FragmentComparedBinding
import com.sport.utilities.InjectorUtils

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
        var i = 0;
        viewModel.getSportInfos().observe(viewLifecycleOwner, Observer { sportInfos ->
            val m = "有没有值${++i}"
            binding.comtxt.text = m
            if (sportInfos.status == 0) {
                Log.e("数据", "" + sportInfos.data!!.size)
            }
        })

        return binding.root
    }
}