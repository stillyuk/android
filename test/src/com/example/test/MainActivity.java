package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.JService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(this);
		ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(200, 200);
		listView.setLayoutParams(layout);
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 15; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("a", "a");
			dataList.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.fragment_test, new String[] { "a" }, new int[] { R.id.button });
		listView.setAdapter(adapter);
		setContentView(listView);
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
