package org.flink.wc;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SocketStreamWordCount {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.socketTextStream("node06",8888)
                .flatMap((s,out) -> {
                    for (String word : s.split(" ")) {
                        out.collect(new Tuple2<String,Integer>(word,1));
                    }
                }, Types.TUPLE(Types.STRING,Types.INT))
                .keyBy(key -> key.getField(0))
                .sum(1)
                .print();

        env.execute();
    }
}
