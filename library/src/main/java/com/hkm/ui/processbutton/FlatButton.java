package com.hkm.ui.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;


public class FlatButton extends Button {
    public static final int BUTTON_BOTTOM_PAD = 1, BUTTON_FLAT = 2, BUTTON_EXTENSION = 3;
    private StateListDrawable mNormalDrawable;
    private GradientDrawable mDisabledDrawable;
    protected CharSequence mNormalText;
    private float cornerRadius;
    protected TypedArray mAttr;
    protected int defBorderWidth;
    private int button_presentation;
    private LayerDrawable drawableNormal;
    protected int defColor_blue, defColor_blue_dark, defColor_white;
    private String font_name;

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
        getFont(context);
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

    protected void initAttributesExtension(Context c, AttributeSet t) {
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        mAttr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (mAttr == null) {
            return;
        }

        try {
            defColor_blue = getResources().getColor(R.color.blue_normal);
            defColor_blue_dark = getResources().getColor(R.color.blue_pressed);
            defBorderWidth = context.getResources().getDimensionPixelOffset(R.dimen.border_width);
            defColor_white = context.getResources().getColor(R.color.grey_disabled);
            float defValue = getDimension(R.dimen.corner_radius);
            cornerRadius = mAttr.getDimension(R.styleable.FlatButton_pb_cornerRadius, defValue);
            font_name = mAttr.getString(R.styleable.FlatButton_pb_fontName);
            button_presentation = extension_button_presentation(mAttr.getInt(R.styleable.FlatButton_pb_presentation, BUTTON_BOTTOM_PAD));
            initAttributesExtension(context, attributeSet);
            constructNormalDrawable(mNormalDrawable);
        } finally {
            mAttr.recycle();
        }
    }

    protected void getFont(Context context) {
        if (font_name != null) {
            try {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + font_name);
                if (typeface != null) {
                    setTypeface(typeface);
                }
            } catch (Exception e) {
                Log.d("fonrloading", e.getMessage());
            }
        }
    }

    protected void constructNormalDrawable(StateListDrawable list) {
        list.addState(new int[]{android.R.attr.state_pressed}, createPressedDrawable());
        list.addState(new int[]{android.R.attr.state_focused}, createPressedDrawable());
        list.addState(new int[]{android.R.attr.state_selected}, createPressedDrawable());
        list.addState(new int[]{-android.R.attr.state_enabled}, createDisabledDrawable());
        list.addState(new int[]{}, createNormalDrawable());
    }

    /**
     * maded for extension: please write your own extension in here
     *
     * @return the layer drawable in here
     */
    protected LayerDrawable drawable_button_extended() {
        return drawble_pad_button();
    }

    protected LayerDrawable getLayersById(@DrawableRes int id) {
        return (LayerDrawable) getDrawable(id).mutate();
    }

    protected GradientDrawable getFromLayer(final LayerDrawable draw, final int number_layer) {
        if (draw.getNumberOfLayers() > number_layer) {
            return (GradientDrawable) draw.getDrawable(number_layer).mutate();
        } else {
            return null;
        }
    }


    private int getColorXML(@ColorRes int colorResId, @StyleableRes int styleIdColor) {
        int default_color = getResources().getColor(colorResId);
        return mAttr.getColor(styleIdColor, default_color);
    }

    public void setDisableDrawable(GradientDrawable disableDrawable) {
        mDisabledDrawable = disableDrawable;
    }

    private LayerDrawable drawble_pad_button() {
        final LayerDrawable drawableNormal = getLayersById(R.drawable.rect_normal);

        final GradientDrawable drawableTop = getFromLayer(drawableNormal, 0);
        drawableTop.setCornerRadius(getCornerRadius());

        int colorPressed = mAttr.getColor(R.styleable.FlatButton_pb_colorPressed, defColor_blue_dark);
        drawableTop.setColor(colorPressed);

        final GradientDrawable drawableBottom = getFromLayer(drawableNormal, 1);

        drawableBottom.setCornerRadius(getCornerRadius());

        int colorNormal = mAttr.getColor(R.styleable.FlatButton_pb_colorNormal, defColor_blue);
        drawableBottom.setColor(colorNormal);
        return drawableNormal;
    }

    private LayerDrawable drawble_flat_button() {
        final LayerDrawable drawableNormal = getLayersById(R.drawable.rect_stroke);

        GradientDrawable drawableBottom = getFromLayer(drawableNormal, 0);

        drawableBottom.setCornerRadius(getCornerRadius());

        int colorNormal = mAttr.getColor(R.styleable.FlatButton_pb_colorNormal, defColor_blue);
        drawableBottom.setColor(colorNormal);

        int colorStroke = mAttr.getColor(R.styleable.FlatButton_pb_colorBorder, defColor_blue);
        int strokeWidth = mAttr.getDimensionPixelOffset(R.styleable.FlatButton_pb_borderWidth, defBorderWidth);

        drawableBottom.setStroke(strokeWidth, colorStroke);
        return drawableNormal;
    }


    private LayerDrawable createNormalDrawable() {
        switch (button_presentation) {
            case BUTTON_BOTTOM_PAD:
                return drawble_pad_button();
            case BUTTON_FLAT:
                return drawble_flat_button();
            default:
                return drawable_button_extended();
        }
    }


    public LayerDrawable creatNormalDrawable(int top_color_id, int bottom_color_id) {
        LayerDrawable drawableNormal = (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();
        GradientDrawable drawableTop = getFromLayer(drawableNormal, 0);
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setColor(getResources().getColor(top_color_id));
        GradientDrawable drawableBottom = getFromLayer(drawableNormal, 1);
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setColor(getResources().getColor(bottom_color_id));
        return drawableNormal;
    }

    protected LayerDrawable createDisabledDrawable() {
        LayerDrawable normal = (LayerDrawable) getDrawable(R.drawable.rect_disable).mutate();
        GradientDrawable layer1 = getFromLayer(normal, 1);
        layer1.setCornerRadius(getCornerRadius());
        GradientDrawable layer0 = getFromLayer(normal, 0);
        layer0.setCornerRadius(getCornerRadius());
        layer0.setColor(getColorXML(R.color.grey_disabled, R.styleable.FlatButton_pb_colorDisabled));
        return normal;
    }

    protected Drawable createPressedDrawable() {
        GradientDrawable drawablePressed = (GradientDrawable) getDrawable(R.drawable.rect_pressed).mutate();
        drawablePressed.setCornerRadius(getCornerRadius());
        drawablePressed.setColor(getColorXML(R.color.blue_pressed, R.styleable.FlatButton_pb_colorPressed));
        return drawablePressed;
    }

    protected Drawable getDrawable(int id) {
        Drawable drawable;
        drawable = getResources().getDrawable(id);
        return drawable;
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
     * @param draw the drawable
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable draw) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(draw);
        } else {
            setBackgroundDrawable(draw);
        }
        setPadding(pL, pT, pR, pB);
    }

}
