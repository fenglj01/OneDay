package com.knight.oneday.utilities

/**
 * 事件类型
 */
enum class EventType(type: Int) {
    GUIDE(0),              // 引导
    IMPORTANT(1),          // 重要
    MEDIUM(2),             // 中等
    SECONDARY(3)           // 次要
}

// TODO 明日 完善数据库的初始化 然后做一些单元测试 看看