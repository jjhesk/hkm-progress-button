package com.dd.sample.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dd.sample.R;
import com.dd.sample.utils.ProgressGenerator;
import com.hkm.ui.processbutton.iml.AnimationButton;
import com.hkm.ui.processbutton.iml.AreYouSureButton;

/**
 * This is it now!
 * Created by hesk on 2/11/15.
 */
public class AreYouSureActivity extends Activity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_areyousure);
        AreYouSureButton gateButton = (AreYouSureButton) findViewById(R.id.gateButton1);
        gateButton.setOnClickListener(this);
        AreYouSureButton alarmButton = (AreYouSureButton) findViewById(R.id.alarmButton1);
        alarmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Yes you are really sure", Toast.LENGTH_LONG).show();
    }
}
