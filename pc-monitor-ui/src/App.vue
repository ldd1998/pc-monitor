<template>
  <div id="CpuMonitor">
    <CpuMonitor :cpu-usage="cpuUsage"></CpuMonitor>
  </div>
  <div id="MemMonitor">
    <MemMonitor :mem-usage="memUsage"></MemMonitor>
  </div>
  <div>

  </div>
  <!-- <button @click="update">update</button> -->
</template>

<script>

import CpuMonitor from './components/CpuMonitor.vue'
import MemMonitor from './components/MemMonitor.vue'
// const axios = require('axios');
export default {
  name: 'App',
  components: {
    CpuMonitor, MemMonitor
  },
  data: () => ({
    cpuUsage: String,
    memUsage: String
  }),
  async created() {
    this.cpuUsage = 0;
    this.memUsage = 0;
    this.openSocket();
    setInterval(this.reConnectWebSocket,1000)
  },
  methods: {
    // 重试webSocket
    reConnectWebSocket(){
      if(this.webSocket != null){
        if(this.webSocket.readyState != 1){
          console.log("ws连接重试，当前状态"+this.webSocket.readyState)
          this.webSocketClose()
          this.openSocket()
        }
      }else {
        this.openSocket()
      }
    },
    openSocket() {
      if (typeof (WebSocket) === "undefined") {
        this.$toast.fail("您的浏览器不支持WebSocket")
      } else {
        let SocketUrl = `ws://192.168.1.100:8081/monitor/ldd`
        // 实例化WebSocket
        this.webSocket = new WebSocket(SocketUrl)
        // 监听socket连接
        this.webSocket.onopen = this.webSocketOnOpen
        // 监听socket错误信息
        this.webSocket.onerror = this.webSocketOnError
        // 监听socket消息
        this.webSocket.onmessage = this.webSocketOnMessage
        this.webSocket.onclose = this.webSocketClose
        }
      },
      //关闭日志
      checkSocket() {
        if (this.webSocket != null) {
          this.webSocketClose();
          this.res = "";
          this.webSocket = null;
        } else {
          this.openSocket();
        }
      },
      // 开启日志
      startSocket() {
        this.openSocket();
      },
      // 连接建立之后执行send方法发送数据
      webSocketOnOpen() {
      },
      // 连接建立失败重连
      webSocketOnError() {
        this.res = "<div style='color: #18d035;font-size: 14px'>通道连接失败,尝试重新连接,静默等待....</div>";
        console.log("连接发生错误")
        // setInterval(this.reConnectWebSocket(),1000);
      },
      // 数据接收
      webSocketOnMessage: function (event) {
        let sysInfo = JSON.parse(event.data);
        this.cpuUsage = sysInfo.cpuUsed
        this.memUsage = sysInfo.memUsed
      },
      // 数据发送
      webSocketSend(Data) {
        this.webSocket.send(Data);
      },
      //关闭
      webSocketClose() {
        console.log("webSocket关闭")
        this.webSocket.close();
      },
    },
}
</script>

<style>
#body {
  background-color: black;
  margin: 50px;
}
#CpuMonitor{
  float:left;
  margin-right: 100px;
  background-color: black;
}
#MemMonitor{
  float:left;
  background-color: black;
}

</style>
