package org.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataGeneratorSourceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataGeneratorSource<String> dataGeneratorSource = new DataGeneratorSource<>(new GeneratorFunction<Long,String>() {
            @Override
            public String map(Long value) throws Exception {
                return "value: " + value ;
            }
        }
        , 1000L
                , RateLimiterStrategy.perSecond(10)
                , Types.STRING);
        env.fromSource(dataGeneratorSource, WatermarkStrategy.noWatermarks(),"genSource")
                .print();

        env.execute();
    }
}
