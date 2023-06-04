package org.flink.transform.combine;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class UnionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Integer> lowNumStream = env.fromElements(1, 2, 3);
        DataStreamSource<Integer> upNumStream = env.fromElements(4, 5, 6);
        DataStreamSource<String> stringStream = env.fromElements("4", "15", "12");
        /**
         * union 的数据类型必须相同
         */
        lowNumStream.union(upNumStream).union(stringStream.map(Integer::valueOf)).print();

        env.execute();
    }
}
