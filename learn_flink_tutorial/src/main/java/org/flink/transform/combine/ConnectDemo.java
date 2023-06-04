package org.flink.transform.combine;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

public class ConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> strDS = env.fromElements("a", "b", "c");
        DataStreamSource<Integer> numDS = env.fromElements(1, 2, 3);

        strDS.connect(numDS).map(new CoMapFunction<String, Integer, String>() {
            @Override
            public String map1(String value) throws Exception {
                return "来源于数字流:" + value;
            }

            @Override
            public String map2(Integer value) throws Exception {
                return "来源于字母流:" + value.toString();
            }

        }).print("connect");
        env.execute();
    }
}
