package com.group.group_project1;

import android.app.Activity;
import android.graphics.drawable.Drawable;
//import android.widget.Button;
import android.widget.ImageButton;


public class CardAnimation
{
	private final long time = 100;
	private final long timeShort = 50;
	private Activity activity;
	//private Button currentCard;
	private ImageButton currentCard;
	private Drawable currentDrawable;
	
	public CardAnimation(Activity activity)
	{
		this.activity = activity;
	}
	/*
	public void turnOver(Button card, Drawable drawable)
	{
		currentCard = card;
		currentDrawable = drawable;
		animation();
	}
	
	public void turnOverFast(Button card, Drawable drawable)
	{
		currentCard = card;
		currentDrawable = drawable;
		animationFast();
	}
	*/
	public void turnOver(ImageButton card, Drawable drawable)
	{
		currentCard = card;
		currentDrawable = drawable;
		animation();
	}
	
	public void turnOverFast(ImageButton card, Drawable drawable)
	{
		currentCard = card;
		currentDrawable = drawable;
		animationFast();
	}
	
	private void animation()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					//Only the original thread that created a view hierarchy
					//can touch its views
					activity.runOnUiThread(scaleDown);
					sleep(time);
					activity.runOnUiThread(scaleDown);
					sleep(time);
					activity.runOnUiThread(scaleDown);
					sleep(time);
					activity.runOnUiThread(changeDrawable);
					sleep(time);
					activity.runOnUiThread(scaleUp);
					sleep(time);
					activity.runOnUiThread(scaleUp);
					sleep(time);
					activity.runOnUiThread(scaleUp);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	private void animationFast()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					//Only the original thread that created a view hierarchy
					//can touch its views
					activity.runOnUiThread(scaleDown);
					sleep(timeShort);
					activity.runOnUiThread(scaleDown);
					sleep(timeShort);
					activity.runOnUiThread(scaleDown);
					sleep(timeShort);
					activity.runOnUiThread(changeDrawable);
					sleep(timeShort);
					activity.runOnUiThread(scaleUp);
					sleep(timeShort);
					activity.runOnUiThread(scaleUp);
					sleep(timeShort);
					activity.runOnUiThread(scaleUp);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	private Runnable scaleDown = new Runnable()
	{
		@Override
		public void run()
		{
			currentCard.setScaleX(currentCard.getScaleX()/2);
		}
	};
	private Runnable scaleUp = new Runnable()
	{
		@Override
		public void run()
		{
			currentCard.setScaleX(currentCard.getScaleX()*2);
		}
	};
	private Runnable changeDrawable = new Runnable()
	{
		@Override
		public void run()
		{
			//currentCard.setBackground(currentDrawable);
			currentCard.setImageDrawable(currentDrawable);
		}
	};
}
