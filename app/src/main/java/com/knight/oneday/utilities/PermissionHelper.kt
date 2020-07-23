package com.knight.oneday.utilities

import android.content.Context
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils

/**
 * Create by FLJ in 2020/7/23 15:50
 * 权限项
 */
object PermissionHelper {

    fun checkPermission(
        permissionConstants: String,
        onGranted: (() -> Unit)? = null,
        onDenied: (() -> Unit)? = null
    ) {
        PermissionUtils.permission(permissionConstants)
            .rationale { activity, shouldRequest ->
                showRationaleDialog(activity, shouldRequest)
            }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    onGranted?.invoke()
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    onDenied?.invoke()
                }
            })
            .request()
    }

    fun showRationaleDialog(
        context: Context,
        shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest
    ) {

    }

}