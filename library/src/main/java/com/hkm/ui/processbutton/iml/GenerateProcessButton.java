package com.hkm.ui.processbutton.iml;

import com.hkm.ui.processbutton.ProcessButton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class GenerateProcessButton extends ProcessButton {

    public GenerateProcessButton(Context context) {
        super(context);
    }

    public GenerateProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorHeight = (float) getMeasuredHeight() * scale;

        getProgressDrawable().setBounds(0, 0, getMeasuredWidth(), (int) indicatorHeight);
        getProgressDrawable().draw(canvas);
    }

}
