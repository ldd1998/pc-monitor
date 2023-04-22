package com.example.pcmonitorapi.service.serviceImpl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.example.pcmonitorapi.service.MonitorService;
import org.springframework.stereotype.Service;
import oshi.hardware.GlobalMemory;
import oshi.util.GlobalConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ldd
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    public Map<String, String> monitor(Long waitingTime){
        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
        CpuInfo cpuInfo = OshiUtil.getCpuInfo(waitingTime);
        GlobalMemory memory = OshiUtil.getMemory();

        Map<String ,String > map = new HashMap<>();
        map.put("cpuUsed",(int)cpuInfo.getUsed()+"");
        map.put("memUsed",(NumberUtil.roundStr((( memory.getTotal()-memory.getAvailable())/1024F/1024F/1024F),1)));
        return map;
    }
}
