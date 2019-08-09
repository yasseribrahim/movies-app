package com.cyber.accounting.movies.app.presentation.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.cyber.accounting.movies.app.R;

/**
 * Created by Yasser.Ibrahim on 1/24/2017.
 */

public class RoundedImageView extends AppCompatImageView {

    public float radius = 18.0f;

    public RoundedImageView(Context context) {
        super(context);
        radius = context.getResources().getDimension(R.dimen.image_rounded_corners);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        radius = context.getResources().getDimension(R.dimen.image_rounded_corners);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        radius = context.getResources().getDimension(R.dimen.image_rounded_corners);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}