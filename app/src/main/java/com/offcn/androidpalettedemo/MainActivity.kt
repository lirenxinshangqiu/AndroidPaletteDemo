package com.offcn.androidpalettedemo

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private val colorAdapter: PaletteAdapter by lazy {
        PaletteAdapter(this, mutableListOf())
    }
    private val IMAGE_PICKER = 2001
    private val REQUEST_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).init()
        initImagePicker()
        colorRv?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = colorAdapter
        }
        val bitmap = getBitmap(R.mipmap.ic_launcher)

        bitmap?.apply {
            getPaletteColor(this)
        }
        pickerBtn?.setOnClickListener {
            checkPermission()
        }


    }

    private fun pickerImage(){
        val intent = Intent(this, ImageGridActivity::class.java)
        startActivityForResult(intent, IMAGE_PICKER)
    }

    private fun checkPermission(){
        val checkSelfPermission = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (checkSelfPermission == PERMISSION_GRANTED){
            pickerImage()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
        }
    }

    private fun initImagePicker() {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = PicassoImageLoader() //设置图片加载器

        imagePicker.isShowCamera = true //显示拍照按钮

        imagePicker.isCrop = false //允许裁剪（单选才有效）

        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.isMultiMode = false

        imagePicker.selectLimit = 9 //选中数量限制

        imagePicker.style = CropImageView.Style.RECTANGLE //裁剪框的形状

        imagePicker.focusWidth = 800 //裁剪框的宽度。单位像素（圆形自动取宽高最小值）

        imagePicker.focusHeight = 800 //裁剪框的高度。单位像素（圆形自动取宽高最小值）

        imagePicker.outPutX = 1000 //保存文件的宽度。单位像素

        imagePicker.outPutY = 1000 //保存文件的高度。单位像素

    }

    fun getPaletteColor(bitmap: Bitmap) {
        val fastBlurBitmap = PaletteUtils.fastBlur(bitmap, 0.4f, 30)
        bgIv?.setImageBitmap(fastBlurBitmap)
        fastBlurBitmap?.let {
            val bigColor = PaletteUtils.getBigColor(it)
            val alpha = Color.alpha(bigColor)
            val red = Color.red(bigColor)
            val green = Color.green(bigColor)
            val blue = Color.blue(bigColor)
            Log.d("获取图片颜色","bigColor:$bigColor\nalpha:$alpha\nred:$red\ngreen:$green\nblue:$blue")
            pickerBtn?.setBackgroundColor(bigColor)
        }

        fastBlurBitmap?:return
        Palette.from(fastBlurBitmap).maximumColorCount(24).generate {
            val dominantSwatch = it?.dominantSwatch//主色调
            val mutedSwatch = it?.mutedSwatch//柔和的
            val darkMutedSwatch = it?.darkMutedSwatch//柔和的黑
            val lightMutedSwatch = it?.lightMutedSwatch//柔和的亮
            val vibrantSwatch = it?.vibrantSwatch//充满活力的
            val darkVibrantSwatch = it?.darkVibrantSwatch//充满活力的黑
            val lightVibrantSwatch = it?.lightVibrantSwatch//充满活力的亮
            val list = mutableListOf<SwatchBean>()
            dominantSwatch?.let { platte ->
                ImmersionBar.with(this).statusBarColorInt(platte.rgb).fullScreen(false).titleBarMarginTop(supportActionBar?.customView).init()
                supportActionBar?.setBackgroundDrawable(ColorDrawable(platte.rgb))
                val bigColor = platte.rgb
                val alpha = Color.alpha(bigColor)
                val red = Color.red(bigColor)
                val green = Color.green(bigColor)
                val blue = Color.blue(bigColor)
                Log.d("获取图片颜色","主色调颜色：$bigColor\nalpha:$alpha\nred:$red\ngreen:$green\nblue:$blue")
                list.add(SwatchBean(platte, 1))
            }
            mutedSwatch?.let { platte ->
                list.add(SwatchBean(platte, 2))
            }
            darkMutedSwatch?.let { platte ->
                list.add(SwatchBean(platte, 3))
            }
            lightMutedSwatch?.let { platte ->
                list.add(SwatchBean(platte, 4))
            }

            vibrantSwatch?.let { platte ->
                list.add(SwatchBean(platte, 5))
            }

            darkVibrantSwatch?.let { platte ->
                list.add(SwatchBean(platte, 6))
            }
            lightVibrantSwatch?.let { platte ->
                list.add(SwatchBean(platte, 7))
            }
//            bgIv?.setImageBitmap(bitmap)

            colorAdapter.setData(list)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode === IMAGE_PICKER) {
                val images =
                    data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
                if (images.isNotEmpty()){
                    getPaletteColor(BitmapFactory.decodeFile(images[0].path))
                }else{
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE){
            grantResults.forEach {
                if (it == PERMISSION_GRANTED){
                    pickerImage()
                }
            }
        }
    }
}


