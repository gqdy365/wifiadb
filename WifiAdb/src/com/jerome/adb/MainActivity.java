package com.jerome.adb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button start = null;
	Button stop = null;
	TextView text = null;
	AdbConnect aConnect = null;

	String[] startCommand = new String[] { 
			"setprop service.adb.tcp.port 5555",// 设置监听的端口，端口可以自定义，如5554，5555是默认的
			"stop adbd",// 关闭adbd
			"start adbd"// 重新启动adbd
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		start = (Button) findViewById(R.id.start_adb);
		stop = (Button) findViewById(R.id.stop_adb);
		text = (TextView) findViewById(R.id.adb_text);

		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		
		aConnect = new AdbConnect();
		String ip = aConnect.getWifiIp(this);
		text.setText("在电脑cmd中执行命令：adb connect "+ip);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_adb:
			aConnect.excuteStartShell();
//			aConnect.execCommand(startCommand);
//			ShellUtils.execCommand(startCommand, false);
			break;

		case R.id.stop_adb:
			aConnect.excuteStopShell();
			break;
		default:
			break;
		}
	}
}
