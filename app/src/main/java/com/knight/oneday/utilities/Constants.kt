package com.knight.oneday.utilities

import com.haibin.calendarview.Calendar

/**
 * @author knight
 * create at 20-2-29 下午2:48
 * 一些常用常量
 */
const val DATABASE_NAME = "oneDay.db"
const val TABLE_NAME_TASK = "task"
const val TABLE_NAME_STEP = "step"


const val VIEW_MODEL_STATUS_ON_IO = 0
const val VIEW_MODEL_STATUS_ON_SUCCESS = 1
const val VIEW_MODEL_STATUS_ON_FAIL = 2

const val UI_CALENDAR_SCHEME_IS_DONE = 0x11
const val UI_CALENDAR_SCHEME_IS_EXPIRED = 0x12
const val UI_CALENDAR_SCHEME_NOT_EXPIRED = 0x13

const val DIALOG_TAG_DELETE_EVENT = "delete_event"

const val SAVE_STATE_CURRENT_ID = "SAVE_STATE_CURRENT_ID"

typealias UiCalendar = Calendar
typealias UiScheme = Calendar.Scheme

typealias SysCalendar = java.util.Calendar