package com.group.group_project1;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	private int remainTime = 100;
	private TextView timeText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timeText = (TextView) findViewById(R.id.timer);
		// 宣告Timer
		Timer timer01 = new Timer();
		// 設定Timer(task為執行內容，0代表立刻開始,間格1秒執行一次)
		timer01.schedule(task, 0, 1000);

		// 產生layout，當成一個容器來存放物件

		TableLayout layout = (TableLayout) findViewById(R.id.user_list);
		for (int f = 0; f <= 4; f++) { // tr為一列容器，用來存放按鈕
			TableRow tr = new TableRow(this);
			for (int c = 0; c <= 4; c++) {
				ImageButton b = new ImageButton(this);
				b.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_launcher));
				b.setOnClickListener(click);

				tr.addView(b);
			} // for // 將tr加入layout
			layout.addView(tr);
		} // for

		// 將layout加入畫面中
	}
	
	private OnClickListener click = new OnClickListener() {
		public void onClick(View v) {
			ImageButton button = (ImageButton) v;
			button.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_launcher));
		}
	};

	private TimerTask task = new TimerTask() {
		public void run() {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				remainTime--;
				timeText.setText(String.valueOf(remainTime));
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
