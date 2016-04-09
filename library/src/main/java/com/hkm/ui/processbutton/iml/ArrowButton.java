package com.hkm.ui.processbutton.iml;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;

import com.hkm.ui.processbutton.ProcessButton;
import com.hkm.ui.processbutton.R;
import com.hkm.ui.processbutton.Util.ProgressBar;

/**
 * Created by hesk on 15/10/15.
 */
public class ArrowButton extends ProcessButton {

    public static int NO_ID = 0;
    private ProgressBar mProgressBar;

    private Mode mMode;

    private int
            mColor1,
            mColor2,
            mColor3,
            mColor4,
            icon_size_text_padding,
            defIconSize,
            resIcon,
            resIconSize,
            strokeWidth,
            colorStroke,
            colorArrow,
            colorPressed,
            colorNormal,
            resArrow,
            colorDisable,
            mDrawableSize,
            topBorder, bottomBorder,
            vertical_padding;

    private RectF reface_Rect;

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

    private LayerDrawable getDefault() {
        ShapeDrawable border = new ShapeDrawable();
        border.getPaint().setColor(Color.WHITE);
        ShapeDrawable background = new ShapeDrawable();
        background.getPaint().setColor(Color.BLACK);

        ShapeDrawable clip = new ShapeDrawable();
        clip.getPaint().setColor(Color.YELLOW);

        Drawable[] layers = new Drawable[]{background, border, clip};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        layerDrawable.setLayerInset(1, 1, 0, 1, 1);
        layerDrawable.setLayerInset(2, 0, 0, 0, 10);
        return layerDrawable;
    }


    private LayerDrawable basic_drawble_render(@ColorInt final int faceColor, @DrawableRes final int asset_drawable) {
        final LayerDrawable drawableNormal = getLayersById(asset_drawable);
        GradientDrawable drawableTop = getFromLayer(drawableNormal, 0);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(colorStroke);
        GradientDrawable drawableBottom = getFromLayer(drawableNormal, 1);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(faceColor);
        drawableNormal.setLayerInset(1, 0, topBorder, 0, bottomBorder);
        return drawableNormal;
    }

