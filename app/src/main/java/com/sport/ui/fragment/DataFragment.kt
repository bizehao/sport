package com.sport.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.sport.databinding.FragmentDataBinding
import com.sport.utilities.MyMediaPlayer
import timber.log.Timber
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.natives.NativeApi
import com.sport.ui.activity.ClipImageActivity
import com.sport.utilities.GlobalUtil
import com.sport.utilities.GlobalUtil.REQUEST_CROP_PHOTO
import com.sport.utilities.GlobalUtil.REQUEST_PICK_IMAGE
import com.sport.utilities.GlobalUtil.REQUEST_PICK_VIDEO
import com.sport.utilities.SportExecutors

/**
 * User: bizehao
 * Date: 2019-03-20
 * Time: 上午9:52
 * Description:
 */
class DataFragment : Fragment() {

    lateinit var binding: FragmentDataBinding;

    private val mPermissionList = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDataBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        subscribeUi(binding, context)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentDataBinding, context: Context) {
        binding.test1.setOnClickListener {
            Timber.e("判断权限")
            if (appraisalPermissions(context)) { //有权限 执行代码
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = "image/*"
                startActivityForResult(intent, GlobalUtil.REQUEST_PICK_IMAGE)
            } else { //无权限 申请权限
                requestPermissions(mPermissionList, 100)
            }
        }

        val myplay = MyMediaPlayer(context)

        binding.test2.setOnClickListener {
            Timber.e("判断权限")
            if (appraisalPermissions(context)) { //有权限 执行代码
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = "image/*"
                startActivityForResult(intent, GlobalUtil.REQUEST_PICK_VIDEO)
            } else { //无权限 申请权限
                requestPermissions(mPermissionList, 200)
            }
        }
        binding.test3.setOnClickListener {
            //myplay.playSound(R.raw.dong)

            NativeApi.testLevelDB()
        }
        binding.test4.setOnClickListener {
            binding.btnTest.animate()
                .translationXBy(200f)
                .alpha(1.0f)
                .scaleX(1f).scaleY(1f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(3000)
                .setStartDelay(0)
                .withLayer()
                .start()
        }
        binding.test5.setOnClickListener {
            binding.target.animate().rotationX(360f)
                .translationX(5f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(3000)
                .setStartDelay(0)
                .withLayer()
                .start()
        }

        binding.target.addOnClickListener {
            Timber.e("点击了我 你要负责")
        }

        binding.btnTest.setOnClickListener {
            Timber.e("点击了我 你要负责")
        }

    }

    //判断权限
    private fun appraisalPermissions(context: Context): Boolean {
        for (mPermission in mPermissionList) {
            if (ContextCompat.checkSelfPermission(context, mPermission) == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    //权限处理
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                val writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                val readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.isNotEmpty() && writeExternalStorage && readExternalStorage) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_PICK
                    intent.type = "image/*"
                    startActivityForResult(Intent.createChooser(intent, "请选择图片"), GlobalUtil.REQUEST_PICK_IMAGE)
                } else {
                    Toast.makeText(context, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
            }
            200 -> {
                val writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                val readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.isNotEmpty() && writeExternalStorage && readExternalStorage) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_PICK
                    intent.type = "image/*"
                    startActivityForResult(Intent.createChooser(intent, "请选择视频"), GlobalUtil.REQUEST_PICK_VIDEO)
                } else {
                    Toast.makeText(context, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //结果返回处理
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_PICK_IMAGE -> {
                    if (data != null) {
                        val uri = data.data
                        if (uri != null) {
                            val intent = Intent()
                            intent.setClass(context!!, ClipImageActivity::class.java)
                            intent.putExtra("type", 2)
                            intent.data = uri
                            startActivityForResult(intent, GlobalUtil.REQUEST_CROP_PHOTO)
                        }
                    } else {
                        Toast.makeText(context, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                }
                REQUEST_PICK_VIDEO -> {
                    if (data != null) {
                        val uri = data.data
                        SportExecutors.diskIO.execute {
                            val path = GlobalUtil.getPathOfImgOrVideo(context, uri)
                            Timber.e("路径是 $path")

                            //val videoUri = getPhotoFromPhotoAlbum.getRealPathFromUri(context, uri)

                            //Timber.e("视频路径 $imgUri")
                            /*val ss = NativeApi.formatImg("/storage/self/primary/DCIM/Camera/lige.wmv", 300, 300)
                            val listType = object : TypeToken<List<List<List<Int>>>>() {}.type
                            val videoData = Gson().fromJson<List<List<List<Int>>>>(ss, listType)
                            Timber.e("帧数 ${videoData.size}")
                            Timber.e("长度 ${videoData[0].size}")
                            Timber.e("宽度 ${videoData[0][0].size}")*/
                        }
                    } else {
                        Toast.makeText(context, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                }

                REQUEST_CROP_PHOTO -> {
                    if (data != null) {
                        SportExecutors.diskIO.execute {
                            val uri = data.data
                            val imgUri = GlobalUtil.getPathOfImgOrVideo(context, uri)
                            Timber.e("图片路径 $imgUri")
                            val ss = NativeApi.formatImg(imgUri, 600, 300)
                            val listType = object : TypeToken<List<List<Int>>>() {}.type
                            val imgData = Gson().fromJson<List<List<Int>>>(ss, listType)
                            Timber.e("长度 ${imgData.size}")
                            Timber.e("宽度 ${imgData[0].size}")

                            if (imgData.isNotEmpty()) {
                                var a1 = 0
                                var a2 = 0
                                var a3 = 0
                                var a4 = 0
                                val sb = StringBuilder()
                                val zifu = charArrayOf('.', '~', '+', '@')
                                for (m in imgData) {
                                    for (n in m) {
                                        sb.append(zifu[n])
                                        when (n) {
                                            0 -> a1++
                                            1 -> a2++
                                            2 -> a3++
                                            3 -> a4++
                                        }
                                    }
                                    sb.append('\n')
                                }
                                binding.ziFuHua.post {
                                                                    binding.ziFuHua.text = sb.toString()
                                }
                                Timber.e("a1: $a1")
                                Timber.e("a2: $a2")
                                Timber.e("a3: $a3")
                                Timber.e("a4: $a4")
                            }

                        }
                    }

                }
            }
        }
    }

}