package org.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.file.sink.compactor.SimpleStringDecoder;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.nio.charset.Charset;

public class KafkaSourceDemo {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set(RestOptions.BIND_PORT, "8082");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);


        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setBootstrapServers("node06:9092")
                .setTopics("kafka-test")
                .setGroupId("test")
                /**
                 *   kafka消费者的参数：
                 *      auto.reset.offsets
                 *          earliest: 如果有offset，从offset继续消费; 如果没有offset，从 最早 消费
                 *          latest  : 如果有offset，从offset继续消费; 如果没有offset，从 最新 消费
                 *
                 *   flink的kafkasource，offset消费策略：OffsetsInitializer，默认是 earliest
                 *          earliest: 一定从 最早 消费
                 *          latest  : 一定从 最新 消费
                 */
                .setStartingOffsets(OffsetsInitializer.latest())
                .build();

        DataStreamSource<String> resDS = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafka-source");
        System.out.println(resDS.getParallelism());
        resDS
                .print();
        env.execute();
    }
}
