package com.hwswcodesign.legoapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	ConnectionManager conManager;
	FragmentManager frag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		frag = getFragmentManager();
		conManager = new ConnectionManager(this, frag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_connect:
			conManager.activateWifi();
			return true;
		case R.id.action_sendData:
			sendMessage();
			return true;
		case R.id.action_receiveData:
			receiveMessage();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// creates a dialog object and shows it on the screen, when the specified
	// access point was not found
	public void showAPnotFoundDialog(FragmentManager frag) {
		APnotFoundDialogFragment dialog = new APnotFoundDialogFragment();
		dialog.show(frag, "APnotFound");
	}

	// tries to receive a message after the button was pressed. - merely a
	// method for testing
	public void receiveMessage() {
		new ReceiverAsyncTask(MainActivity.this).execute();
	}

	// tries to send a message after the button was pressed. - merely a method
	// for testing
	public void sendMessage() {
		new SenderAsyncTask().execute();
	}

	/**
	 * protected void onStop(){ super.onStop();
	 * 
	 * conManager.wifiManager.setWifiEnabled(conManager.wasEnabled);
	 * this.finish(); }
	 **/

}
