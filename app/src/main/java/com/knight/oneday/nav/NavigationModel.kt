package com.knight.oneday.nav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.R
import com.knight.oneday.utilities.getString

object NavigationModel {

    private var navigationMenuItems = mutableListOf(
        NavigationModelItem.NavMenuItem(
            id = 0,
            icon = R.drawable.ic_one_day_home,
            titleRes = R.string.nav_menu_home,
            checked = false
        ),
        NavigationModelItem.NavMenuItem(
            id = 1,
            icon = R.drawable.ic_one_day_about,
            titleRes = R.string.nav_menu_about,
            checked = false
        ),
        NavigationModelItem.NavMenuItem(
            id = 2,
            icon = R.drawable.ic_one_day_chart,
            titleRes = R.string.nav_menu_chart,
            checked = false
        ),
        NavigationModelItem.NavMenuItem(
            id = 3,
            icon = R.drawable.ic_one_day_settings,
            titleRes = R.string.nav_menu_settings,
            checked = false
        )
    )

    private val navigationTags = mutableListOf(
        NavigationModelItem.NavEventTag(
            getString(R.string.tag_no_tag),
            R.drawable.ic_one_day_tag_no_type
        ),
        NavigationModelItem.NavEventTag(
            getString(R.string.tag_life),
            R.drawable.ic_one_day_tag_life
        ),
        NavigationModelItem.NavEventTag(
            getString(R.string.tag_work),
            R.drawable.ic_one_day_tag_work
        ),
        NavigationModelItem.NavEventTag(
            getString(R.string.tag_entertainment),
            R.drawable.ic_one_day_tag_leisure
        ),
        NavigationModelItem.NavEventTag(
            getString(R.string.tag_health),
            R.drawable.ic_one_day_tag_sport
        )
    )

    private val _navigationList: MutableLiveData<List<NavigationModelItem>> = MutableLiveData()


    val navigationList: LiveData<List<NavigationModelItem>>
        get() = _navigationList

    init {
        postListUpdate()
    }

    fun getNavTagString() = navigationTags.map { it.taskTag }

    fun getNavTagItems() = navigationTags

    fun setNavigationMenuItemChecked(id: Int): Boolean {
        var updated = false
        navigationMenuItems.forEachIndexed { index, item ->
            val shouldCheck = item.id == id
            if (item.checked != shouldCheck) {
                navigationMenuItems[index] = item.copy(checked = shouldCheck)
                updated = true
            }
        }
        if (updated) postListUpdate()
        return updated
    }

    private fun postListUpdate() {
        val newList =
            navigationMenuItems + (NavigationModelItem.NavDivider(getString(R.string.divider_tag))) + navigationTags
        _navigationList.value = newList
    }


}