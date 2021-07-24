package top.bluewort.Notes.base_utils.X015_system_monitor;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

@Component
public class RuntimeMonitor {


    /**
     * 虚拟机 垃圾回收
     */

    public void tes01(){
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gcm:garbageCollectorMXBeans){
            System.out.println("name::"+gcm.getName()+",CollectionCount::"+gcm.getCollectionCount()+",CollectionTime::"+gcm.getCollectionTime());
            System.out.println("MemoryPoolNames::"+ JSON.toJSONString(gcm.getMemoryPoolNames()));
        }
    }
    /**
     * 虚拟机 vm内存
     */

    public void tes02(){
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println("HeapMemoryUsage::"+memoryMXBean.getHeapMemoryUsage());
        System.out.println("NonHeapMemoryUsage::"+memoryMXBean.getNonHeapMemoryUsage());
        System.out.println("ObjectPendingFinalizationCount::"+memoryMXBean.getObjectPendingFinalizationCount());
        System.out.println("isVerbose::"+memoryMXBean.isVerbose());
    }
    /**
     * 系统
     */

    public void tes03(){
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Arch::"+operatingSystemMXBean.getArch());
        System.out.println("Name::"+operatingSystemMXBean.getName());
        System.out.println("Version::"+operatingSystemMXBean.getVersion());
        System.out.println("AvailableProcessors::"+operatingSystemMXBean.getAvailableProcessors());
        System.out.println("SystemLoadAverage::"+operatingSystemMXBean.getSystemLoadAverage());
    }
}
