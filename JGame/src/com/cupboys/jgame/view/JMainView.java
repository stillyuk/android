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
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.cupboys.jgame.R;
import com.cupboys.jgame.controller.JImagePositionController;
import com.cupboys.jgame.listener.JContextViewListener;
import com.cupboys.jgame.listener.JViewListener;

/**
 * @author jiangyukun
 * @since 2014-06-23 19:50
 */
public class JMainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private static final long REFRESH_INTERVAL = 20;
	protected static final int UPDATE = 0;

	private List<JViewListener> viewListeners;

	private SurfaceHolder holder;
	private Canvas canvas;
	private Paint paint;
	private Matrix matrix = new Matrix();
	private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	private Bitmap spriteImage = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
	private JImagePositionController backgroundController;
	private List<JBullet> bullets;
	private List<JPlane> planes;

	private String spriteText = "J啦啦啦";
	private int spriteX = 100, spriteY = 100;
	private float spriteWidth, spriteHeight;
	private Boolean pX, pY;
	private Boolean runFlag;

	Handler mHandler = new MyHandler(getContext());

	public JMainView(Context context) {
		super(context);
		for (JViewListener viewListener : viewListeners) {
			if (viewListener instanceof JContextViewListener) {
				((JContextViewListener) viewListener).setContext(context);
			}
			viewListener.init(context);
		}
		holder = super.getHolder();
		holder.addCallback(this);
	}

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

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		for (JViewListener viewListener : viewListeners) {
			viewListener.start();
		}
		this.holder = holder;
		runFlag = true;
		pX = pY = true;
		paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setAlpha(90);
		paint.setTextSize(50);
		spriteWidth = paint.measureText(spriteText);
		FontMetrics fontMetrics = paint.getFontMetrics();
		spriteHeight = fontMetrics.descent - fontMetrics.ascent;
		JScreenManager.setWidth(getWidth());
		JScreenManager.setHeight(getHeight());
		backgroundController = new JImagePositionController(background, getWidth(), getHeight());
		bullets = new ArrayList<JBullet>();
		planes = new ArrayList<JPlane>();
		matrix.postTranslate(100, 100);
		Thread thread = new Thread(this);
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
			Path path = new Path();
			path.moveTo(spriteX - 50, spriteY - 50);
			path.lineTo(spriteX - 50, spriteY - 100);
			/*
			 * path.lineTo(spriteX - 100, spriteY - 100); path.lineTo(spriteX -
			 * 100, spriteY - 50);
			 */
			path.quadTo(20, 10, 200, 300);
			path.close();
			canvas.drawPath(path, paint);
			canvas.drawText(spriteText, spriteX, spriteY, paint);

			canvas.save();
			canvas.clipRect(20, 20, 500, 1000);
			canvas.drawBitmap(spriteImage, matrix, paint);
			canvas.restore();
			matrix.postRotate(1);
			for (JBullet bullet : bullets) {
				canvas.drawRect(bullet.getRect(), paint);
				bullet.next(Orientation.VERTICAL);
				if (bullet.isOverBorder()) {
					bullets.remove(bullet);
				}
			}
			nextPosition();
		} catch (Exception e) {
		} finally {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		JBullet bullet = new JBullet(spriteX, spriteY, (int) (Math.random() * 5) + 2);
		bullets.add(bullet);
		return super.onTouchEvent(event);
	}

	@Override
	public void run() {
		Looper.prepare();
		Thread computeBulletNumberThread = new Thread(new Runnable() {
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
		computeBulletNumberThread.setDaemon(true);
		computeBulletNumberThread.start();
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
			spriteX += 2;
		} else {
			spriteX -= 2;
		}
		if (spriteX < 0 || spriteX > getWidth() - spriteWidth) {
			pX = !pX;
		}

		if (pY) {
			spriteY += 2;
		} else {
			spriteY -= 2;
		}
		if (spriteY - spriteHeight < 0 || spriteY > getHeight()) {
			pY = !pY;
		}
	}
}
