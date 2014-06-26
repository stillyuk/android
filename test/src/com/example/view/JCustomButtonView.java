package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author jiangyukun
 * @since 2014-06-25 15:41
 */
public class JCustomButtonView extends ViewGroup {
	Paint paint = new Paint();
	Rect rect = new Rect(10, 10, 500, 500);
	Button button;
	private Context context;

	public JCustomButtonView(Context context) {
		super(context);
		this.context = context;
		button = new Button(context);
		button.setText("啦啦啦");
		addView(button);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		canvas.drawRect(rect, paint);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Toast.makeText(context, "lala", Toast.LENGTH_LONG).show();
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.layout(l, t, r, b);
		}
	}
}
