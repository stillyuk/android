package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author jiangyukun
 * @since 2014-06-23 15:02
 */
public class JBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
	}

}
