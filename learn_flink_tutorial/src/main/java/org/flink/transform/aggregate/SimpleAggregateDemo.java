package org.flink.transform.aggregate;

import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.flink.Bean.WaterSensor;

public class SimpleAggregateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KeyedStream<WaterSensor, String> keyedWaterSensorDS = env.fromElements(
                        new WaterSensor("s1", 1L, 1),
                        new WaterSensor("s1", 11L, 11),
                        new WaterSensor("s1", 21L, 21),
                        new WaterSensor("s2", 2L, 2),
                        new WaterSensor("s3", 3L, 3)
                )
                .keyBy(WaterSensor::getId);
        /**
         *   max\maxBy： 同min
         *       max：只会取比较字段的最大值，非比较字段保留第一次的值
         *       maxBy：取比较字段的最大值，同时非比较字段 取 最大值这条数据的值
         */
        keyedWaterSensorDS.max("vc").print("max");
        keyedWaterSensorDS.maxBy("vc").print("maxBy");

        env.execute();
    }
}
