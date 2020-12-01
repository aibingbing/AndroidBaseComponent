package com.aibb.android.base.example.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SquareTextView extends androidx.appcompat.widget.AppCompatTextView {

    private float hwRatio = 1f;
    private static final String key_hwRatio = "hwRatio";

    public SquareTextView(@NonNull Context context) {
        super(context);
        setAttributes(context, null);
    }

    public SquareTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributes(context, null);
    }

    public SquareTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, null);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        String packageName = "http://schemas.android.com/apk/res-auto";
        hwRatio = attrs.getAttributeFloatValue(packageName, key_hwRatio, 1f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int calculatedHeight = calculateHeightByRatio(width);

        if (calculatedHeight != height) {
            setMeasuredDimension(width, calculatedHeight);
        }

    }

    private int calculateHeightByRatio(int side) {
        return (int) (hwRatio * (float) side);
    }

    public float getHwRatio() {
        return hwRatio;
    }

    public void setXyRatio(float xyRatio) {
        this.hwRatio = xyRatio;
        this.invalidate();
    }

}
