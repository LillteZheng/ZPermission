package com.zhengsr.zplib

import android.content.pm.PackageManager
import android.util.Log
import androidx.fragment.app.Fragment
import java.util.ArrayList

/**
 * @author by zhengshaorui 2020/8/3 15:17
 * describe： 一个隐形的fragment，知识用来代理权限
 */
//用 typelias 指定一个类型我为别名
typealias RequestCallback = (Boolean,List<String>) ->Unit
//同个模块下才可见,不公布给外面
internal class InvisibleFragment :Fragment(){
    private val TAG = javaClass.simpleName
    private val REQUEST_CODE = 0x01
    private var callback : RequestCallback? = null


    companion object{
        fun newInstance() = InvisibleFragment()
    }

    /**
     * 用 fragment 去调用权限管理
     */
    fun requestNow(cb:RequestCallback?, vararg permissions: String){
        callback = cb

        requestPermissions(permissions,REQUEST_CODE)
    }

    /**
     * 拿到当前的权限属性
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE){
            val deniedList = ArrayList<String>()

            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED){
                    deniedList.add(index,permissions[index])
                }
            }


            //没有拒绝的list，则全部通过了
            val isAllGranted = deniedList.isEmpty()
            callback?.let { it(isAllGranted,deniedList) }

        }
    }
}