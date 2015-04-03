package com.hkm.ui.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;


public abstract class ProcessButton extends FlatButton {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private GradientDrawable mProgressDrawable;
    private GradientDrawable mCompleteDrawable;
    private GradientDrawable mErrorDrawable;

    private CharSequence mLoadingText;
    private CharSequence mCompleteText;
    private CharSequence mErrorText;
    public static final int
            NORMAL = 0,
            FULL = 100,
            FULLBUTTON = -8,
            ERROR = -1;

    public ProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProcessButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mMinProgress = 0;
        mMaxProgress = 100;

        mProgressDrawable = (GradientDrawable) getDrawable(R.drawable.rect_progress).mutate();
        mProgressDrawable.setCornerRadius(getCornerRadius());

        mCompleteDrawable = (GradientDrawable) getDrawable(R.drawable.rect_complete).mutate();
        mCompleteDrawable.setCornerRadius(getCornerRadius());

        mErrorDrawable = (GradientDrawable) getDrawable(R.drawable.rect_error).mutate();
        mErrorDrawable.setCornerRadius(getCornerRadius());


        if (attrs != null) {
            initAttributes(context, attrs);
        }
    }

    private int getColorXML(int colorResId, int styleIdColor) {
        int default_color = getColor(colorResId);
        return xmlData.getColor(styleIdColor, default_color);
    }

    private TypedArray xmlData;

    private void initAttributes(Context context, AttributeSet attributeSet) {
        xmlData = getTypedArray(context, attributeSet, R.styleable.ProcessButton);
        if (xmlData == null) {
            return;
        }
        try {
            mLoadingText = xmlData.getString(R.styleable.ProcessButton_pb_textProgress);
            mCompleteText = xmlData.getString(R.styleable.ProcessButton_pb_textComplete);
            mErrorText = xmlData.getString(R.styleable.ProcessButton_pb_textError);
            mProgressDrawable.setColor(getColorXML(R.color.purple_progress, R.styleable.ProcessButton_pb_colorProgress));
            mCompleteDrawable.setColor(getColorXML(R.color.green_complete, R.styleable.ProcessButton_pb_colorComplete));
            mErrorDrawable.setColor(getColorXML(R.color.red_error, R.styleable.ProcessButton_pb_colorError));
        } finally {
            xmlData.recycle();
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;

        if (mProgress == mMinProgress) {
            onNormalState();
        } else if (mProgress == mMaxProgress) {
            onCompleteState();
        } else if (mProgress == FULLBUTTON) {
            onCompleteStateButton();
        } else if (mProgress < mMinProgress) {
            onErrorState();
        } else {
            onProgress();
        }

        invalidate();
    }

    /*  protected void onDisable() {
          setBackgroundCompat(getDisableDrawable());
      }

      protected void onEnable() {
          setProgress(mProgress);
      }

      @Override
      public void setEnabled(boolean enabled) {

          if (enabled) {
              onEnable();
          } else {
              onDisable();
          }
          super.setEnabled(enabled);
      }

      @Override
      public boolean isEnabled() {
          return super.isEnabled();
      }
      */
    protected void onErrorState() {
        if (getErrorText() != null) {
            setText(getErrorText());
        }
        setBackgroundCompat(getErrorDrawable());
    }

    protected void onProgress() {
        if (getLoadingText() != null) {
            setText(getLoadingText());
        }
        setBackgroundCompat(getNormalDrawable());
    }

    private OnClickListener list;
    private LayerDrawable complete_layerdrawable;

    public ProcessButton setOnClickCompleteState(OnClickListener listener) {
        list = listener;
        return this;
    }

    public ProcessButton setOnCompleteColorButton(int top, int bottom) {
        complete_layerdrawable = creatNormalDrawable(top, bottom);
        return this;
    }

    protected void onCompleteStateButton() {
        if (list != null) {
            setOnClickListener(list);
            setEnabled(true);
        }
        if (complete_layerdrawable != null) {
            setBackgroundCompat(complete_layerdrawable);
        }
        setText(getCompleteText());
    }

    protected void onCompleteState() {
        if (getCompleteText() != null) {
            setText(getCompleteText());
        }
        setBackgroundCompat(getCompleteDrawable());
    }

    protected void onNormalState() {
        if (getNormalText() != null) {
            setText(getNormalText());
        }
        setBackgroundCompat(getNormalDrawable());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // progress
        if (mProgress > mMinProgress && mProgress < mMaxProgress) {
            drawProgress(canvas);
        }
        super.onDraw(canvas);
    }

    public abstract void drawProgress(Canvas canvas);

    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public GradientDrawable getProgressDrawable() {
        return mProgressDrawable;
    }

    public GradientDrawable getCompleteDrawable() {
        return mCompleteDrawable;
    }

    public CharSequence getLoadingText() {
        return mLoadingText;
    }

    public CharSequence getCompleteText() {
        return mCompleteText;
    }


    public void setProgressDrawable(GradientDrawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    public void setCompleteDrawable(GradientDrawable completeDrawable) {
        mCompleteDrawable = completeDrawable;
    }

    /* public void setCompleteDrawable(Drawable completeDrawable) {
         mCompleteDrawabled = completeDrawable;
     }*/
    public void setLoadingText(CharSequence loadingText) {
        mLoadingText = loadingText;
    }

    public void setCompleteText(CharSequence completeText) {
        mCompleteText = completeText;
    }

    public GradientDrawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(GradientDrawable errorDrawable) {
        mErrorDrawable = errorDrawable;
    }

    public CharSequence getErrorText() {
        return mErrorText;
    }

    public void setErrorText(CharSequence errorText) {
        mErrorText = errorText;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mProgress = mProgress;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mProgress = savedState.mProgress;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(mProgress);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    /**
     * A {@link android.os.Parcelable} representing the {@link ProcessButton}'s
     * state.
     */
    public static class SavedState extends BaseSavedState {

        private int mProgress;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            super(in);
            mProgress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mProgress);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
