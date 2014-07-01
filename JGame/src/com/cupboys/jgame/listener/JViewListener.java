package com.cupboys.jgame.listener;

import android.content.Context;

/**
 * @author jiangyukun
 * @since 2014-07-01 10:19
 */
public interface JViewListener {
	public abstract void init(Context context);

	public abstract void start();

	public abstract void destroy();
}
