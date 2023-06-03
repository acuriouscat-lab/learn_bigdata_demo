package org.flink.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CollectionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        env.fromCollection()
        env.fromElements(101, 102, 102, 201, 203)
                .print();
        env.execute();
    }
}
