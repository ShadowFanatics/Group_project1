package com.group.group_project1;

import java.util.Random;
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
	private Timer gameTimer;
	private int remainTime = 30;
	private TextView levelText;
	private TextView timeText;
	private TableLayout playTableLayout;
	private MyImageButton Images[];
	private int totalCard = 0;
	private int hasOpen = 0;
	private int level = 0;
	private int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		levelText = (TextView) findViewById(R.id.textView1);
		timeText = (TextView) findViewById(R.id.timer);
		// 宣告Timer
		gameTimer = new Timer();
		// 設定Timer(task為執行內容，0代表立刻開始,間格1秒執行一次)
		gameTimer.schedule(task, 0, 1000);

		// 產生layout，當成一個容器來存放物件

		playTableLayout = (TableLayout) findViewById(R.id.user_list);
		totalCard = genLevel(++level);
		
	}

	private int genLevel(int input) {
		levelText.setText("Level " + String.valueOf(input));
		Images = new MyImageButton[25];
		int sizeX = input; // start from 1
		int sizeY = 4;
		int numberOftypes = Math.abs(sizeX * sizeY / 2);
		int typeCount[] = new int[numberOftypes + 1];
		for (int i = 0; i < sizeX; i++) { // tr為一列容器，用來存放按鈕
			TableRow tr = new TableRow(this);
			for (int j = 0; j < sizeY; j++) {
				int index = i * (sizeY) + j;
				Images[index] = new MyImageButton(this,MainActivity.this);
				Images[index].setOnClickListener(click);
				tr.addView(Images[index]);
				int type = getRandomInt(1, numberOftypes);
				while (typeCount[type] >= 2) {
					type = getRandomInt(1, numberOftypes);
				}
				typeCount[type]++;
				Images[index].setType(type);
			} // for // 將tr加入layout
			playTableLayout.addView(tr);
		} // for

		return sizeX * sizeY;
	}

	private int getRandomInt(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

	private OnClickListener click = new OnClickListener() {
		private int openNumber = 0;
		private MyImageButton lastbutton;
		private MyImageButton button;
		Timer showTimer = new Timer();
		private boolean canClick = true;
		private Handler myHandler = new Handler();
		public void onClick(View v) {
			button = (MyImageButton) v;
			if (!canClick || button.isOpen())
				return;
			button.open();
			myHandler.postDelayed(openDelay, 500);
		}

		private Runnable runTimerStop = new Runnable() {
			public void run() {
				lastbutton.close();
				button.close();
				canClick = true;
				// 移除語法
				myHandler.removeCallbacks(runTimerStop);
			}
		};
		private Runnable openDelay = new Runnable() {
			public void run() {
				openNumber++;
				if (openNumber == 2) {
					if (lastbutton.getType() != button.getType()) {
						canClick = false;
						// 幾秒後(delaySec)呼叫runTimerStop這個Runnable，再由這個Runnable去呼叫你想要做的事情
						myHandler.postDelayed(runTimerStop, 200);
					} else {
						hasOpen = hasOpen + 2;
						if (totalCard == hasOpen) {
							hasOpen = 0;
							playTableLayout.removeAllViewsInLayout();
							totalCard = genLevel(++level);
						}
					}
					openNumber = 0;
				} else {
					lastbutton = button;
				}
				myHandler.removeCallbacks(openDelay);
			}
		};
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
				if (remainTime == 0) {
					gameTimer.cancel();
					setContentView(R.layout.end_layout);
				}
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
