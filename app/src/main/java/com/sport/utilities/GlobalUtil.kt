/*
 * Copyright (C) guolin, Suzhou Quxiang Inc. Open source codes for study only.
 * Do not use for commercial purpose.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sport.utilities

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.TypedValue
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * 应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具类当中，从而给全局代码提供方面的操作。
 *
 * @author guolin
 * @since 17/2/18
 */
object GlobalUtil {

    //请求标志
    val REQUEST_PICK_IMAGE = 1001
    val REQUEST_PICK_VIDEO = 1002
    val REQUEST_CROP_PHOTO = 1003

    private var TAG = "GlobalUtil"

    private var toast: Toast? = null

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun px2dp(context: Context, pxValue: Float): Float {
        return pxValue / context.resources.displayMetrics.density
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dp2px(context: Context, dipValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.resources.displayMetrics)
    }

    /**
     * 将sp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics)
    }

    /**
     * 将px值转换为sp值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Float {
        return pxValue / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获取当前应用程序的包名。
     *
     * @return 当前应用程序的包名。
     */
    fun getAppPackage(context: Context): String {
        return context.packageName
    }

    /**
     * 获取图片或者视频的路径
     */
    fun getPathOfImgOrVideo(context: Context?, uri: Uri?): String? {
        context ?: return null
        uri ?: return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
            val cursor =
                context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * 检查文件是否存在
     */
    fun checkDirPath(dirPath: String): String {
        if (TextUtils.isEmpty(dirPath)) {
            return ""
        }
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dirPath
    }


    /**
     * 获取当前应用程序的名称。
     * @return 当前应用程序的名称。
     */
    /*val appName: String
        get() = GifFun.getContext().resources.getString(GifFun.getContext().applicationInfo.labelRes)*/

    /**
     * 获取当前应用程序的版本名。
     * @return 当前应用程序的版本名。
     */
    /*val appVersionName: String
        get() = GifFun.getContext().packageManager.getPackageInfo(appPackage, 0).versionName*/

    /**
     * 获取当前应用程序的版本号。
     * @return 当前应用程序的版本号。
     */
    /*val appVersionCode: Int
        get() = GifFun.getContext().packageManager.getPackageInfo(appPackage, 0).versionCode*/

    /**
     * 获取当前时间的字符串，格式为yyyyMMddHHmmss。
     * @return 当前时间的字符串。
     */
    val currentDateString: String
        get() {
            val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
            return sdf.format(Date())
        }

    /**
     * 将当前线程睡眠指定毫秒数。
     *
     * @param millis
     * 睡眠的时长，单位毫秒。
     */
    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId
     * 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    /*fun getString(resId: Int): String {
        return GifFun.getContext().resources.getString(resId)
    }*/

    /**
     * 获取指定资源名的资源id。
     *
     * @param name
     * 资源名
     * @param type
     * 资源类型
     * @return 指定资源名的资源id。
     */
    /*fun getResourceId(name: String, type: String): Int {
        return GifFun.getContext().resources.getIdentifier(name, type, appPackage)
    }*/

    /**
     * 获取AndroidManifest.xml文件中，<application>标签下的meta-data值。
     *
     * @param key
     *  <application>标签下的meta-data健
     */
    /*fun getApplicationMetaData(key: String): String? {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = GifFun.getContext().packageManager.getApplicationInfo(appPackage, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            logWarn(TAG, e.message, e)
        }
        if (applicationInfo == null) return ""
        return applicationInfo.metaData.getString(key)
    }*/

    /**
     * 获取请求结果的线索，即将状态码和简单描述组合成一段调试信息。
     *
     * @param status
     * 请求结果的状态码
     * @param msg
     * 请求结果的简单描述
     * @return 请求结果的调试线索。
     */
    fun getResponseClue(status: Int, msg: String): String {
        return "code: $status , msg: $msg"
    }

    /**
     * 获取传入通用资源标识符的后缀名。
     *
     * @param uri
     * 通用资源标识符
     * @return 通用资源标识符的后缀名。
     */
    fun getUriSuffix(uri: String): String {
        if (!TextUtils.isEmpty(uri)) {
            val doubleSlashIndex = uri.indexOf("//")
            val slashIndex = uri.lastIndexOf("/")
            if (doubleSlashIndex != -1 && slashIndex != -1) {
                if (doubleSlashIndex + 1 == slashIndex) {
                    return ""
                }
            }
            val dotIndex = uri.lastIndexOf(".")
            if (dotIndex != -1 && dotIndex > slashIndex) {
                return uri.substring(dotIndex + 1)
            }
        }
        return ""
    }

    /**
     * 生成资源对应的key。
     *
     * @param uri
     * 通用资源标识符
     * @param dir
     * 生成带有指定目录结构的key
     * @return 资源对应的key。
     */
    fun generateKey(uri: String, dir: String): String {
        val suffix = getUriSuffix(uri)
        val uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase()
        return if (TextUtils.isEmpty(dir)) {
            var key = "$currentDateString-$uuid"
            if (!TextUtils.isEmpty(suffix)) {
                key = "$key.$suffix"
            }
            key
        } else {
            var key = "$dir/$currentDateString-$uuid"
            if (!TextUtils.isEmpty(suffix)) {
                key = "$key.$suffix"
            }
            key.replace("//", "/")
        }
    }

    /**
     * 获取转换之后的数字显示，如123456会被转换成12.3万。
     * @param number
     * 原始数字
     * @return 转换之后的数字。
     */
    fun getConvertedNumber(number: Int) = when {
        number < 10000 -> number.toString()
        number < 1000000 -> {
            var converted = String.format(Locale.ENGLISH, "%.1f", number / 10000.0)
            if (converted.endsWith(".0")) {
                converted = converted.replace(".0", "")
            }
            converted + "万"
        }
        number < 100100100 -> {
            val converted = number / 10000
            converted.toString() + "万"
        }
        else -> {
            var converted = String.format(Locale.ENGLISH, "%.1f", number / 100_000_000.0)
            if (converted.endsWith(".00")) {
                converted = converted.replace(".00", "")
            }
            converted + "亿"
        }
    }

    /**
     * 判断某个应用是否安装。
     * @param packageName
     * 要检查是否安装的应用包名
     * @return 安装返回true，否则返回false。
     */
    /*fun isInstalled(packageName: String): Boolean {
        val packageInfo: PackageInfo? = try {
            GifFun.getContext().packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        return packageInfo != null
    }*/

    /**
     * 获取当前应用程序的图标。
     */
    /*fun getAppIcon(): Drawable {
        val packageManager = GifFun.getContext().packageManager
        val applicationInfo = packageManager.getApplicationInfo(appPackage, 0)
        return packageManager.getApplicationIcon(applicationInfo)
    }*/

    /**
     * 判断手机是否安装了QQ。
     */
    //fun isQQInstalled() = isInstalled("com.tencent.mobileqq")

    /**
     * 判断手机是否安装了微信。
     */
    //fun isWechatInstalled() = isInstalled("com.tencent.mm")

    /**
     * 判断手机是否安装了微博。
     * */
    //fun isWeiboInstalled() = isInstalled("com.sina.weibo")

    /**
     * 判断当前版本是否是开源版本。
     */
    //fun isOpenSource() = GifFun.getPackageName() == "com.quxianggif.opensource"


    /** @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    fun getRealPathFromUriAbove(context: Context?, uri: Uri?): String? {
        if (context == null || uri == null) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
            val cursor =
                context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

}