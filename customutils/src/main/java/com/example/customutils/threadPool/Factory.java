package com.example.customutils.threadPool;

import java.util.concurrent.ThreadFactory;


public class Factory implements ThreadFactory {
	private final static String TAG = "Factory";

	@Override
	public Thread newThread(Runnable arg0) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(arg0, TAG + System.currentTimeMillis());
		thread.setPriority(Thread.MAX_PRIORITY);
		return thread;
	}

}
