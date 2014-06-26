package com.cupboys.jgame.view;

import android.graphics.Rect;

/**
 * @author jiangyukun
 * @since 2014-06-25 15:05
 */
public class JBullet {
	private int width = 10;
	private int height = 50;
	private int speed;

	private int positionX;
	private int positionY;

	public JBullet(int positionX, int positionY, int speed) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
	}

	public void next(int orientation) {
		if (orientation == Orientation.HORIZONTAL) {
			positionX += speed;
		} else if (orientation == Orientation.VERTICAL) {
			positionY += speed;
		}
	}

	public Rect getRect() {
		return new Rect(positionX, positionY, positionX + width, positionY + height);
	}

	public boolean isOverBorder() {
		if (width < 0 || width > JScreenManager.getWidth()) {
			return true;
		}
		if (height < 0 || height > JScreenManager.getHeight()) {
			return true;
		}
		return false;
	}
}
