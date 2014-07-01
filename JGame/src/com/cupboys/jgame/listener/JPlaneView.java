package com.cupboys.jgame.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cupboys.jgame.R;
import com.cupboys.jgame.view.JBaseView;

/**
 * @author jiangyukun
 * @since 2014-07-01 10:41
 */
public class JPlaneView extends JBaseView implements JViewListener {
	private Context context;
	protected Bitmap plane;

	@Override
	public void init(Context context) {
		this.context = context;
		plane = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
	}

	@Override
	public void start() {

	}

	@Override
	public void destroy() {

	}
}
