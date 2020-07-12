package com.knight.oneday.guide

import com.knight.oneday.R
import com.knight.oneday.utilities.getString

val resourceArray = arrayListOf(
    R.mipmap.ill_welcome,
    R.mipmap.ill_finished_task,
    R.mipmap.ill_edit_task,
    R.mipmap.ill_delete_task
)

val titleArray = arrayListOf(
    getString(R.string.guide_welcome),
    getString(R.string.guide_finished),
    getString(R.string.guide_edit),
    getString(R.string.guide_delete)
)

val contentArray = arrayListOf(
    getString(R.string.guide_welcome_content),
    getString(R.string.guide_finished_content),
    getString(R.string.guide_edit_content),
    getString(R.string.guide_delete_content)
)