package com.sport.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sport.R
import com.sport.databinding.FragmentPlanBinding
import com.sport.ui.fragment.GradeFragment

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:52
 * Description:
 */
class PlanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPlanBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        subscribeUi(binding, context)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentPlanBinding, context: Context) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, GradeFragment())
        transaction.commit()
    }
}