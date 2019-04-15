package com.sport.ui.fragment

import android.content.Context
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
import timber.log.Timber
import kotlin.collections.HashMap

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:51
 * Description:
 */
class IndexFragment : Fragment() {

    private lateinit var viewModel: IndexViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentIndexBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideIndexViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(IndexViewModel::class.java)
        subscribeUi(binding, context)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentIndexBinding, context: Context) {
        binding.beginSport.setOnClickListener {
            val direction = SportFragmentDirections.actionSportFragmentToRunningFragment()
            it.findNavController().navigate(direction)
        }
        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        binding.plantList.layoutManager = LinearLayoutManager(activity)

        viewModel.plants.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.submitList(it)
            }
        })

        binding.testBtn.setOnClickListener {
            SportExecutors.diskIO.execute {
                val database = AppDatabase.getInstance(context)
                val list = database.sportDataDao().getSportData()
                for (a in list) {
                    Timber.e("数据展示 $a")
                }
            }
        }

        binding.chooseGrade.setOnClickListener {
            val direction = SportFragmentDirections.actionSportFragmentToGradeFragment()
            it.findNavController().navigate(direction)
        }

        binding.btnTest1.setOnClickListener {
            /*val ll = HashMap<Int,Sport>()
            val kk = Sport(1,true,5,56,true)
            ll[1] = kk
            ll[2] = kk
            ll[3] = kk
            val mm = Gson().toJson(ll)*/
            val ss =
                "{\"1\":{\"current\":true,\"dateTime\":\"\",\"grade\":5,\"id\":1,\"intervalTime\":56,\"isStartOfGroup\":true},\"2\":{\"current\":true,\"dateTime\":\"\",\"grade\":5,\"id\":1,\"intervalTime\":56,\"isStartOfGroup\":true},\"3\":{\"current\":true,\"dateTime\":\"\",\"grade\":5,\"id\":1,\"intervalTime\":56,\"isStartOfGroup\":true}}\n"
            val dataType = object : TypeToken<HashMap<Int, Sport>>() {}.type
            val tt = Gson().fromJson<HashMap<Int, Sport>>(ss, dataType)
            for (l in tt){
                Timber.e(l.key.toString())
                Timber.e(l.value.toString())
                Timber.e("\n")
            }
        }
    }
}