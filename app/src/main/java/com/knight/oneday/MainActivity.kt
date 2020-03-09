package com.knight.oneday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.idescout.sql.SqlScoutServer
import com.knight.oneday.utilities.InjectorUtils
import com.knight.oneday.viewmodels.DayEventAndStepViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: DayEventAndStepViewModel by lazy {
        InjectorUtils.dayEventAndStepViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SqlScoutServer.create(this, packageName)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*viewModel.events.observe(this, Observer {
            tvText.text = it.toString()
        })
        viewModel.steps.observe(this, Observer {
            Log.d("step", "$it")
        })*/
        /*viewModel.eventsWithSteps.observe(this, Observer {
            tvText.text = it.toString()
        })*/
        /*viewModel.addEvent()
        viewModel.steps.observe(this, Observer {
            Log.d("step", "$it")
        })*/
    }

}
