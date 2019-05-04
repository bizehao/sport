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
import com.sport.data.table.SportData
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
 * Description: 主页界面
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

        //日志展示
        viewModel.plants.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.submitList(it)
            }
        })

        //界面数据
        mainViewModel.currentPosition.observe(viewLifecycleOwner, Observer {
            binding.current.text = (it + 1).toString()
            viewModel.handleCurrentInfo(it).observe(viewLifecycleOwner, Observer { map ->
                if (map != null) {
                    binding.completedNum.text = map["totalCompletedPushUpNum"].toString() //已完成
                    binding.surplusNum.text = map["totalSurplusPushUpNum"].toString() //剩余
                    val nextData = map["nextData"] as SportData

                    val dateFormat = SimpleDateFormat("MM-dd")
                    val dataTime = dateFormat.parse(nextData.dateTime)
                    val todayDate = Date()
                    val today = dateFormat.parse(dateFormat.format(todayDate))
                    val date = when ((dataTime.time - today.time) / (1000 * 3600 * 24)) {
                        0L -> "今天"
                        1L -> "明天"
                        2L -> "后天"
                        else -> nextData.dateTime
                    }
                    binding.nextSportTime.text = date //下次训练
                    var total = 0
                    val sport = StringBuilder()
                    for (l in nextData.subData) {
                        total += l.pushUpNum
                        sport.append(l.pushUpNum).append("-")
                    }
                    binding.nextTotalNum.text = total.toString() //总共
                    binding.nextSportData.text = sport.deleteCharAt(sport.length - 1).toString() //组
                }
            })
        })

        //距目标时间
        viewModel.dateTimeOfLast.observe(viewLifecycleOwner, Observer {
            val days: String
            days = if (it != "") {
                val dateFormat = SimpleDateFormat("MM-dd")
                val lastDataTime = dateFormat.parse(it)
                val date = Date()
                val currentDataTime = dateFormat.parse(dateFormat.format(date))
                ((lastDataTime.time - currentDataTime.time) / (1000 * 3600 * 24)).toString()
            } else {
                ""
            }
            binding.targetDays.text = days //距目标时间
        })

        //总等级数量
        viewModel.getSportDataCount().observe(viewLifecycleOwner, Observer {
            binding.count.text = "/$it"
        })
    }
}