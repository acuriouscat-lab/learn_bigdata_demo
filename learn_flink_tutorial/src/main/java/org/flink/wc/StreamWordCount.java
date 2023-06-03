package org.flink.wc;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamWordCount {
    public static void main(String[] args) throws Exception {



        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

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
