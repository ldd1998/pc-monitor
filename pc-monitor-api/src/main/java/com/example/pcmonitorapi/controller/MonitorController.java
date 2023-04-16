package com.example.pcmonitorapi.controller;

import com.example.pcmonitorapi.controller.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ldd
 */
@RestController
@CrossOrigin(origins = "*")
public class MonitorController {
    @Autowired
    MonitorService monitorService;
    @GetMapping("/cpuMonitor")
    public Map<String, String> cpuMonitor(){
        return monitorService.monitor();
    }
}
