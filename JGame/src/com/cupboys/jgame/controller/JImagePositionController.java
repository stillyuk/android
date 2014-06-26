package com.cupboys.jgame.controller;

import android.graphics.Bitmap;

/**
 * @author jiangyukun
 * @since 2014-06-24 18:41
 */
public class JImagePositionController {
	private int x = 2;
	private int y = 1;
	private Bitmap image;

	private boolean orientationX;
	private boolean orientationY;
	private int deltaX = 2;
	private int deltaY = 1;
	private int maxWidth;
	private int maxHeight;

	public JImagePositionController(Bitmap image, int maxWidth, int maxHeight) {
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.image = image;
	}

	public void next() {
		if (orientationX) {
			x += deltaX;
		} else {
			x -= deltaX;
		}
		if ((x == 0 || x + image.getWidth() == maxWidth) && image.getWidth() <= maxWidth) {
			orientationX = !orientationX;
		}
		if (image.getWidth() > maxWidth && (x > 0 || x + image.getWidth() < maxWidth)) {
			orientationX = !orientationX;
		}

		if (orientationY) {
			y += deltaY;
		} else {
			y -= deltaY;
		}
		if ((y == 0 || y + image.getHeight() == maxHeight) && image.getHeight() <= maxHeight) {
			orientationY = !orientationY;
		}
		if (image.getHeight() > maxHeight && (y > 0 || y + image.getHeight() < maxHeight)) {
			orientationY = !orientationY;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
