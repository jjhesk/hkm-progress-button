package com.hkm.ui.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;


public class FlatButton extends Button {

    private StateListDrawable mNormalDrawable;
    private GradientDrawable mDisabledDrawable;
    private CharSequence mNormalText;
    private float cornerRadius;
    private TypedArray xml;

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

    private void initAttributes(Context context, AttributeSet attributeSet) {
        xml = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (xml == null) {
            return;
        }

        try {
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

    private int getColorXML(int colorResId, int styleIdColor) {
        int default_color = getColor(colorResId);
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
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getLayer(0, drawableNormal);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getColorXML(R.color.blue_pressed, R.styleable.FlatButton_pb_colorPressed));
        GradientDrawable drawableBottom = getLayer(1, drawableNormal);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getColorXML(R.color.blue_normal, R.styleable.FlatButton_pb_colorNormal));
        return drawableNormal;
    }

    private int getColorResource(int color) {
        return getResources().getColor(color);

    }

    public LayerDrawable creatNormalDrawable(int one_color) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getLayer(0, drawableNormal);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getColorResource(one_color));
        GradientDrawable drawableBottom = getLayer(1, drawableNormal);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getColorResource(one_color));
        return drawableNormal;
    }

    public LayerDrawable creatNormalDrawable(int top_color_id, int bottom_color_id) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getLayer(0, drawableNormal);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getColorResource(top_color_id));
        GradientDrawable drawableBottom = getLayer(1, drawableNormal);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getColorResource(bottom_color_id));
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

    protected int getColor(int id) {
        return getResources().getColor(id);
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
     * @param drawable
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
