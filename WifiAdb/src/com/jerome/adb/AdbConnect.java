package com.jerome.adb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.stericson.RootTools.RootTools;

public class AdbConnect {

	private String TAG = AdbConnect.class.getSimpleName();

	public String getWifiIp(Context context) {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}

	private String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	public boolean execute(String cmd) {
		boolean isSuccess = false;
		try {
//			Process proc = Runtime.getRuntime().exec("su");
			Process proc = Runtime.getRuntime().exec("sh");
			proc.getOutputStream().write(cmd.getBytes());
			proc.getOutputStream().flush();
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
			isSuccess = false;
		}
		Log.v("", "---> cmd : " + cmd + "  , isSuccess : " + isSuccess);
		return isSuccess;
	}

	public void excuteStartShell() {
		String[] commands = new String[] { 
				"setprop service.adb.tcp.port 5555",// 设置监听的端口，端口可以自定义，如5554，5555是默认的
				"stop adbd",// 关闭adbd
				"start adbd",// 重新启动adbd
		};
		try {
			List<String> temp = RootTools.sendShell(commands, 10, 3000);
			for (int i = 0; i < temp.size(); i++) {
				Log.i(TAG, "__This is result from root:__" + temp.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excuteStopShell() {
		String[] commands = new String[] {
				"setprop service.adb.tcp.port -1",// 设置监听的端口，端口可以自定义，如5554，5555是默认的
				"stop adbd",// 关闭adbd
				"start adbd",// 重新启动adbd
		};
		try {
			List<String> temp = RootTools.sendShell(commands, 10, 3000);
			for (int i = 0; i < temp.size(); i++) {
				Log.i(TAG, "__This is result from root:__" + temp.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execCommand(String command) throws IOException {
        Log.i(TAG, "execCommand:" + command);
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        try {
            if (proc.waitFor() != 0) {
                Log.i(TAG, "exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line+"-");
            }
            Log.i(TAG, stringBuffer.toString());
 
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }
	
	public void execCommand(final String[] command) {
		new Thread(){
			public void run(){
				for(int i=0;i<command.length;i++){
					try {
						execCommand(command[i]);
						Thread.sleep(200);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
