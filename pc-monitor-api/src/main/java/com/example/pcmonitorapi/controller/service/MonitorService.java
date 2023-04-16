package com.example.pcmonitorapi.controller.service;

import java.util.Map;

/**
 * @author ldd
 */
public interface MonitorService {
    /**
     * 获取cpu使用率
     *
     * @return cpu使用率
     */
    Map<String, String> monitor();
}
