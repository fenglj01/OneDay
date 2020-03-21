package com.knight.oneday

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        
    }


}
