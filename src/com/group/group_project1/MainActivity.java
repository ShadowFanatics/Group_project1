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
	private int remainTime = 100;
	private TextView timeText;
	private TableLayout playTableLayout;
	private MyImageButton Images[];
	private int hasOpen = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timeText = (TextView) findViewById(R.id.timer);
		// �ŧiTimer
		Timer timer01 = new Timer();
		// �]�wTimer(task�����椺�e�A0�N��ߨ�}�l,����1�����@��)
		timer01.schedule(task, 0, 1000);

		// ����layout�A���@�Ӯe���Ӧs�񪫥�

		playTableLayout = (TableLayout) findViewById(R.id.user_list);
		genLevel(1);
	}

	private void genLevel(int input) {
		Images = new MyImageButton[25];
		int sizeX = 4; // start from 1
		int sizeY = 4;
		int numberOftypes = Math.abs(sizeX * sizeY / 2);
		int typeCount[] = new int[numberOftypes + 1];
		for (int i = 0; i < sizeX; i++) { // tr���@�C�e���A�ΨӦs����s
			TableRow tr = new TableRow(this);
			for (int j = 0; j < sizeY; j++) {
				int index = i * (sizeY) + j;
				Images[index] = new MyImageButton(this);
				// Images[index].setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
				Images[index].setOnClickListener(click);
				tr.addView(Images[index]);
				int type = getRandomInt(1, numberOftypes);
				while (typeCount[type] >= 2) {
					type = getRandomInt(1, numberOftypes);
				}
				typeCount[type]++;
				Images[index].setType(type);
			} // for // �Ntr�[�Jlayout
			playTableLayout.addView(tr);
		} // for
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

		public void onClick(View v) {
			if (!canClick)
				return;
			button = (MyImageButton) v;
			button.open();
			openNumber++;
			if (openNumber == 2) {
				if (lastbutton.getType() != button.getType()) {
					canClick = false;
					Handler myHandler = new Handler();
					// �X���(delaySec)�I�srunTimerStop�o��Runnable�A�A�ѳo��Runnable�h�I�s�A�Q�n�����Ʊ�
					myHandler.postDelayed(runTimerStop, 500);
					// �����y�k:myHandler.removeCallbacks(runTimerStop);
				}
				else {
					hasOpen = hasOpen + 2;
				}
				openNumber = 0;
			} else {
				lastbutton = button;
			}
		}

		private Runnable runTimerStop = new Runnable() {
			public void run() {
				lastbutton.close();
				button.close();
				canClick = true;
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
