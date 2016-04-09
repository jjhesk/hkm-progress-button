package com.dd.sample.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.sample.R;
import com.dd.sample.utils.ProgressGenerator;
import com.hkm.ui.processbutton.iml.AnimationButton;

/**
 * This is it now!
 * Created by hesk on 2/11/15.
 */
public class AnimationActivity extends Activity implements ProgressGenerator.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_animation);
        final AnimationButton btnSend = (AnimationButton) findViewById(R.id.animationButton);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.toggleAnim();
            }
        });
        final AnimationButton btnSendf = (AnimationButton) findViewById(R.id.animationButton2);
        btnSendf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSendf.toggleAnim();
            }
        });

    }

    @Override
    public void onComplete() {
        Toast.makeText(this, R.string.Loading_Complete, Toast.LENGTH_LONG).show();
    }
}
