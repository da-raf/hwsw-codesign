package com.hwswcodesign.legoapp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class SenderAsyncTask extends AsyncTask<Void, String, Void> {

	InetAddress receiverAddress;
	DatagramSocket datagramSocket;

	// unfinished but working sending method
	// sends the string message "i" to receiver with IP=1.2.3.4:2014
	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			datagramSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		byte[] buffer = "i".getBytes();
		// String message = "i";
		String address = "1.2.3.4";

		try {
			receiverAddress = InetAddress.getByName(address);
			boolean status = receiverAddress.isReachable(3000);
			if (status) {
				System.out.println("REACHABLE");
			} else {
				System.out.println("NOT REACHABLE");
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2) {
			System.out.println("NOT REACHABLE");
		}

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				receiverAddress, 2014);

		while (true) {
			try {
				System.out.println("sending i");
				datagramSocket.send(packet);
				try {
					wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * public String Message = "i"; private Activity act;
	 * 
	 * public SenderAsyncTask(Activity _act) { this.act = _act; }
	 * 
	 * 
	 * protected Void doInBackground(Void... params) { DatagramSocket socket =
	 * null;
	 * 
	 * try { socket = new DatagramSocket(2014); DatagramPacket packet = new
	 * DatagramPacket(Message.getBytes(), Message.length(),
	 * Main.BroadcastAddress, Main.SERVER_PORT); socket.setBroadcast(true);
	 * socket.send(packet); } catch (Exception e) { e.printStackTrace(); }
	 * finally { if (socket != null) { socket.close(); } } return null; }
	 * 
	 * protected void onPostExecute(Void result) { super.onPostExecute(result);
	 * }
	 * 
	 * if (Build.VERSION.SDK_INT >= 11)
	 * async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); else
	 * async_cient.execute(); }
	 **/
}
