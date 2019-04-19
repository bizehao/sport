/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sport.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sport.data.table.Plant
import com.sport.databinding.ListItemPlantBinding
import com.sport.databinding.ListSportPlanBinding
import timber.log.Timber


/**
 * Adapter for the [RecyclerView] in [IndexFragment].
 */
class SportPlanAdapter : ListAdapter<String, SportPlanAdapter.ViewHolder>(SportPlanDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListSportPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        holder.itemView.tag = plant

        holder.getBinding().date.text = getItem(position)

//        if(position == 1){
//            Timber.e("ssssssssss")
//            holder.getBinding().nowItem.visibility = View.VISIBLE
//            holder.getBinding().topLayout.visibility = View.VISIBLE
//        }
        Timber.e("展示 $holder")
    }

    class ViewHolder(
        private val binding: ListSportPlanBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding
    }
}

private class SportPlanDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}