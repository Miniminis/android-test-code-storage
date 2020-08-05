package com.example.androidtestproject.imagecompression

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtestproject.R
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.android.synthetic.main.fragment_image_compression.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * A simple [Fragment] subclass.
 */
class ImageCompression : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_compression, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_img_load.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            var photoUri = data?.data
//            var file = File(photoUri?.path)
//            var compressedImageFile: File? = null
            var myBitmap: Bitmap? = null
//            Tiny.getInstance().init(this.application);

            var bitmapPhoto = if (Build.VERSION.SDK_INT >= 29)
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.activity?.contentResolver!!, photoUri!!))
            else {
                MediaStore.Images.Media.getBitmap(this.activity?.contentResolver, photoUri)
            }



//            var filePathFromActivity = extras.get(Intent.EXTRA_STREAM) as Uri
//            var photoUriPath = getRealPathFromUri(this, photoUri)
            //Uri.parse(FileUtil.getRealPathFromUri(this@IntentActivity as Activity?, filePathFromActivity))
//            val imageFile = File(photoUriPath)

//            Log.d("mh", photoUriPath)
//            Log.d("mh", imageFile.toString())


//            myBitmap = Compressor(this).compressToBitmap(imageFile);

//            GlobalScope.launch {
//                compressedImageFile = Compressor.compress(this@MainActivity, file) {
//                    resolution(1280, 720)
//                    quality(80)
//                    format(Bitmap.CompressFormat.WEBP)
////                    size(2_097_152) // 2 MB
//                    size(1_048_576)
//                }
//            }

//            if (compressedImageFile?.exists()!!) {
//                myBitmap = BitmapFactory.decodeFile(compressedImageFile?.absolutePath)
//                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
////                val myImage: ImageView = findViewById<ImageView>(R.id.imageviewTest)
////                myImage.setImageBitmap(myBitmap)
//            }

//            var realPath = getRealPathFromURI(this, photoUri)
//            Log.d("mhh", "realPath1")
//            Log.d("mhh", realPath)
//
//            myBitmap =
//                SiliCompressor.with(this).getCompressBitmap(realPath, true)

//            textResized.text = "${myBitmap?.width} :: ${myBitmap?.height} :: ${myBitmap?.byteCount}"

//            val input = this?.contentResolver.openInputStream(photoUri!!)
//            var bitmapPhoto = BitmapFactory.decodeStream(input)
//            textOriginal.text = "${bitmapPhoto.width} :: ${bitmapPhoto.height} :: ${bitmapPhoto.byteCount}"

            Log.d("mhh", "$bitmapPhoto")

            if (bitmapPhoto != null) {

                var file = createFileFromBitmap(activity!!, bitmapPhoto)
                Log.d("mhh", "$file")
                Log.d("mhh", file.absolutePath)
                Log.d("mhh", file.path)
                Log.d("mhh", "${file.totalSpace}")
                Log.d("mhh", "${bitmapPhoto?.width} :: ${bitmapPhoto?.height} :: ${bitmapPhoto?.byteCount}")
                Log.d("mhh", file.length().toString())
                Log.d("mhh", "${file.length() / (1024*1024) > 1}")
                Log.d("mhh", "${activity?.filesDir}")

//                myBitmap = SiliCompressor.with(this).getCompressBitmap(file, true)
//
//                Log.d("mhh",  "${myBitmap.width} :: ${myBitmap.height} :: ${myBitmap.byteCount}")

                var compressedImageFile: File? = null

                GlobalScope.launch {
                    compressedImageFile = Compressor.compress(activity!!, file) {
                        resolution(1280, 720)
                        quality(80)
                        format(Bitmap.CompressFormat.PNG)
                        size(1_048_576) // 2 MB                             size(1_048_576) // 1 MB
                    }
                }

                Log.d("mhh", "compressedImageFile :: $compressedImageFile :: ${compressedImageFile?.length()}")

            }
        }
    }

    private fun createFileFromBitmap(context: Context, bitmap : Bitmap) : File {
        var newFile = File(context.filesDir, makeImageFileName())
        var fileOutputStream = FileOutputStream(newFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        return newFile
    }

    private fun makeImageFileName(): String {
        var simpleDateFormat = SimpleDateFormat("yyyyMMdd_hhmmss")
        var date = Date()
        var strDate = simpleDateFormat.format(date)
        return "$strDate.png"
    }









    fun getRealPathFromUri(activity: Activity, contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = activity.contentResolver.query(contentUri!!, proj, null, null, null)!!
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            cursor?.getString(column_index!!)
        } finally {
            cursor?.close()
        }
    }


    private fun resizeBitmap03(original: Bitmap, photoUri: Uri): Bitmap {
        val maxBytes: Long = 1024 * 1024
        val maxPixel: Long = maxBytes / 4
        val currPixel = original.height * original.width

        if (currPixel <= maxPixel) {
            return original
        }

        val scaleFactor: Double = sqrt(maxPixel.toDouble() / currPixel.toDouble())
        var resizeWidth: Int = floor(original.width * scaleFactor).toInt()
        var resizeHeight: Int = floor(original.height * scaleFactor).toInt()

        var result = Bitmap.createScaledBitmap(original, resizeWidth, resizeHeight, false)
        val baos: ByteArrayOutputStream = ByteArrayOutputStream()
        result!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val realResult = BitmapFactory.decodeByteArray(b, 0, b.size)

//        if (result != original)
//            original.recycle()

        return realResult
    }

    private fun resizeBitmap02(bitmap: Bitmap, photoUri: Uri): Bitmap {
        val input = this.activity?.contentResolver?.openInputStream(photoUri)
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        var newBitmapPhoto = BitmapFactory.decodeStream(input, null, options)

        val output = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
        newBitmapPhoto!!.compress(Bitmap.CompressFormat.JPEG, 100, output)
        output.flush()
        output.close()
        return bitmap
    }


    private fun resizeBitmap(bitmap: Bitmap, photoUri: Uri): Bitmap {
        val file = File(URI(photoUri.toString()))
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        return bitmap
    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri!!, projection, null, null, null) ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }

}
