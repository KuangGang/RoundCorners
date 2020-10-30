package com.kproduce.roundcorners.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kproduce.roundcorners.RoundTextView

/**
 * @author kuanggang on 2019/12/01
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<RoundTextView>(R.id.tv)
        tv.setOnClickListener {
            tv.setRadius(10f, 0f, 0f, 0f)
            tv.setStrokeWidthColor(5f, resources.getColor(android.R.color.holo_green_dark))
        }
    }
}