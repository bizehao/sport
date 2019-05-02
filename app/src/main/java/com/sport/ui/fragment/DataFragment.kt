package com.sport.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sport.R
import com.sport.databinding.FragmentDataBinding
import com.sport.natives.DataHandle
import com.sport.utilities.MyMediaPlayer
import timber.log.Timber

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:52
 * Description:
 */
class DataFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentDataBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        subscribeUi(binding, context)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentDataBinding, context: Context) {
        binding.test1.setOnClickListener {
            Timber.e("测试一下")
            val ss = DataHandle.say();
            Timber.e(ss)
        }

        val myplay = MyMediaPlayer(context)

        binding.test2.setOnClickListener {
            myplay.playSound(R.raw.qinshi)
        }
    }
}