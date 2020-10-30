package com.kproduce.roundcorners.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author kuanggang on 2019/12/01
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.setOnClickListener {
            tv.setRadius(10f, 0f, 0f, 0f)
            tv.setStrokeWidthColor(5f, resources.getColor(android.R.color.holo_green_dark))
        }
    }
}