<template>
    <div id='main'></div>
    <!-- <button @click="getCpuUsage">getCpuUsage</button> -->
</template>
  
<script>
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { GaugeChart } from 'echarts/charts';

export default {
    props: ['cpuUsage'],
    data: () => ({
        myChart: null,
    }),
    created() {
        // 注册必须的组件
        echarts.use([
            GaugeChart,
            CanvasRenderer
        ]);
    },

    mounted() {
        // 接下来的使用就跟之前一样，初始化图表，设置配置项
        this.myChart = echarts.init(document.getElementById('main'));
        let option = {
            series: [
                {
                    type: 'gauge',
                    center: ['50%', '60%'],
                    startAngle: 200,
                    endAngle: -20,
                    min: 0,
                    max: 100,
                    splitNumber: 10,
                    itemStyle: {
                        color: '#FFAB91'
                    },
                    progress: {
                        show: true,
                        width: 30
                    },
                    pointer: {
                        show: false
                    },
                    axisLine: {
                        lineStyle: {
                            width: 30
                        }
                    },
                    axisTick: {
                        distance: -45,
                        splitNumber: 5,
                        lineStyle: {
                            width: 2,
                            color: '#999'
                        }
                    },
                    splitLine: {
                        distance: -52,
                        length: 14,
                        lineStyle: {
                            width: 3,
                            color: '#999'
                        }
                    },
                    axisLabel: {
                        distance: -20,
                        color: '#999',
                        fontSize: 20
                    },
                    anchor: {
                        show: false
                    },
                    title: {
                        show: false
                    },
                    detail: {
                        valueAnimation: true,
                        width: '60%',
                        lineHeight: 40,
                        borderRadius: 8,
                        offsetCenter: [0, '-15%'],
                        fontSize: 60,
                        fontWeight: 'bolder',
                        formatter: '{value} %',
                        color: 'inherit'
                    },
                    data: [
                        {
                            value: this.cpuUsage
                        }
                    ]
                },
                {
                    type: 'gauge',
                    center: ['50%', '60%'],
                    startAngle: 200,
                    endAngle: -20,
                    min: 0,
                    max: 100,
                    itemStyle: {
                        color: '#FD7347'
                    },
                    progress: {
                        show: true,
                        width: 8
                    },
                    pointer: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    detail: {
                        show: false
                    },
                    data: [
                        {
                            value: this.cpuUsage
                        }
                    ]
                }
            ]
        }
        this.myChart.setOption(option);
    },
    watch: {
        cpuUsage: {
            handler() {
                console.log("watch"+this.cpuUsage)
                // 监听cpuUsage
                this.myChart.setOption({
                    series: [
                        {
                            data: [
                                {
                                    value: this.cpuUsage
                                }
                            ]
                        },
                        {
                            data: [
                                {
                                    value: this.cpuUsage
                                }
                            ]
                        }
                    ]
                });
            }
        }
    },
    methods: {
        async getCpuUsage() {
            console.log(this.cpuUsage)
        }
    }
}
</script>
  
<style scoped>
#main {
    height: 450px;
    width: 450px;
}
</style>