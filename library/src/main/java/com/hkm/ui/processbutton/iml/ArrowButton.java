package com.hkm.ui.processbutton.iml;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.hkm.ui.processbutton.ProcessButton;
import com.hkm.ui.processbutton.R;
import com.hkm.ui.processbutton.Util.ProgressBar;

/**
 * Created by hesk on 15/10/15.
 */
public class ArrowButton extends ProcessButton {


    private ProgressBar mProgressBar;

    private Mode mMode;

    private int mColor1;
    private int mColor2;
    private int mColor3;
    private int mColor4;
    private int strokeWidth, colorStroke, colorArrow;

    public enum Mode {
        PROGRESS, ENDLESS;
    }

    public ArrowButton(Context context) {
        super(context);
        init(context);
    }

    public ArrowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArrowButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * only for extended program for specific modifications. exposing the parameter for the button configuration
     *
     * @param button_pres the constant for the button presentation
     * @return number
     */
    @Override
    protected int extension_button_presentation(int button_pres) {
        return BUTTON_EXTENSION;
    }

    @Override
    protected Drawable createPressedDrawable() {
        int colorPressed = mAttr.getColor(R.styleable.FlatButton_pb_colorPressed, defColor_blue_dark);
        return basic_drawble_render(colorPressed, R.drawable.rect_list_item_pressed);
    }

    @Override
    protected LayerDrawable createDisabledDrawable() {
        int colorDisable = mAttr.getColor(R.styleable.FlatButton_pb_colorDisabled, defColor_white);
        return basic_drawble_render(colorDisable, R.drawable.rect_disable);
    }

    /**
     * maded for extension: please write your own extension in here
     *
     * @return the layer drawable in here
     */
    @Override
    protected LayerDrawable drawable_button_extended() {
        int colorNormal = mAttr.getColor(R.styleable.FlatButton_pb_colorNormal, defColor_blue);
        return basic_drawble_render(colorNormal, R.drawable.rect_list_item);
    }

    private LayerDrawable basic_drawble_render(@ColorInt final int faceColor, @DrawableRes final int asset_drawable) {

        final LayerDrawable drawableNormal = getLayersById(asset_drawable);

        GradientDrawable drawableTop = getFromLayer(drawableNormal, 0);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(colorStroke);

        GradientDrawable drawableBottom = getFromLayer(drawableNormal, 1);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(faceColor);
        drawableNormal.setLayerInset(1, 0, 0, 0, strokeWidth);

        if (drawableNormal.getNumberOfLayers() > 2) {
            BitmapDrawable drawableArrow = (BitmapDrawable) drawableNormal.getDrawable(2);
            drawableArrow.setFilterBitmap(true);

            drawableArrow.setColorFilter(colorArrow, PorterDuff.Mode.SCREEN);
        }
        return drawableNormal;
    }

    private void init(Context context) {
        Resources res = context.getResources();
        mMode = Mode.ENDLESS;
        mColor1 = res.getColor(R.color.holo_blue_bright);
        mColor2 = res.getColor(R.color.holo_green_light);
        mColor3 = res.getColor(R.color.holo_orange_light);
        mColor4 = res.getColor(R.color.holo_red_light);

    }

    @Override
    protected void initAttributesExtension(Context c) {
        strokeWidth = mAttr.getDimensionPixelOffset(R.styleable.FlatButton_pb_borderWidth, defBorderWidth);
        colorStroke = mAttr.getColor(R.styleable.FlatButton_pb_colorBorder, defColor_blue);
        colorArrow = mAttr.getColor(R.styleable.ArrowButton_pb_colorArrow, defColor_blue_dark);
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    @Override
    public float getCornerRadius() {
        return 0f;
    }

    @Override
    public void drawProgress(Canvas canvas) {
        if (getBackground() != getNormalDrawable()) {
            setBackgroundDrawable(getNormalDrawable());
        }

        switch (mMode) {
            case ENDLESS:
                drawEndlessProgress(canvas);
                break;
            case PROGRESS:
                drawLineProgress(canvas);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mProgressBar != null) {
            setupProgressBarBounds();
        }
    }

    private void drawLineProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        double indicatorHeightPercent = 0.05; // 5%
        int bottom = (int) (getMeasuredHeight() - getMeasuredHeight() * indicatorHeightPercent);
        getProgressDrawable().setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }

    private void drawEndlessProgress(Canvas canvas) {
        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(this);
            setupProgressBarBounds();
            mProgressBar.setColorScheme(mColor1, mColor2, mColor3, mColor4);
            mProgressBar.start();
        }

        if (getProgress() > 0) {
            mProgressBar.draw(canvas);
        }
    }

    private void setupProgressBarBounds() {
        double indicatorHeight = getDimension(R.dimen.layer_padding);
        int bottom = (int) (getMeasuredHeight() - indicatorHeight);
        mProgressBar.setBounds(0, bottom, getMeasuredWidth(), getMeasuredHeight());
    }

}
