package com.example.pcmonitorapi.service;

import java.util.Map;

/**
 * @author ldd
 */
public interface MonitorService {

    /**
     * 获取cpu，内存使用率
     * @param waitingTime
     * @return
     */
    Map<String, String> monitor(Long waitingTime);
}
