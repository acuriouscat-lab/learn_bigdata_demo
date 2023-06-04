package org.flink.window;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.flink.Bean.WaterSensor;
import org.flink.functions.WaterSensorMapFunction;

public class WindowApiDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        sensorDS.keyBy(WaterSensor::getId);
//                .countWindow(5, 2)
//                .window(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(2)))
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(1)))
//                .aggregate()

        env.execute();
    }


}
