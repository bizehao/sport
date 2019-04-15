package com.sport.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.SparseArray
import androidx.databinding.ViewDataBinding


/**
 * User: bizehao
 * Date: 2019-03-22
 * Time: 上午11:29
 * Description:
 */
abstract class CommomAdapter<T, V : ViewDataBinding>: RecyclerView.Adapter<CommomAdapter<T, V>.ViewHolder>() {

    var lise:ArrayList<T> = ArrayList()

    abstract fun onSetBinding(parent: ViewGroup): V

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = onSetBinding(parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val t = lise[position]
        holder.itemView.tag = t
        convert(holder, t, position)
    }

    override fun getItemCount(): Int {
        return lise.size
    }

    abstract fun convert(holder: ViewHolder, t: T, position: Int)

    fun getList():List<T>{
        return this.lise
    }

    inner class ViewHolder(private val binding: V) : RecyclerView.ViewHolder(binding.root) {
        fun getBinding() = binding
    }
}