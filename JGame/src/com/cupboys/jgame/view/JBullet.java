package com.cupboys.jgame.view;

import android.graphics.Rect;

/**
 * @author jiangyukun
 * @since 2014-06-25 15:05
 */
public class JBullet extends JBaseView {
	private int speed;

	public JBullet(int positionX, int positionY, int speed) {
		setPositionX(positionX);
		setPositionY(positionY);
		this.speed = speed;
	}

	public void next(int orientation) {
		if (orientation == Orientation.HORIZONTAL) {
			setPositionX(getPositionX() - speed);
		} else if (orientation == Orientation.VERTICAL) {
			setPositionY(getPositionY() - speed);
		}
	}

	public Rect getRect() {
		return new Rect(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight());
	}

	public boolean isOverBorder() {
		if (getPositionX() < 0 || getPositionX() > JScreenManager.getWidth()) {
			return true;
		}
		if (getPositionY() < 0 || getPositionY() > JScreenManager.getHeight()) {
			return true;
		}
		return false;
	}
}
