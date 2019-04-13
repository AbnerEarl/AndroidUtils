package com.example.customutils.threadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * threadPool com.xh.threadPool 2018 2018-4-26 ����10:33:34 instructions��
 * author:liuhuiliang email:825378291@qq.com
 **/

public class ScheduledThreadPool {
	private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
	private boolean isShutdown = false;

	/**
	 * 
	 * 2018 2018-4-26 ����10:36:04 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ���б�����߳������������߳�
	 * @param threadFactory
	 *            �������̵߳Ĺ���
	 * @param handler
	 *            ���ڳ����̷߳�Χ�Ͷ���������ʹִ�б�����ʱ��ʹ�õĴ������
	 */
	public ScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {
		// TODO Auto-generated constructor stub
		mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
				corePoolSize, threadFactory, handler);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:41:12 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ���б�����߳������������߳�
	 * @param threadFactory
	 *            �������̵߳Ĺ���
	 */
	public ScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, threadFactory, ThreadPool.EXECUTION_HANDLER);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:41:24 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ���б�����߳������������߳�
	 * @param handler
	 *            ���ڳ����̷߳�Χ�Ͷ���������ʹִ�б�����ʱ��ʹ�õĴ������
	 */
	public ScheduledThreadPool(int corePoolSize,
			RejectedExecutionHandler handler) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, ThreadPool.FACTORY, handler);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:41:38 author��liuhuiliang email ��825378291@qq.com
	 * 
	 * @param corePoolSize
	 *            ���б�����߳������������߳�
	 */
	public ScheduledThreadPool(int corePoolSize) {
		// TODO Auto-generated constructor stub
		this(corePoolSize, ThreadPool.FACTORY);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:44:41 annotation���Ƿ��ִ������ author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param runnable
	 *            ����
	 * @return boolean
	 */
	private boolean isSchedule(Runnable runnable) {
		return !(runnable == null || isShutdown);
	}

	public ScheduledThreadPool() {
		// TODO Auto-generated constructor stub
		this(ThreadPool.CPRE_POOL_SIZE);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:46:06 annotation���ӳ��ύ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param command
	 *            ����
	 * @param delay
	 *            �ӳ�ʱ�䵥λΪ���� void
	 */
	public void submit(Runnable command, long delay) {
		if (isSchedule(command))
			mScheduledThreadPoolExecutor.schedule(command, delay,
					TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:50:03 annotation���ӳ��ύ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param command
	 *            ����
	 * @param delay
	 *            �ӳ�ʱ�䵥λΪ����
	 * @param period
	 *            ���ڵ�λΪ���� void
	 */
	public void submit(Runnable command, long delay, long period) {
		if (isSchedule(command))
			mScheduledThreadPoolExecutor.scheduleAtFixedRate(command, delay,
					period, TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����10:51:23 annotation���Ƴ����� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param command
	 *            Ҫ�Ƴ�������
	 * @return boolean
	 */
	public boolean remove(Runnable command) {
		return mScheduledThreadPoolExecutor.remove(command);
	}

	/**
	 * 
	 * 2018 2018-4-26 ����11:18:23 annotation���Ƴ����� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @param commands
	 *            Ҫ�Ƴ���������
	 * @return Collection<Runnable>
	 */
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

	/**
	 * 
	 * 2018 2018-4-26 ����11:57:31 annotation��ֹͣ�������� author��liuhuiliang email
	 * ��825378291@qq.com void
	 */
	public void shutdown() {
		isShutdown = true;
		if (!mScheduledThreadPoolExecutor.isShutdown())
			mScheduledThreadPoolExecutor.shutdown();
	}

	/**
	 * 
	 * 2018 2018-4-26 ����11:57:47 annotation��ֹͣ���� author��liuhuiliang email
	 * ��825378291@qq.com
	 * 
	 * @return Collection<Runnable>
	 */
	public Collection<Runnable> shutdownNow() {
		isShutdown = true;
		return mScheduledThreadPoolExecutor.shutdownNow();
	}
}
