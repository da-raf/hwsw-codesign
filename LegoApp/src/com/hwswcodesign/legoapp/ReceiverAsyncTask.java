package com.hwswcodesign.legoapp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class ReceiverAsyncTask extends AsyncTask<Void, String, Void> {
	private Activity act;

	public ReceiverAsyncTask(Activity _act) {
		this.act = _act;
	}

	// unfinished/maybe working receiver method that tries to receive a
	// DatagramPacket over Port 2014
	@Override
	protected Void doInBackground(Void... params) {
		byte[] receiveData = new byte[1024];
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(2014);
			socket.setSoTimeout(30000);
			DatagramPacket packet = new DatagramPacket(receiveData,
					receiveData.length);
			socket.receive(packet);
			String contents = new String(packet.getData());
			publishProgress(contents);
			socket.close();
		} catch (SocketException e) {
			socket.close();
		} catch (SocketTimeoutException e) {
			socket.close();
		} catch (IOException e) {
			socket.close();
		}
		return (Void) null;
	}

	@Override
	protected void onProgressUpdate(String... str) {
		Toast.makeText(act, str[0], Toast.LENGTH_SHORT).show();
	}
}
