package com.hkm.ui.processbutton.iml;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.hkm.ui.processbutton.FlatButton;
import com.hkm.ui.processbutton.R;

/**
 * Created by hesk on 2/11/15.
 */
public class AnimationButton extends FlatButton {
    private TextView textView;
    private Operation initial_op = Operation.STOP;
    private Operation state_now;

    private void changeAnimation(Operation operation) {
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null && drawable instanceof Animatable) {
                Animatable animatable = ((Animatable) drawable);
                switch (operation) {
                    case START:
                        animatable.start();
                        break;
                    case STOP:
                        animatable.stop();
                        break;
                }
            }
        }
    }

    private enum Operation {
        START, STOP
    }

    public AnimationButton(Context context) {
        super(context);
        init(context, null);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimationButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mAttr = getTypedArray(context, attrs, R.styleable.FlatButton);
        if (mAttr == null) {
            return;
        }
        try {
            int op = mAttr.getInt(R.styleable.AnimationButton_pb_start_op, Operation.STOP.ordinal());
            initial_op = Operation.values()[op];
        } catch (Exception e) {
            Log.d("like", e.getMessage());
        } finally {
            mAttr.recycle();
        }
        changeAnimation(initial_op);
    }

    public void toggleAnim() {
        if (state_now == null) {
            state_now = Operation.STOP;
        }
        if (state_now == Operation.START) {
            state_now = Operation.STOP;
        } else if (state_now == Operation.STOP) {
            state_now = Operation.START;
        }
        changeAnimation(state_now);
    }
}
