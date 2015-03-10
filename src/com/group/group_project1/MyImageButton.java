package com.group.group_project1;

import android.content.Context;
import android.widget.ImageButton;

public class MyImageButton extends ImageButton {
	private int type = 0;
	private boolean isOpen = false;
	private static int images[] = { R.drawable.ic_launcher, R.drawable.card1,
			R.drawable.card2, R.drawable.card3, R.drawable.card4,
			R.drawable.card5, R.drawable.card6, R.drawable.card7,
			R.drawable.card8 };

	public MyImageButton(Context context) {
		super(context);
		this.setMinimumWidth(100);
		this.setMinimumHeight(100);
		this.setMaxWidth(100);
		this.setMaxHeight(100);
		setImageDrawable(getResources().getDrawable(images[0]));
	}

	public void open() {
		setImageDrawable(getResources().getDrawable(images[type]));
		isOpen = true;
	}

	public void close() {
		setImageDrawable(getResources().getDrawable(images[0]));
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
