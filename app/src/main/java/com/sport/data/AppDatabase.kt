package com.sport.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sport.data.dao.PersonDao
import com.sport.data.dao.PlantDao
import com.sport.data.dao.SportDataDao
import com.sport.data.dao.SportInfoDao
import com.sport.data.table.Person
import com.sport.data.table.Plant
import com.sport.data.table.SportData
import com.sport.data.table.SportInfo
import com.sport.utilities.DATABASE_NAME
import com.sport.workers.SeedDatabaseWorker
import com.sport.workers.SportDataDatabaseWorker

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午1:53
 * Description: The Room database for this app
 */
@Database(
    entities = [Person::class, SportInfo::class, Plant::class, SportData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun sportInfoDao(): SportInfoDao
    abstract fun plantDao(): PlantDao
    abstract fun sportDataDao(): SportDataDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        //数据库第一次初始化时执行的操作
                        super.onCreate(db)
                        Log.e("==========", "============")
                        //一次性工作
                        val request1 = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request1)

                        val request2 = OneTimeWorkRequestBuilder<SportDataDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request2)
                    }
                })
                .build()
        }
    }

}