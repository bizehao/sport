package com.sport.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sport.R

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:52
 * Description:
 */
class DataFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_data,container,false)
        return view
    }
}