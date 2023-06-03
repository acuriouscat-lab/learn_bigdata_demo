package org.flink.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> lineDS = env.readTextFile("learn_flink_tutorial/input/word.txt");
        FlatMapOperator<String, Tuple2<String, Integer>> wordOne = lineDS
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        for (String word : s.split(" ")) {
                            collector.collect(new Tuple2<>(word, 1));
                        }
                    }
                });
        wordOne.groupBy(0)
                                .sum(1)
                                        .print();

//        env.execute();
    }
}
