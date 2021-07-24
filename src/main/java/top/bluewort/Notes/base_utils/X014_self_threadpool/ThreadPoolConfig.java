package top.bluewort.Notes.base_utils.X014_self_threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {

    private static ThreadPoolExecutor threadPoolExecutor;
    /**
     * 核心线程数
     * IO密集型（频繁请求 读取磁盘）线程池核心线程数为N*2
     * CPU密集型（递归较多） 线程池核心线程N+1
     * 本处属于IO相对密集型+CPU相对密集型 故而折中 (2*N-1)
     */
    private static int corePoolSize = 4;
    /**
     * 最大线程数
     */
    private static int maximumPoolSize = 500;
    /**
     * 最大存活时间
     */
    private static long keepAliveTime = 60L;

    /**
     * 获取线程实例
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(){
        if(threadPoolExecutor == null){
            synchronized (ThreadPoolConfig.class){
                if(threadPoolExecutor == null){
                    int cpuNum = Runtime.getRuntime().availableProcessors();
                    corePoolSize  = cpuNum>0?(cpuNum*2-1):corePoolSize;
                    threadPoolExecutor = new ThreadPoolExecutor(
                            corePoolSize,
                            maximumPoolSize,
                            keepAliveTime,
                            TimeUnit.SECONDS,
                            new SynchronousQueue<Runnable>());
                }
            }
        }
        return threadPoolExecutor;
    }



}
