package com.example.test;

import service.JService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.view.JCustomButtonView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new JCustomButtonView(this));
		Intent service = new Intent(this, JService.class);
		startService(service);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			new AlertDialog.Builder(this).setTitle("aaa").setMessage("bbb").show();
			getFragmentManager().beginTransaction().replace(R.id.container, new TestFragment()).commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
