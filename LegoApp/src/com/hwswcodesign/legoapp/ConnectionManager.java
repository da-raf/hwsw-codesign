package com.hwswcodesign.legoapp;

import android.net.wifi.*;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.text.TextUtils;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class ConnectionManager extends MainActivity {

	Context context;
	WifiManager wifiManager;
	FragmentManager frag;
	protected boolean wasEnabled;
	List<ScanResult> wireless;
	boolean connecting, networkFound;

	DatagramSocket datagramSocket;
	DatagramPacket packet;

	// Hardcoded SSID of the wifi, the app should connect with
	String carwifi = "WiFly-GSX-07";

	public ConnectionManager(Context context, FragmentManager frag) {
		this.context = context;
		this.frag = frag;
		wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wasEnabled = wifiManager.isWifiEnabled();
		connecting = false;
		networkFound = false;

		// Intent Filter for incomming wifi scan results
		IntentFilter i = new IntentFilter();
		i.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {
				// Code to execute when SCAN_RESULTS_AVAILABLE_ACTION event
				// occurs
				System.out.println("scan results incomming");
				wifiManager = (WifiManager) c
						.getSystemService(Context.WIFI_SERVICE);
				wireless = wifiManager.getScanResults();

				if (connecting) {
					connectToNetwork();
					connecting = false;
				}
			}
		}, i);
	}

	// action performed when Action button is pressed
	// activates tablet wifi and tries to connect to specified access point
	public void activateWifi() {

		if (!wifiManager.isWifiEnabled()) {
			System.out.println("activating wifi");
			wifiManager.setWifiEnabled(true);
			while (!wifiManager.isWifiEnabled()) {
			}
		}

		System.out.println("start Scan");
		if (wifiManager.startScan()) {
			connecting = true;
		}
	}

	// performed after scan results come in
	private void connectToNetwork() {
		System.out.println("prepare to connect");
		wifiManager.disconnect();
		for (ScanResult scan : wireless) {
			if (scan.SSID.equals(carwifi)) {
				networkFound = true;
				System.out.println("found network");
				System.out.println("connecting...");
				System.out.println(scan.SSID);

				// Adding the network
				WifiConfiguration config = new WifiConfiguration();
				config.SSID = "\"" + scan.SSID + "\"";
				// config.preSharedKey = "\"" + "PASSWORD" + "\"";

				config.status = WifiConfiguration.Status.ENABLED;
				config.allowedKeyManagement.set(KeyMgmt.NONE);
				config.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.NONE);
				config.allowedAuthAlgorithms
						.set(WifiConfiguration.AuthAlgorithm.OPEN);
				config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
				config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

				int netId = wifiManager.addNetwork(config);
				wifiManager.enableNetwork(netId, true);
				wifiManager.saveConfiguration();
				System.out.println("END");
				break;
			} else {
				networkFound = false;
				System.out.println("AP not found");
			}
		}

		// checking if connected to the correct SSID
		final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
		if (connectionInfo != null
				&& !TextUtils.isEmpty(connectionInfo.getSSID())) {
			if (connectionInfo.getSSID().equals(carwifi)) {
				System.out.println("CONNECTED!");
			} else {
				System.out.println("NOT CONNECTED!");
			}
		}

		// show Dialog if 'carwifi' was not in network list while connecting
		// attempt
		if (!networkFound)
			showAPnotFoundDialog(frag);
	}
	/**
	 * public void receiveMessages() {
	 * 
	 * try { datagramSocket = new DatagramSocket(2014); } catch (SocketException
	 * e1) { e1.printStackTrace(); }
	 * 
	 * byte[] buffer = new byte[10]; packet = new DatagramPacket(buffer,
	 * buffer.length);
	 * 
	 * try { datagramSocket.receive(packet); } catch (IOException e) {
	 * e.printStackTrace(); } byte[] buff = packet.getData();
	 * 
	 * System.out.println(buff.toString());
	 * 
	 * }
	 **/

}
