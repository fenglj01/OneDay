package com.knight.oneday.app

/**
 * @author knight
 * create at 20-3-7 下午2:41
 * 区分三种模式的实现方案
 */
interface AppStyle {

    fun goHomeFrag()

    /**
     * 不同的风格有不同的自行逻辑 比如自定义模式下 涉及到进行事件类型的定义
     */
    fun styleLogic()

}