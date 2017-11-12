package com.example.wonsi.petdictionary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by wonsi on 2017-11-10.
 */

public class Zxing extends CaptureActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView title = new TextView(this);
        title.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        title.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        title.setPadding(150,100,100,100);
        title.setTextColor(Color.parseColor("#FF7200"));
        title.setTextSize(30);
        title.setText("바코드를 입력해주세요.");

        this.addContentView(title, layoutParams);
    }


}
