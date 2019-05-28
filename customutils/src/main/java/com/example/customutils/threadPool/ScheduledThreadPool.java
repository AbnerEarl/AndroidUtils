package com.example.customutils.threadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;



public class ScheduledThreadPool {
	private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
	private boolean isShutdown = false;


	public ScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		// TODO Auto-generated constructor stub
		mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
				corePoolSize, threadFactory, handler);
	}


	public ScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, threadFactory, ThreadPool.EXECUTION_HANDLER);
	}


	public ScheduledThreadPool(int corePoolSize,
			RejectedExecutionHandler handler) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, ThreadPool.FACTORY, handler);
	}

	public ScheduledThreadPool(int corePoolSize) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, ThreadPool.FACTORY);
	}


	private boolean isSchedule(Runnable runnable) {
		return !(runnable == null || isShutdown);
	}

	public ScheduledThreadPool() {
		// TODO Auto-generated constructor stub
		this(ThreadPool.CPRE_POOL_SIZE);
	}


	public void submit(Runnable command, long delay) {
		if (isSchedule(command))
			mScheduledThreadPoolExecutor.schedule(command, delay,
					TimeUnit.MILLISECONDS);
	}


	public void submit(Runnable command, long delay, long period) {
		if (isSchedule(command))
			mScheduledThreadPoolExecutor.scheduleAtFixedRate(command, delay,
					period, TimeUnit.MILLISECONDS);
	}


	public boolean remove(Runnable command) {
		return mScheduledThreadPoolExecutor.remove(command);
	}


	public Collection<Runnable> remove(Collection<Runnable> commands) {
		if (commands == null || commands.size() <= 0)
			return null;
		int size = commands.size();
		List<Runnable> mList = new ArrayList<Runnable>(size);
		Runnable[] runnables = commands.toArray(new Runnable[size]);
		for (int i = 0; i < size; i++) {
			Runnable command = runnables[i];
			if (remove(command))
				continue;
			mList.add(command);
		}
		return mList;
	}


	public void shutdown() {
		isShutdown = true;
		if (!mScheduledThreadPoolExecutor.isShutdown())
			mScheduledThreadPoolExecutor.shutdown();
	}


	public Collection<Runnable> shutdownNow() {
		isShutdown = true;
		return mScheduledThreadPoolExecutor.shutdownNow();
	}
}
