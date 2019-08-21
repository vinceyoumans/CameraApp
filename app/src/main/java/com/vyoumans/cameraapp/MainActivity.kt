package com.vyoumans.cameraapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {


    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001

    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //btn Click
        capture_btn.setOnClickListener{


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {

                if (
                checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    // Permission not granted
                    val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    // show  popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)

                } else {
                    //Permission already Granted
                    openCamera()
                }

            } else {
                // system os is Marchmellow
                openCamera()

            }




        }



    }

    private fun openCamera() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "NEW PICTURE")
        TODO("Need to change Description to Mission or Patient Viewing ID")
        values.put(MediaStore.Images.Media.DESCRIPTION, "FROM CAMERA")

        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission from popup was granted
                    openCamera()


                } else {
                    // permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            //set image capture
            image_view.setImageURI(image_uri)


        }


    }


}
