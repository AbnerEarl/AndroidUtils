package com.example.customutils.threadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class ThreadPool {
	private static final String TAG = "ThreadPool";
	private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
			128);// ????
	protected static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();// ??cpu????
	protected static final int CPRE_POOL_SIZE = CPU_COUNT + 1;// ??????????
	protected static final int MAX_POOL_SIZE = (CPU_COUNT << 1) + 1;// ????????
	private ThreadPoolExecutor mThreadPoolExecutor;// ???????????
	protected static final long SECOND = 1000;// ??
	protected static final long MINUTE = 60 * SECOND;// ????
	protected static final long HOUR = 60 * MINUTE;// С?
	protected static final long DAY = 24 * HOUR;// ??
	protected static final long KEEP_ALIVE_TIME = 10 * SECOND;// ????????
	protected static final Factory FACTORY = new Factory();
	protected static final ExecutionHandler EXECUTION_HANDLER = new ExecutionHandler();
	private boolean isShutdown = false;// ?????


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			ThreadFactory factory, RejectedExecutionHandler handler) {
		mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
				keepAliveTime, unit, workQueue, factory, handler);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			ThreadFactory factory) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue,
				factory, EXECUTION_HANDLER);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue, FACTORY);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			BlockingQueue<Runnable> workQueue) {
		this(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				workQueue);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, WORK_QUEUE);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime) {
		this(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS);
	}


	public ThreadPool(int corePoolSize, int maxPoolSize) {
		this(corePoolSize, maxPoolSize, KEEP_ALIVE_TIME);
	}


	public ThreadPool(int corePoolSize, long keepAliveTime) {
		this(corePoolSize, MAX_POOL_SIZE, keepAliveTime);
	}

	public ThreadPool(int corePoolSize) {
		this(corePoolSize, MAX_POOL_SIZE);
	}


	public ThreadPool(long keepAliveTime) {
		this(CPRE_POOL_SIZE, keepAliveTime);
	}

	public ThreadPool() {
		this(CPRE_POOL_SIZE);
	}


	private boolean isExecute(Runnable runnable) {
		return !(runnable == null || isShutdown);
	}

	public void submit(Runnable runnable) {
		if (isExecute(runnable))
			mThreadPoolExecutor.execute(runnable);
	}


	public void submit(Collection<Runnable> commands) {
		if (commands == null || commands.size() <= 0 || isShutdown)
			return;
		int size = commands.size();
		Runnable[] runnables = commands.toArray(new Runnable[size]);
		for (int i = 0; i < size; i++) {
			submit(runnables[i]);
		}
	}


	public boolean remove(Runnable command) {
		return mThreadPoolExecutor.remove(command);
	}


	public Collection<Runnable> remove(Collection<Runnable> commands) {
		if (commands == null || commands.size() <= 0)
			return null;
		int size = commands.size();
		Runnable[] runnables = commands.toArray(new Runnable[size]);
		List<Runnable> mList = new ArrayList<Runnable>(size);
		for (int i = 0; i < size; i++) {
			Runnable runnable = runnables[i];
			if (remove(runnable))
				continue;
			mList.add(runnable);
		}
		return mList;
	}

	public void shutdown() {
		isShutdown = true;
		if (!mThreadPoolExecutor.isShutdown())
			mThreadPoolExecutor.shutdown();
	}

	public Collection<Runnable> shutdownNow() {
		isShutdown = true;
		return mThreadPoolExecutor.shutdownNow();
	}
}
