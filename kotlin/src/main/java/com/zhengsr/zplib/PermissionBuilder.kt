package com.zhengsr.zplib

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * @author by zhengshaorui 2020/8/4 09:37
 * describe：为permission 提供更多的类
 */
class PermissionBuilder {
    private var activity: FragmentActivity? = null
    private var fragment: Fragment? = null
    private var permissions:List<String>

    constructor(activity: FragmentActivity, permissions: Array<out String>){
        this.activity = activity
        this.permissions = permissions.toList()
    }
    constructor(fragment: Fragment, permissions: Array<out String>){
        this.fragment = fragment
        this.permissions = permissions.toList()
    }


    /**
     * 调用方法
     */
    fun request(callback: RequestCallback) {
        getInvisibleFragment()?.requestNow(callback,*permissions.toTypedArray())
    }


    /**
     * 拿到隐形的 fragment
     */
    private fun getInvisibleFragment(): InvisibleFragment? {

        val fragmentManager: FragmentManager? =
            fragment?.childFragmentManager ?: activity?.supportFragmentManager


        fragmentManager?:return null
        var fragment = fragmentManager.findFragmentByTag(F_TAG)
        fragment = if (fragment != null) {
            fragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment.newInstance()
            fragmentManager.beginTransaction().add(invisibleFragment, F_TAG).commitNow()
            invisibleFragment
        }
        return fragment
    }
}