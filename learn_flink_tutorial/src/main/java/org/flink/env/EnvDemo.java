package org.flink.env;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class EnvDemo {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set(RestOptions.BIND_PORT, "8082");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);
        // 流批一体：代码api是同一套，可以指定为 批，也可以指定为 流
        // 默认 STREAMING
        // 一般不在代码写死，提交时 参数指定：-Dexecution.runtime-mode=BATCH
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        DataStreamSource<String> lineDS = env.readTextFile("learn_flink_tutorial/input/word.txt");

        lineDS.flatMap((String s, Collector<Tuple2<String,Integer>> collector) -> {
                    for (String word : s.split(" ")) {
                        collector.collect(new Tuple2<String, Integer>(word, 1));
                    }
                })
                .returns(Types.TUPLE(Types.STRING,Types.INT))
                .keyBy(key -> key.f0)
                .sum(1)
                .print();

        env.execute();

    }
}
