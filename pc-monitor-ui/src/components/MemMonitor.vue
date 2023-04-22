<template>
    <div id='mem'></div>
    <!-- <button @click="getMemUsage">getMemUsage</button> -->
</template>
  
<script>
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { GaugeChart } from 'echarts/charts';

export default {
    props: ['memUsage'],
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
        this.myChart = echarts.init(document.getElementById('mem'));
        let option = {
            series: [
                {
                    type: 'gauge',
                    center: ['50%', '60%'],
                    startAngle: 200,
                    endAngle: -20,
                    min: 0,
                    max: 32,
                    splitNumber: 8,
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
                        formatter: '{value} G',
                        color: 'inherit'
                    },
                    data: [
                        {
                            value: this.memUsage
                        }
                    ]
                },
                {
                    type: 'gauge',
                    center: ['50%', '60%'],
                    startAngle: 200,
                    endAngle: -20,
                    min: 0,
                    max: 32,
                    splitNumber: 8,
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
                            value: this.memUsage
                        }
                    ]
                }
            ]
        }
        this.myChart.setOption(option);
    },
    watch: {
        memUsage: {
            handler() {
                // console.log("watch"+this.memUsage)
                // 监听memUsage
                this.myChart.setOption({
                    series: [
                        {
                            data: [
                                {
                                    value: this.memUsage
                                }
                            ]
                        },
                        {
                            data: [
                                {
                                    value: this.memUsage
                                }
                            ]
                        }
                    ]
                });
            }
        }
    },
    methods: {
        async getMemUsage() {
            // console.log(this.MemUsage)
        }
    }
}
</script>
  
<style scoped>
#mem {
    height: 450px;
    width: 450px;
}
</style>