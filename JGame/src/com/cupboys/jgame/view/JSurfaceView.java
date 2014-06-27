package com.cupboys.jgame.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.cupboys.jgame.R;
import com.cupboys.jgame.controller.JImagePositionController;

/**
 * @author jiangyukun
 * @since 2014-06-23 19:50
 */
public class JSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final long REFRESH_INTERVAL = 20;
	protected static final int UPDATE = 0;
	private SurfaceHolder holder;
	private Canvas canvas;
	private Matrix matrix = new Matrix();
	private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	private Bitmap sprite = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
	private JImagePositionController backgroundController;
	private List<JBullet> bullets = new ArrayList<JBullet>();

	private Integer spriteX = 100, spriteY = 100;
	private Boolean pX, pY;
	private Boolean runFlag;

	Handler mHandler = new MyHandler(getContext());

	static class MyHandler extends Handler {
		private Context context;

		public MyHandler(Context context) {
			this.context = context;
		}

		public void handleMessage(Message msg) {
			if (msg.what == UPDATE) {
				Toast.makeText(context, msg.getData().getString("bulletNumber"), Toast.LENGTH_SHORT).show();
			}
			super.handleMessage(msg);
		}
	};

	public JSurfaceView(Context context) {
		super(context);
		holder = super.getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		Thread thread = new Thread(this);
		runFlag = true;
		pX = pY = true;
		JScreenManager.setWidth(getWidth());
		JScreenManager.setHeight(getHeight());
		backgroundController = new JImagePositionController(background, getWidth(), getHeight());
		matrix.postTranslate(10, 10);
		thread.start();
	}

	private void logic() {
		while (runFlag) {
			long start = System.currentTimeMillis();
			refresh();
			draw();
			long end = System.currentTimeMillis();
			if (end - start < REFRESH_INTERVAL) {
				try {
					Thread.sleep(REFRESH_INTERVAL - (end - start));
				} catch (InterruptedException ex) {
				}
			}
		}
	}

	private void refresh() {
		canvas = holder.lockCanvas();
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(background, backgroundController.getX(), backgroundController.getY(), new Paint());
		backgroundController.next();
		holder.unlockCanvasAndPost(canvas);
	}

	private void draw() {
		try {
			canvas = holder.lockCanvas();
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setTextSize(50);
			canvas.drawText("次都是", spriteX, spriteY, paint);
			canvas.save();
			canvas.clipRect(20, 20, 500, 1000);
			canvas.drawBitmap(sprite, matrix, paint);
			canvas.restore();
			canvas.drawText("啦啦啦", spriteX * 2, spriteY * 2, paint);
			matrix.postRotate(1);
			for (JBullet bullet : bullets) {
				canvas.save();
				canvas.rotate(180, getWidth() / 2, getHeight() / 2);
				canvas.drawRect(bullet.getRect(), paint);
				bullet.next(Orientation.VERTICAL);
				if (bullet.isOverBorder()) {
					bullets.remove(bullet);
				}
				canvas.restore();
			}
			nextPosition();
		} catch (Exception e) {
		} finally {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		JBullet bullet = new JBullet(spriteX, spriteY, (int) (Math.random() * 10) + 1);
		bullets.add(bullet);
		return super.onTouchEvent(event);
	}

	@Override
	public void run() {
		Looper.prepare();
		Thread computeBulletNumber = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Message msg = new Message();
					msg.what = UPDATE;
					msg.getData().putString("bulletNumber", Integer.toString(bullets.size()));
					mHandler.sendMessage(msg);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		computeBulletNumber.setDaemon(true);
		computeBulletNumber.start();
		logic();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		synchronized (runFlag) {
			runFlag = false;
		}
	}

	private void nextPosition() {
		if (pX) {
			spriteX++;
		} else {
			spriteX--;
		}
		if (spriteX < 0 || spriteX > getWidth()) {
			pX = !pX;
		}

		if (pY) {
			spriteY++;
		} else {
			spriteY--;
		}
		if (spriteY < 0 || spriteY > getHeight()) {
			pY = !pY;
		}
	}
}
