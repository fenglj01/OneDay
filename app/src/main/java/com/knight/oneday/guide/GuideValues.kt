package com.knight.oneday.guide

import com.knight.oneday.R
import com.knight.oneday.utilities.getString

val resourceArray = arrayListOf(
    R.mipmap.ill_welcome,
    R.mipmap.ill_finished_task,
    R.mipmap.ill_delete_task,
    R.mipmap.ill_edit_task
)

val titleArray = arrayListOf(
    "welcome",
    "finished",
    "delete",
    "edit"
)

val contentArray = arrayListOf(
    getString(R.string.guide_step_one),
    getString(R.string.guide_step_two),
    getString(R.string.guide_step_three),
    getString(R.string.guide_step_four)
)