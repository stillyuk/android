package com.example.view;

import com.example.test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class JSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private SurfaceHolder surfaceHolder;
	private Paint paint;
	private int textX = 10, textY = 10;
	private Thread thread;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;

	public JSurfaceView(Context context) {
		super(context);
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		thread = new Thread(this);
		thread.start();
	}

	public void myDraw() {
		try {
			canvas = surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.drawText("Game", textX, textY, paint);
			canvas.drawPoints(new float[] { 10, 30, 30, 30 }, paint);
			canvas.drawRect(10, 60, 40, 100, paint);
			RectF rect = new RectF(10, 140, 60, 170);
			canvas.drawRoundRect(rect, 20, 20, paint);
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.la);
			canvas.rotate(30);
			canvas.drawBitmap(bitmap, 0, 0, paint);
		} catch (Exception e) {

		} finally {
			if (canvas != null) {
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		textX = (int) event.getX();
		textY = (int) event.getY();
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	private void logic() {

	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
