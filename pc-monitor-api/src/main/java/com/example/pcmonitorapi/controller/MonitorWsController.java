package com.example.pcmonitorapi.controller;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.example.pcmonitorapi.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.*;

/**
 * websocket操作类
 *
 * @author ldd
 */
@Component
@ServerEndpoint(value = "/monitor/{userId}")
@Slf4j
public class MonitorWsController {
    //  这里使用静态，让 service 属于类
    private static MonitorService monitorService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setMonitorService(MonitorService monitorService) {
        MonitorWsController.monitorService = monitorService;
    }
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用来存放每个客户端对应的MyWebSocket对象
     */
    private static CopyOnWriteArraySet<MonitorWsController> webSockets = new CopyOnWriteArraySet<>();
    /**
     * 用来存在线连接用户信息
     */
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();
    @PostConstruct
    public void monitorStart(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("monitorWs");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (sessionPool.size() != 0) {
                        Map<String, String> monitor = monitorService.monitor(700L);
                        log.info("线程名称：" + Thread.currentThread().getName() + "广播消息：" + JSONUtil.toJsonStr(monitor));
                        sendAllMessage(JSONUtil.toJsonStr(monitor));
                    }
                }
            }
        };
        Thread[] threads = ThreadUtil.getThreads();
        boolean existMonitor = false;
        for (int i = 0; i < threads.length; i++) {
            if(threads[i].getName().equals("monitorWs")){
                existMonitor = true;
            }
        }
        if(existMonitor != true){
            ThreadUtil.execute(runnable);
        }
    }
    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 此为广播消息
     */
    public void sendAllMessage(String message) {
//        log.info("【websocket消息】广播消息:" + message);
        for (MonitorWsController webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此为单点消息
     */
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 此为单点消息(多人)
     */
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