    /**
     * @return StateListDrawable
     */
    protected StateListDrawable createFillDrawable() {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},
                basic_drawble_render(colorPressed, R.drawable.rect_list_item));
        drawable.addState(new int[]{android.R.attr.state_focused},
                basic_drawble_render(colorPressed, R.drawable.rect_list_item));
        drawable.addState(new int[]{android.R.attr.state_selected},
                basic_drawble_render(colorPressed, R.drawable.rect_list_item));
        drawable.addState(new int[]{}, basic_drawble_render(colorNormal,
                R.drawable.rect_list_item));
        return drawable;
    }


    protected LayerDrawable createCommonFace() {
        if (resIcon != -1) {
            LayerDrawable ly = afterProcessLayerDrawable(new LayerDrawable(
                    new Drawable[]{
                            createFillDrawable(),
                            getArrow(),
                            getIcon()
                    }));
            ly.setLayerInset(2, icon_size_text_padding, 0, 0, 0);
            return ly;
        } else {
            return afterProcessLayerDrawable(new LayerDrawable(
                    new Drawable[]{
                            createFillDrawable(),
                            getArrow()
                    }));
        }
    }

    protected LayerDrawable afterProcessLayerDrawable(LayerDrawable ld) {
        ld.setLayerInset(ld.getNumberOfLayers() - 1, 0, vertical_padding, 0, vertical_padding);
        return ld;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    protected Drawable getIcon() {
        Drawable drawable;
        /*
        try {
            BitmapDrawable draw = (BitmapDrawable) getDrawable(resIcon).mutate();
            Bitmap scaled = Bitmap.createScaledBitmap(draw.getBitmap(), resIconSize, resIconSize, true);
             drawable = new BitmapDrawable(getContext().getResources(), scaled);
            return drawable;
        } catch (ClassCastException e) {
            e.fillInStackTrace();
            Drawable drawable = ContextCompat.getDrawable(getContext(), resIcon);
            ((BitmapDrawable) drawable).setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            return drawable;
        } catch (Resources.NotFoundException e) {
            e.fillInStackTrace();
        }*/

        // ScaleDrawable sized = new ScaleDrawable(getDrawable(resIcon), Gravity.CENTER, resIconSize, resIconSize);
        //  sized.setBounds(0, 0, resIconSize, resIconSize);
        //BitmapDrawable draw = (BitmapDrawable) sized.getDrawable();
        // drawable.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        Drawable draw = ContextCompat.getDrawable(getContext(), resIcon);
        Bitmap scaled = Bitmap.createScaledBitmap(((BitmapDrawable)draw).getBitmap(), resIconSize, resIconSize, true);
        drawable = new BitmapDrawable(getContext().getResources(), scaled);
        setPadding(resIconSize + (int) (icon_size_text_padding * 2), 0, 0, 0);
        return drawable;
    }


    protected Drawable getArrow() {
        BitmapDrawable draw = (BitmapDrawable) getDrawable(resArrow).mutate();
        if (colorArrow != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            draw.setTint(colorArrow);
        }
        draw.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        return draw;
    }


    private void init(Context context) {
        Resources res = context.getResources();
        mMode = Mode.ENDLESS;
        mColor1 = res.getColor(R.color.holo_blue_bright);
        mColor2 = res.getColor(R.color.holo_green_light);
        mColor3 = res.getColor(R.color.holo_orange_light);
        mColor4 = res.getColor(R.color.holo_red_light);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     *
     * @param draw the drawable
     */
    @Override
    public void setBackgroundCompat(Drawable draw) {
        super.setBackgroundCompat(createCommonFace());
    }

    @Override
    protected void initAttributesExtension(Context c, AttributeSet t) {
        defIconSize = c.getResources().getDimensionPixelOffset(R.dimen.icon_size_def);
        icon_size_text_padding = c.getResources().getDimensionPixelOffset(R.dimen.icon_size_text_padding_xl);

        icon_size_text_padding = mAttr.getDimensionPixelOffset(R.styleable.ArrowButton_pb_IconPadding
                , icon_size_text_padding);


        strokeWidth = mAttr.getDimensionPixelOffset(R.styleable.FlatButton_pb_borderWidth, defBorderWidth);
        colorStroke = mAttr.getColor(R.styleable.FlatButton_pb_colorBorder, defColor_blue);
        colorPressed = mAttr.getColor(R.styleable.FlatButton_pb_colorPressed, defColor_blue_dark);
        colorNormal = mAttr.getColor(R.styleable.FlatButton_pb_colorNormal, defColor_blue);
        colorDisable = mAttr.getColor(R.styleable.FlatButton_pb_colorDisabled, defColor_white);

        TypedArray a = getTypedArray(c, t, R.styleable.ArrowButton);
        colorArrow = a.getColor(R.styleable.ArrowButton_pb_colorArrow, defColor_blue_dark);
        resArrow = a.getResourceId(R.styleable.ArrowButton_pb_arrowRes, R.drawable.ic_arrow_to_right);
        vertical_padding = a.getDimensionPixelOffset(R.styleable.ArrowButton_pb_verticalPadding, defBorderWidth);
        bottomBorder = a.getDimensionPixelOffset(R.styleable.ArrowButton_pb_bottomLineThickness, strokeWidth);
        topBorder = a.getDimensionPixelOffset(R.styleable.ArrowButton_pb_topLineThickness, strokeWidth);

        resIcon = a.getResourceId(R.styleable.ArrowButton_pb_IconRes, -1);
        resIconSize = a.getDimensionPixelOffset(R.styleable.ArrowButton_pb_IconSize, defIconSize);
        mDrawableSize = 90;
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
