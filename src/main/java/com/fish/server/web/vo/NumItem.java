package com.fish.server.web.vo;

public class NumItem {
	public NumItem(int value, int priority) {
	    super();
	    this.value = value;
	    this.priority = priority;
	}

	/** 值 */
	private int value;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/** 概率 */
	private int priority;
}
