package com.sport.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.common.adapter.PlantAdapter
import com.sport.data.AppDatabase
import com.sport.databinding.FragmentIndexBinding
import com.sport.model.Sport
import com.sport.utilities.InjectorUtils
import com.sport.utilities.SportExecutors
import com.sport.viewmodels.IndexViewModel
import com.sport.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.list_sport_plan.*
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashMap

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:51
 * Description:
 */
class IndexFragment : Fragment() {

    private lateinit var viewModel: IndexViewModel

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentIndexBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideIndexViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(IndexViewModel::class.java)
        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        subscribeUi(binding, context)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeUi(binding: FragmentIndexBinding, context: Context) {

        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        binding.plantList.layoutManager = LinearLayoutManager(activity)

        viewModel.plants.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.submitList(it)
            }
        })

        mainViewModel.nextPosition.observe(viewLifecycleOwner, Observer {
            viewModel.handleNextInfo(it)
        })

        mainViewModel.currentPosition.observe(viewLifecycleOwner, Observer {
            binding.current.text = (it + 1).toString()
            viewModel.handleCurrentInfo(it)
        })

        viewModel.dateTimeOfLast.observe(viewLifecycleOwner, Observer {
            val dataFormat = SimpleDateFormat("MM-dd")
            val lastDataTime = dataFormat.parse(it)
            val date = Date()
            val currentDataTime = dataFormat.parse(dataFormat.format(date))
            val days = (lastDataTime.time - currentDataTime.time) / (1000 * 3600 * 24)
            binding.targetDays.text = days.toString()
        })

        viewModel.completed.observe(viewLifecycleOwner, Observer {
            binding.completedNum.text = it.toString()
        })

        viewModel.surplus.observe(viewLifecycleOwner, Observer {
            binding.surplusNum.text = it.toString()
        })

        viewModel.getSportDataCount().observe(viewLifecycleOwner, Observer {
            binding.count.text = "/$it"
        })

        viewModel.nextSport.observe(viewLifecycleOwner, Observer {
            Timber.e("时间 ${it.dateTime}")
            binding.nextSportTime.text = it.dateTime
            var total = 0
            val sport = StringBuilder()
            for (l in it.subData) {
                total += l.pushUpNum
                sport.append(l.pushUpNum).append("-")
            }
            binding.nextTotalNum.text = total.toString()
            binding.nextSportData.text = sport.deleteCharAt(sport.length - 1).toString()

        })
    }
}