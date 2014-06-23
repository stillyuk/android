package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author jiangyukun
 * @since 2014-06-21 17:00
 */
public class JService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		sendBroadcast(new Intent("android.provider.Telephony.SMS_RECEIVED"));
//		Toast.makeText(this, "service1", Toast.LENGTH_LONG).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Toast.makeText(this, "service", Toast.LENGTH_LONG).show();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		IBinder binder = new Binder();
		return binder;
	}
}
