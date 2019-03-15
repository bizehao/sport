package com.sport.ui.sport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sport.utilities.InjectorUtils
import com.sport.databinding.FragmentSportBinding
import java.util.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.sport.data.network.SportNetwork
import com.sport.data.table.Person
import com.sport.model.Province
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午1:34
 * Description: 运动界面
 */
class SportFragment : Fragment() {

    private lateinit var viewModel: SportViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSportBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
        val factory = InjectorUtils.provideSportViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(SportViewModel::class.java)
        subscribeUi(binding)
        return binding.root
    }

    fun subscribeUi(binding: FragmentSportBinding) {

        var i = 2;
        binding.myButton1.setOnClickListener {
            val direction = SportFragmentDirections.actionSportFragmentToComparedFragment()
            it.findNavController().navigate(direction)

            //val sportInfo = SportInfo("001", Date(),500,40,1)
            //viewModel.insertSportInfo(sportInfo)

            Timber.e("增添数据")

            /*i += 1
            val person = Person("00$i", "张三", 18, Date(), 176.2, 71.3, "www.baidu.com")
            val list = ArrayList<Person>();
            list.add(person)
            viewModel.insertPerson(list)*/
        }

        binding.myButton2.setOnClickListener {

            /*viewModel.getPerson().observe(viewLifecycleOwner, Observer { persons ->
                Timber.e("状态" + persons.status)
                if (persons.data == null) {
                    Timber.e("正在加载中")
                }else{
                    Timber.e("数据大小" + persons.data.size)
                }

            })*/

            SportNetwork.getInstance().fetchProvinceList(object : Callback<List<Province>> {
                override fun onFailure(call: Call<List<Province>>, t: Throwable) {
                    Timber.e("当前线程: "+Thread.currentThread())
                    Timber.e("失败了")
                    Timber.e(t.message)
                }

                override fun onResponse(call: Call<List<Province>>, response: Response<List<Province>>) {
                    Timber.e("当前线程: "+Thread.currentThread())
                    Timber.e("成功了")
                }

            })
        }
    }
}