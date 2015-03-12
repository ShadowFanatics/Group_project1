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
	private int remainTime;
	private TextView levelText;
	private TextView timeText;
	private TextView scoreText;
	private TableLayout playTableLayout;
	private MyImageButton Images[];
	private int totalCard = 0;
	private int hasOpen = 0;
	private int level = 0;
	private int score = 0;

	private void init() {
		levelText = (TextView) findViewById(R.id.textView1);
		timeText = (TextView) findViewById(R.id.timer);
		scoreText = (TextView) findViewById(R.id.scoreText);
		playTableLayout = (TableLayout) findViewById(R.id.user_list);
		remainTime = 30;
		totalCard = 0;
		hasOpen = 0;
		level = 0;
		score = 0;
		gameTimer = new Timer();
		// 設定Timer(task為執行內容，0代表立刻開始,間格1秒執行一次)
		gameTimer.schedule(new TimerTask() {
			public void run() {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}, 0, 1000);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		totalCard = genLevel(++level);

	}

	private int genLevel(int input) {
		levelText.setText("Level " + String.valueOf(input));
		Images = new MyImageButton[25];
		int sizeX = input; // start from 1
		if (input >= 4)
			sizeX = 4;
		int sizeY = 4;
		int numberOftypes = Math.abs(sizeX * sizeY / 2);
		int typeCount[] = new int[numberOftypes + 1];
		for (int i = 0; i < sizeX; i++) { // tr為一列容器，用來存放按鈕
			TableRow tr = new TableRow(this);
			for (int j = 0; j < sizeY; j++) {
				int index = i * (sizeY) + j;
				Images[index] = new MyImageButton(this, MainActivity.this);
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
			if (!canClick)
				return;
			button = (MyImageButton) v;
			if (button.isOpen())
				return;
			openNumber++;
			if (openNumber == 2) {
				canClick = false;
				myHandler.postDelayed(openDelay, 500);
			} else {
				lastbutton = button;
			}
			button.open();
		}

		private Runnable runTimerStop = new Runnable() {
			public void run() {
				lastbutton.close();
				button.close();
				canClick = true;
				openNumber = 0;
				// 移除語法
				myHandler.removeCallbacks(runTimerStop);
				button = null;
				lastbutton = null;
			}
		};
		private Runnable openDelay = new Runnable() {
			public void run() {
				if (lastbutton.getType() != button.getType()) {
					// 幾秒後(delaySec)呼叫runTimerStop這個Runnable，再由這個Runnable去呼叫你想要做的事情
					myHandler.postDelayed(runTimerStop, 200);
					remainTime = remainTime - level;
				} else {
					hasOpen = hasOpen + 2;
					score += level * 10 * level;
					scoreText.setText("分數: " + String.valueOf(score));
					if (totalCard == hasOpen) {
						hasOpen = 0;
						playTableLayout.removeAllViewsInLayout();
						totalCard = genLevel(++level);
					}
					canClick = true;
					openNumber = 0;
					button = null;
					lastbutton = null;
				}

				myHandler.removeCallbacks(openDelay);
			}
		};
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
				if (remainTime <= 0) {
					gameTimer.cancel();
					gameTimer = null;
					setContentView(R.layout.end_layout);
					TextView scoreShow = (TextView) findViewById(R.id.textView3);
					scoreShow.setText("你得到分數: " + score);
					Button restart = (Button) findViewById(R.id.restartButton);
					restart.setOnClickListener(reset);
				}
				break;
			default:
				break;
			}
		}
	};

	private OnClickListener reset = new OnClickListener() {
		public void onClick(View v) {
			setContentView(R.layout.activity_main);
			init();
			totalCard = genLevel(++level);
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
