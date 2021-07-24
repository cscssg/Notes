package top.bluewort.Notes.base_utils.X014_self_threadpool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadPoolTaskService {

    public ExecutorService execute(Runnable runnable){
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolConfig.getThreadPoolExecutor();
        threadPoolExecutor.execute(runnable);
        return threadPoolExecutor;
    }
}
