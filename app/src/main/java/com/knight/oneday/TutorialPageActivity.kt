package com.knight.oneday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.themeColor
import com.knight.oneday.views.themeColor

class TutorialPageActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isColorTransitionsEnabled = true
        setNextArrowColor(themeColor(R.attr.colorOnSurface))
        setColorSkipButton(themeColor(R.attr.colorOnSurface))
        setColorDoneText(themeColor(R.attr.colorOnSurface))
        setIndicatorColor(themeColor(R.attr.tlExpiredFillColor), themeColor(R.attr.colorOnSurface))
        showStatusBar(false)
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.guide_welcome),
                description = getString(R.string.guide_welcome_content),
                imageDrawable = R.mipmap.ill_welcome,
                titleColor = themeColor(R.attr.colorOnSurface),
                descriptionColor = themeColor(R.attr.colorOnSurface),
                backgroundColor = themeColor(R.attr.colorSurface)
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.guide_finished),
                description = getString(R.string.guide_finished_content),
                imageDrawable = R.mipmap.ill_finished_task,
                titleColor = themeColor(R.attr.colorOnSurface),
                descriptionColor = themeColor(R.attr.colorOnSurface),
                backgroundColor = themeColor(R.attr.colorSecondary)
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.guide_edit),
                description = getString(R.string.guide_edit_content),
                imageDrawable = R.mipmap.ill_edit_task,
                titleColor = themeColor(R.attr.colorOnSurface),
                descriptionColor = themeColor(R.attr.colorOnSurface),
                backgroundColor = themeColor(R.attr.colorSurface)
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.guide_delete),
                description = getString(R.string.guide_delete_content),
                imageDrawable = R.mipmap.ill_delete_task,
                titleColor = themeColor(R.attr.colorOnSurface),
                descriptionColor = themeColor(R.attr.colorOnSurface),
                backgroundColor = themeColor(R.attr.colorSecondary)
            )
        )
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finishedTutorial()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finishedTutorial()
    }

    private fun finishedTutorial() {
        SettingPreferences.isFirstInstall = false
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
