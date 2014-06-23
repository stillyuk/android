package com.example.test.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author jiangyukun
 * @since 2014-06-20 16:00
 */
public class CustomView extends View {

	private Paint paint = new Paint(Paint.DITHER_FLAG);

	public CustomView(Context context) {
		super(context);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(0, 0, 200, 200, paint);
	}
}
