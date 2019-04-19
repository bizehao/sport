package com.sport.ui.fragment

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.R
import com.sport.common.adapter.CommomAdapter
import com.sport.databinding.FragmentRunningBinding
import com.sport.databinding.ListHeaderBinding
import com.sport.model.GroupSport
import com.sport.model.IteratorSport
import com.sport.ui.activity.MainActivity
import com.sport.viewmodels.RunningViewModel
import timber.log.Timber
import java.lang.StringBuilder
import androidx.lifecycle.Observer
import com.sport.utilities.InjectorUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * User: bizehao
 * Date: 2019-03-21
 * Time: 上午11:43
 * Description: 正在运动的界面
 */
class RunningFragment : Fragment() {

    private lateinit var mSenserManager: SensorManager

    private lateinit var binding: FragmentRunningBinding

    private lateinit var viewModel: RunningViewModel
    private var initReadyCount = 80
    private var isReception: Boolean = false //是否在前台
    private var isMonitor: Boolean = false //是否开始传感器
    private var currentIndex = 0 //当前的位置
    private var currentIndexCount = 0 //当前位置的数量
    private var timer: Timer? = null //计时器
    private lateinit var adapter: Adapter


    //传感器监听事件
    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) { //当传感器监测到的数值发生变化时
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                if (isReception && isMonitor && event.values[0] > 0) {
                    viewModel.sportNum.value = --currentIndexCount
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { //当感应器精度发生变化

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideRunningViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(RunningViewModel::class.java)
        val activity = activity as MainActivity
        activity.setMainActivityActionBar(binding.toolbar)
        subscribeUi(binding, context)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isReception = true
    }

    override fun onPause() {
        super.onPause()
        isReception = false
    }

    private fun subscribeUi(binding: FragmentRunningBinding, context: Context) {
        //注册传感器监听
        mSenserManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager//获取感应器服务
        //TYPE_ACCELEROMETER:加速度传感器;TYPE_LIGHT:获取光线传感器;TYPE_GYROSCOPE:陀螺仪传感器;TYPE_AMBIENT_TEMPERATURE:温度传感器
        //TYPE_PROXIMITY:距离传感器;TYPE_STEP_COUNTER:计步统计;TYPE_STEP_DETECTOR:单次计步;
        val mSensor = mSenserManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)//获取距离传感器
        mSenserManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL) //注册传感器事件

        adapter = Adapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)

        viewModel.getCurrentSport().observe(viewLifecycleOwner, Observer {
            if (it.status == 0 && it.data != null) {
                val list = ArrayList<IteratorSport>()
                var totalNum = 0
                for (obj in it.data.subData) {
                    list.add(IteratorSport(obj.id, obj.pushUpNum, false))
                    totalNum += obj.pushUpNum
                }
                viewModel.totalSportNum.value = totalNum
                adapter.lise = list
                adapter.getList()[currentIndex].current = true
                adapter.notifyDataSetChanged()

                currentIndexCount = adapter.getList()[currentIndex].num
                binding.doCount.text = currentIndexCount.toString()
                binding.duration = CalculationTime(0)

                viewModel.intervalTime.value = 5
            } else {
                Timber.e("正在加载")
            }
        })

        viewModel.totalSportNum.observe(viewLifecycleOwner, Observer {
            binding.needNum = it
        })

        binding.finish.setOnClickListener {
            it.findNavController().popBackStack()
        }

        //执行倒计时
        viewModel.intervalTime.observe(viewLifecycleOwner, Observer {
            Timber.e("开始倒计时")
            var readyNum = it
            binding.readyCount = it
            binding.countDown = true
            binding.beginSport = false
            isMonitor = false
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    binding.readyCount = readyNum--
                    if (readyNum == -1) {
                        Thread.sleep(500)
                        timer.cancel()
                        binding.countDown = false
                        binding.beginSport = true
                        isMonitor = true
                        viewModel.sportTime.postValue(true)
                        viewModel.sportNum.postValue(adapter.getList()[currentIndex].num)
                    }
                }
            }, 0, 1000)
        })

        //俯卧撑数量
        viewModel.sportNum.observe(viewLifecycleOwner, Observer {
            Timber.e("开始俯卧撑")
            if (it == 0 && currentIndex == adapter.getList().size - 1) {
                adapter.updateStatus(currentIndex, false)
                viewModel.sportTime.postValue(false)
                binding.ready.visibility = View.GONE
                binding.success.visibility = View.VISIBLE
                binding.countDown = true
                binding.beginSport = false
                mSenserManager.unregisterListener(listener)
                return@Observer
            }
            if (it == 0) {
                Timber.e("下一组")
                adapter.updateStatus(currentIndex, false)
                adapter.updateStatus(++currentIndex, true)
                currentIndexCount = adapter.getList()[currentIndex].num
                viewModel.sportTime.postValue(false)
                viewModel.intervalTime.value = initReadyCount
            } else {
                binding.doCount.text = it.toString()
            }
        })

        //俯卧撑计时
        viewModel.sportTime.observe(viewLifecycleOwner, Observer {
            if (it) {
                Timber.e("开始俯卧撑计时")
                timer = Timer()
                var initTime = 0
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        binding.duration = CalculationTime(initTime++)
                    }
                }, 0, 1000)
            } else {
                timer!!.cancel()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        //注销监听器
        mSenserManager.unregisterListener(listener)
        if (timer != null) {
            timer!!.purge()
        }

        Timber.e("销毁")
    }

    /**
     * 计算时间
     *
     * @param ssNum 秒
     */
    private fun CalculationTime(ssNum: Int): String {
        val ss = ssNum % 60 //秒
        val mm = ssNum / 60 % 60 //分
        val hh = ssNum / 60 / 60 % 60 //时
        val builder = StringBuilder()
        if (hh != 0) {
            return builder.append(hh).append("h").append(mm).append("m").append(ss).append("s").toString()
        } else if (mm != 0) {
            return builder.append(mm).append("m").append(ss).append("s").toString()
        } else {
            return builder.append(ss).append("s").toString()
        }
    }

    inner class Adapter :
        CommomAdapter<IteratorSport, ListHeaderBinding>() {

        override fun onSetBinding(parent: ViewGroup): ListHeaderBinding {
            return ListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        override fun convert(holder: ViewHolder, t: IteratorSport, position: Int) {
            val binding = holder.getBinding()
            binding.sportNum.text = t.num.toString()
            if (position == getList().lastIndex) {
                binding.jiantou.visibility = View.GONE
            }
            if (t.current) {
                binding.sportNum.setBackgroundColor(ContextCompat.getColor(context!!, R.color.cover))
            } else {
                binding.sportNum.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        fun updateStatus(index: Int, status: Boolean) {
            getList()[index].current = status
            notifyItemChanged(index)
        }
    }
}