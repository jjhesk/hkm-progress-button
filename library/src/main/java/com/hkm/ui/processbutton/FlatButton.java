package com.hkm.ui.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.widget.Button;


public class FlatButton extends Button {
    public static final int BUTTON_BOTTOM_PAD = 1, BUTTON_FLAT = 2;
    private StateListDrawable mNormalDrawable;
    private GradientDrawable mDisabledDrawable;
    protected CharSequence mNormalText;
    private float cornerRadius;
    private TypedArray xml;
    private int borderWidthDefault;
    private int button_presentation;
    private LayerDrawable drawableNormal;

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public FlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlatButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mNormalDrawable = new StateListDrawable();
        if (attrs != null) {
            initAttributes(context, attrs);
        }
        mNormalText = getText().toString();
        setBackgroundCompat(mNormalDrawable);
    }

    /**
     * only for extended program for specific modifications. exposing the parameter for the button configuration
     *
     * @param button_pres the constant for the button presentation
     * @return number
     */
    protected int extension_button_presentation(int button_pres) {
        return button_pres;
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        xml = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (xml == null) {
            return;
        }

        try {
            button_presentation = extension_button_presentation(xml.getInt(R.styleable.FlatButton_pb_presentation, BUTTON_BOTTOM_PAD));
            borderWidthDefault = context.getResources().getDimensionPixelOffset(R.dimen.border_width);

            float defValue = getDimension(R.dimen.corner_radius);
            cornerRadius = xml.getDimension(R.styleable.FlatButton_pb_cornerRadius, defValue);
            mNormalDrawable.addState(new int[]{android.R.attr.state_pressed}, createPressedDrawable());
            mNormalDrawable.addState(new int[]{android.R.attr.state_focused}, createPressedDrawable());
            mNormalDrawable.addState(new int[]{android.R.attr.state_selected}, createPressedDrawable());
            mNormalDrawable.addState(new int[]{-android.R.attr.state_enabled}, createDisabledDrawable());
            mNormalDrawable.addState(new int[]{}, createNormalDrawable());
        } finally {
            xml.recycle();
        }
    }


    private LayerDrawable drawble_pad_button() {
        LayerDrawable drawableNormal =
                (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();

        GradientDrawable drawableTop =
                (GradientDrawable) drawableNormal.getDrawable(0).mutate();
        drawableTop.setCornerRadius(getCornerRadius());

        int blueDark = getResources().getColor(R.color.blue_pressed);
        int colorPressed = xml.getColor(R.styleable.FlatButton_pb_colorPressed, blueDark);
        drawableTop.setColor(colorPressed);

        GradientDrawable drawableBottom =
                (GradientDrawable) drawableNormal.getDrawable(1).mutate();
        drawableBottom.setCornerRadius(getCornerRadius());

        int blueNormal = getResources().getColor(R.color.blue_normal);
        int colorNormal = xml.getColor(R.styleable.FlatButton_pb_colorNormal, blueNormal);
        drawableBottom.setColor(colorNormal);
        return drawableNormal;
    }

    private LayerDrawable drawble_flat_button() {
        drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_stroke).mutate();

        GradientDrawable drawableBottom =
                (GradientDrawable) drawableNormal.getDrawable(0);
        drawableBottom.setCornerRadius(getCornerRadius());

        int blueNormal = getResources().getColor(R.color.blue_normal);
        int colorNormal = xml.getColor(R.styleable.FlatButton_pb_colorNormal, blueNormal);
        drawableBottom.setColor(colorNormal);

        int colorStroke = xml.getColor(R.styleable.FlatButton_pb_colorBorder, blueNormal);
        int strokeWidth = xml.getDimensionPixelOffset(R.styleable.FlatButton_pb_borderWidth, borderWidthDefault);
        drawableBottom.setStroke(strokeWidth, colorStroke);
        return drawableNormal;
    }


    private int getColorXML(@ColorRes int colorResId, @StyleableRes int styleIdColor) {
        int default_color = getResources().getColor(colorResId);
        return xml.getColor(styleIdColor, default_color);
    }

    private GradientDrawable getLayer(int n, int resIdLayers) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(resIdLayers).mutate();
        return (GradientDrawable) drawableNormal.getDrawable(n).mutate();
    }

    private GradientDrawable getLayer(int n, LayerDrawable j) {
        return (GradientDrawable) j.getDrawable(n).mutate();
    }

    public void setDisableDrawable(GradientDrawable disableDrawable) {
        mDisabledDrawable = disableDrawable;
    }

    private LayerDrawable createNormalDrawable() {
        switch (button_presentation) {
            case BUTTON_BOTTOM_PAD:
                return drawble_pad_button();
            case BUTTON_FLAT:
                return drawble_flat_button();
            default:
                return drawble_pad_button();
        }
    }


    public LayerDrawable creatNormalDrawable(int one_color) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getLayer(0, drawableNormal);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getResources().getColor(one_color));
        GradientDrawable drawableBottom = getLayer(1, drawableNormal);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getResources().getColor(one_color));
        return drawableNormal;
    }

    public LayerDrawable creatNormalDrawable(int top_color_id, int bottom_color_id) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getLayer(0, drawableNormal);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getResources().getColor(top_color_id));
        GradientDrawable drawableBottom = getLayer(1, drawableNormal);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getResources().getColor(bottom_color_id));
        return drawableNormal;
    }

    private LayerDrawable createDisabledDrawable() {
        LayerDrawable normal = (LayerDrawable) getDrawable(R.drawable.rect_disable).mutate();
        GradientDrawable layer1 = getLayer(1, normal);
        layer1.setCornerRadius(getCornerRadius());
        GradientDrawable layer0 = getLayer(0, normal);
        layer0.setCornerRadius(getCornerRadius());
        layer0.setColor(getColorXML(R.color.grey_disabled, R.styleable.FlatButton_pb_colorDisabled));
        return normal;
    }

    private Drawable createPressedDrawable() {
        GradientDrawable drawablePressed = (GradientDrawable) getDrawable(R.drawable.rect_pressed).mutate();
        drawablePressed.setCornerRadius(getCornerRadius());
        drawablePressed.setColor(getColorXML(R.color.blue_pressed, R.styleable.FlatButton_pb_colorPressed));
        return drawablePressed;
    }

    protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    protected float getDimension(int id) {
        return getResources().getDimension(id);
    }


    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public StateListDrawable getNormalDrawable() {
        return mNormalDrawable;
    }

    public CharSequence getNormalText() {
        return mNormalText;
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     *
     * @param drawable the drawable
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }
}
