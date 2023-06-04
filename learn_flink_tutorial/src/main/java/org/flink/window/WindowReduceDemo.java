package org.flink.window;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.flink.Bean.WaterSensor;
import org.flink.functions.WaterSensorMapFunction;

import java.time.Duration;

public class WindowReduceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("node06", 8888)
                .map(new WaterSensorMapFunction());
//                .assignTimestampsAndWatermarks(
//                        WatermarkStrategy.<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(2))
//                                .withTimestampAssigner((ws, ts) -> ws.getTs() * 1000L)
//                );

        sensorDS.keyBy(WaterSensor::getId)
//                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .reduce(new ReduceFunction<WaterSensor>() {
                    @Override
                    public WaterSensor reduce(WaterSensor value1, WaterSensor value2) throws Exception {
                        System.out.println("value1 : " + value1 + " value2 : " + value2);
                        return new WaterSensor(value1.id, value2.ts, value1.vc + value2.vc);
                    }
                })
                .print("reduce");

        env.execute();
    }
}
