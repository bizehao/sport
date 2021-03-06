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

package com.sport.data

import androidx.room.TypeConverter
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.data.table.SportData
import com.sport.data.table.SportInfo

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (if (date == null) null else date.time)
    }

    @TypeConverter
    fun stringToList1(value: String): List<SportData.SubData> {
        val listType = object : TypeToken<List<SportData.SubData>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString1(list: List<SportData.SubData>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList2(value: String): List<SportInfo.SubInfo> {
        val listType = object : TypeToken<List<SportInfo.SubInfo>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString2(list: List<SportInfo.SubInfo>): String {
        return Gson().toJson(list)
    }
}