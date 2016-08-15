package com.hkm.ui.processbutton.iml;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hkm.ui.processbutton.R;

import static com.hkm.ui.processbutton.FlatButton.BUTTON_BOTTOM_PAD;


/**
 * Created by saramakm on 23/07/16.
 */
public class AreYouSureButton extends RelativeLayout implements View.OnTouchListener {

    public static final int PERCENT_TO_FINISH_MARK_AS_FINISHED = 10;
    public static final int ONE_SEC = 1000; //ms
    private SubmitProcessButton submitProcessButton;
    private CountDownTimer progress;
    private OnClickListener mOnSureClickListener;

    int howLongPressToBeSure;
    int howOftenRefreshStatus;
    private TypedArray mAttr;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AreYouSureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initAttributes(context, attrs);
    }

    public AreYouSureButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        submitProcessButton = new SubmitProcessButton(context, attrs, defStyleAttr);
        init(context);
        initAttributes(context, attrs);
    }

    public AreYouSureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        submitProcessButton = new SubmitProcessButton(context, attrs);
        init(context);
        initAttributes(context, attrs);
    }

    public AreYouSureButton(Context context) {
        super(context);
        submitProcessButton = new SubmitProcessButton(context);
        init(context);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }


    private void initAttributes(Context context, AttributeSet attributeSet) {
        mAttr = getTypedArray(context, attributeSet, R.styleable.AreYouSureButton);
        if (mAttr == null) {
            return;
        }
        try {
            howLongPressToBeSure = mAttr.getInteger(R.styleable.AreYouSureButton_pb_time_in_ms_to_have_sure, ONE_SEC);
            calculateRefreshTime();
        } finally {
            mAttr.recycle();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnSureClickListener = l;
    }

    private void init(final Context context) {
        howLongPressToBeSure = ONE_SEC;
        calculateRefreshTime();
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        submitProcessButton.setClickable(false);
        submitProcessButton.setFocusableInTouchMode(false);
        submitProcessButton.setFocusable(false);
        submitProcessButton.setOnClickCompleteState(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSureClickListener!=null) {
                    mOnSureClickListener.onClick(v);
                }
            }
        });
        addView(submitProcessButton, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOnTouchListener(this);
    }

    private void calculateRefreshTime() {
        howOftenRefreshStatus = (int)((howLongPressToBeSure*1.0d)/10.0d);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            progress = new IncreaseProgressDuringKeepingButton(submitProcessButton, howLongPressToBeSure, howOftenRefreshStatus);
            progress.start();
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            progress.cancel();
            resetProgressState();
        }
        return false;
    }

    private static class IncreaseProgressDuringKeepingButton extends CountDownTimer {

        private final SubmitProcessButton submitProcessButton;
        private final long howLong;
        private final long howOften;

        public IncreaseProgressDuringKeepingButton(SubmitProcessButton button, long howLong, long howOften) {
            super(howLong, howOften);
            this.submitProcessButton = button;
            this.howLong = howLong;
            this.howOften = howOften;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            updateProgress();
            if (isCloseToFinish()){
                submitProcessButton.setProgress(submitProcessButton.getMaxProgress());
                submitProcessButton.callOnClick();
            }
        }

        private void updateProgress() {
            submitProcessButton.setProgress((int) (submitProcessButton.getProgress()+(submitProcessButton.getMaxProgress()/(howLong/howOften))));
        }

        @Override
        public void onFinish() {

        }
        private boolean isCloseToFinish() {
            return submitProcessButton.getProgress() >= (submitProcessButton.getMaxProgress() - PERCENT_TO_FINISH_MARK_AS_FINISHED);
        }
    }

    private void resetProgressState() {
        submitProcessButton.setProgress(0);
        submitProcessButton.setClickable(false);
        submitProcessButton.setFocusableInTouchMode(false);
        submitProcessButton.setFocusable(false);
        submitProcessButton.setEnabled(true);
    }


}
