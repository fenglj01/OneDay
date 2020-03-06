package com.knight.oneday.utilities

/**
 * Create by FLJ in 2020/3/6 14:27
 * app 风格 : 极简 标准 自定义
 */
enum class AppStyle {
    MINIMALIST,             // 极简 事件不分等级
    STANDARD,               // 标准 事件按照1-3-5 重要事件 是否需要步骤由用户决定
    CUSTOMIZE               // 自定义 事件的等级分类 由用户自定以 最多7个等级 每个等级最多设置任务5件
}

/**
 * 需要思考一个问题，用户在切换AppStyle时 事件的转换 极简=标准=自定义 之间是否需要事件的转换 如何转换
 */