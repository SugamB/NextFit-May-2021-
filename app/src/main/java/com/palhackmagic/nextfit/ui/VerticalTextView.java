package com.palhackmagic.nextfit.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VerticalTextView extends androidx.appcompat.widget.AppCompatTextView {


    public VerticalTextView(@NonNull Context context) {
        super(context);
    }

    public VerticalTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // assigning opposite because view is vertical
        int _width = heightMeasureSpec;
        int _height = widthMeasureSpec;
        setMeasuredDimension(_width, _height);
    }

}
