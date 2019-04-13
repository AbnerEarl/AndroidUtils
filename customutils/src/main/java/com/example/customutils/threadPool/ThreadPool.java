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

/**
 * threadPool com.xh.threadPool 2018 2018-4-26 ����9:38:46 instructions���̳߳�
 * author:liuhuiliang email:825378291@qq.com
 **/

public class ThreadPool {
	private static final String TAG = "ThreadPool";
	private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
			128);// ����
	protected static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();// ϵͳcpu����
	protected static final int CPRE_POOL_SIZE = CPU_COUNT + 1;// ����߳�Ĭ��ֵ
	protected static final int MAX_POOL_SIZE = (CPU_COUNT << 1) + 1;// ����߳���
	private ThreadPoolExecutor mThreadPoolExecutor;// û���ӳٵ��̳߳�
	protected static final long SECOND = 1000;// ��
	protected static final long MINUTE = 60 * SECOND;// ����
	protected static final long HOUR = 60 * MINUTE;// Сʱ
	protected static final long DAY = 24 * HOUR;// ��
	protected static final long KEEP_ALIVE_TIME = 10 * SECOND;// Ĭ�ϴ��ʱ��
	protected static final Factory FACTORY = new Factory();
	protected static final ExecutionHandler EXECUTION_HANDLER = new ExecutionHandler();
	private boolean isShutdown = false;// �Ƿ�ֹͣ

	/**
	 * 
	 * 2018 2018-4-26 ����9:56:37 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ���б�����߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 * @param unit
	 *            ����ʱ��ĵ�λ
	 * @param workQueue
	 *            �ȴ�����Ķ�������
	 * @param factory
	 *            �������̵߳Ĺ���
	 * @param handler
	 *            ���ڳ����̷߳�Χ�Ͷ���������ʹִ�б�����ʱ��ʹ�õĴ������
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			ThreadFactory factory, RejectedExecutionHandler handler) {
		mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
				keepAliveTime, unit, workQueue, factory, handler);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:04:47 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 * @param unit
	 *            ������ʱ�䵥λ
	 * @param workQueue
	 *            �ȴ�����Ķ�������
	 * @param factory
	 *            �������̵߳Ĺ���
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue,
			ThreadFactory factory) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue,
				factory, EXECUTION_HANDLER);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:06:22 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 * @param unit
	 *            ������ʱ�䵥λ
	 * @param workQueue
	 *            �ȴ�����Ķ�������
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue, FACTORY);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:08:05 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 * @param workQueue
	 *            �ȴ�����Ķ�������
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			BlockingQueue<Runnable> workQueue) {
		this(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				workQueue);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:09:38 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 * @param unit
	 *            ����ʱ��ĵ�λ
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime,
			TimeUnit unit) {
		this(corePoolSize, maxPoolSize, keepAliveTime, unit, WORK_QUEUE);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:10:44 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime) {
		this(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:11:25 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param maxPoolSize
	 *            �������������߳���
	 */
	public ThreadPool(int corePoolSize, int maxPoolSize) {
		this(corePoolSize, maxPoolSize, KEEP_ALIVE_TIME);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:12:22 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 */
	public ThreadPool(int corePoolSize, long keepAliveTime) {
		this(corePoolSize, MAX_POOL_SIZE, keepAliveTime);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:13:07 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ������������߳������������߳�
	 */
	public ThreadPool(int corePoolSize) {
		this(corePoolSize, MAX_POOL_SIZE);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:13:46 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param keepAliveTime
	 *            ���߳�������corePoolSizeʱ�����̵߳ȴ�������ʱ��
	 */
	public ThreadPool(long keepAliveTime) {
		this(CPRE_POOL_SIZE, keepAliveTime);
	}

	public ThreadPool() {
		this(CPRE_POOL_SIZE);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:15:33 annotation���Ƿ����ִ�� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param runnable
	 *            ����
	 * @return boolean
	 */
	private boolean isExecute(Runnable runnable) {
		return !(runnable == null || isShutdown);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:16:39 annotation���ύ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param runnable
	 *            ���� void
	 */
	public void submit(Runnable runnable) {
		if (isExecute(runnable))
			mThreadPoolExecutor.execute(runnable);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:23:44 annotation���ύ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param commands
	 *            ������ void
	 */
	public void submit(Collection<Runnable> commands) {
		if (commands == null || commands.size() <= 0 || isShutdown)
			return;
		int size = commands.size();
		Runnable[] runnables = commands.toArray(new Runnable[size]);
		for (int i = 0; i < size; i++) {
			submit(runnables[i]);
		}
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:24:44 annotation���Ƴ����� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param command
	 *            ��Ҫ�Ƴ�������
	 * @return boolean
	 */
	public boolean remove(Runnable command) {
		return mThreadPoolExecutor.remove(command);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:29:38 annotation���Ƴ����� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param commands
	 *            ��Ҫ�Ƴ���������
	 * @return Collection<Runnable>
	 */
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

	/**
	 * 
	 * 2018 2018-4-26 ����10:31:23 annotation��ֹͣ�������� author��liuhuiliang email
	 * ��825378291@qq.com void
	 */
	public void shutdown() {
		isShutdown = true;
		if (!mThreadPoolExecutor.isShutdown())
			mThreadPoolExecutor.shutdown();
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:32:46 annotation��ֹͣ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @return Collection<Runnable>
	 */
	public Collection<Runnable> shutdownNow() {
		isShutdown = true;
		return mThreadPoolExecutor.shutdownNow();
	}
}
