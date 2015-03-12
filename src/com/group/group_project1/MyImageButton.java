package com.group.group_project1;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageButton;

public class MyImageButton extends ImageButton {
	private int type = 0;
	private boolean isOpen = false;
	private CardAnimation cardAnimation;
	public static int images[] = { R.drawable.card_back, R.drawable.card1,
			R.drawable.card2, R.drawable.card3, R.drawable.card4,
			R.drawable.card5, R.drawable.card6, R.drawable.card7,
			R.drawable.card8 };

	public MyImageButton(Context context,Activity activity) {
		super(context);
		this.setMinimumWidth(25);
		this.setMinimumHeight(25);
		this.setMaxWidth(25);
		this.setMaxHeight(25);
		setImageDrawable(getResources().getDrawable(images[0]));
		cardAnimation = new CardAnimation(activity);
	}

	public void open() {
//		setImageDrawable(getResources().getDrawable(images[type]));
		cardAnimation.turnOverFast(this, getResources().getDrawable(images[type]));
		isOpen = true;
	}

	public void close() {
		cardAnimation.turnOverFast(this, getResources().getDrawable(images[0]));
		isOpen = false;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setType(int input) {
		type = input;
	}

	public int getType() {
		return type;
	}

}
