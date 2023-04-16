<template>
  <div id="CpuMonitor">
    <CpuMonitor :cpu-usage="cpuUsage"></CpuMonitor>
  </div>
  <div id="MemMonitor">
    <MemMonitor :mem-usage="memUsage"></MemMonitor>
  </div>
  <!-- <button @click="update">update</button> -->
</template>

<script>

import CpuMonitor from './components/CpuMonitor.vue'
import MemMonitor from './components/MemMonitor.vue'
const axios = require('axios');
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

    var sysInfo = 0;
    
    for (let i = 0; i < 1000000000; i++) {
      sysInfo = await axios.get("http://192.168.1.100:8081/cpuMonitor")
      this.cpuUsage = sysInfo.data.cpuUsed
      this.memUsage = sysInfo.data.memUsed
    }
  }
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
