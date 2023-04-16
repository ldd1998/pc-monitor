package com.example.pcmonitor;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import oshi.hardware.GlobalMemory;
import oshi.util.GlobalConfig;

@SpringBootTest
class PcMonitorApplicationTests {

    @Test
    void contextLoads() {
        GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        GlobalMemory memory = OshiUtil.getMemory();
        System.out.println(cpuInfo);
    }

}
