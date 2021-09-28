package com.kproduce.roundcorners.test;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.kproduce.roundcorners.RoundTextView;

/**
 * @author kuanggang on 2019/12/01
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RoundTextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setRadius(10, 0, 0, 0);
                tv.setStrokeWidthColor(5, getResources().getColor(android.R.color.holo_green_dark));
            }
        });
    }
}
