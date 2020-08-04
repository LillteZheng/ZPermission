package com.zhengsr.zpermission

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zhengsr.zplib.ZPermission

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ZPermission.with(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .request { allGranted, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "全部授权了", Toast.LENGTH_SHORT).show()
                } else {
                    deniedList.forEach { permission ->
                        Log.d(TAG, "zsr 拒绝的: $permission")
                    }
                }
            }

    }
}

