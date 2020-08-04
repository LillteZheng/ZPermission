package com.zhengsr.zplib

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author by zhengshaorui 2020/8/3 15:42
 * describe：权限接口，主要模仿和学习郭老师的PermissionX这个库，自己走一遍
 * 跟着学习设计方法和架构思想，如果看到大部门相同，不要惊讶，我就是抄袭学习的。
 */

const val F_TAG = "InvisibleFragment"

object ZPermission {


    fun with(activity: FragmentActivity) = PermissionCollection(activity)
    fun with(fragment: Fragment) = PermissionCollection(fragment)


    fun isGranted(context: Context, permission: String) = ContextCompat
        .checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED



}



/**
 * 提供一个接口，为更多的入口提供给接口
 */
class PermissionCollection  {

    private lateinit var activity:FragmentActivity
    private lateinit var fragment:Fragment
    constructor(activity: FragmentActivity){
        this.activity = activity
    }
    constructor(fragment: Fragment){
        this.fragment = fragment
    }

    fun permissions(vararg permissions:String) = if (::activity.isInitialized) PermissionBuilder(activity,permissions)
                            else PermissionBuilder(fragment,permissions)
}

