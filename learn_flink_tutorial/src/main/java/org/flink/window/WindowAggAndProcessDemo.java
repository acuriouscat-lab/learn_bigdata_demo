package org.flink.window;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.flink.Bean.WaterSensor;
import org.flink.functions.WaterSensorMapFunction;

public class WindowAggAndProcessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("node06", 8888)
                .map(new WaterSensorMapFunction());

        env.setParallelism(1);

        sensorDS.keyBy(WaterSensor::getId)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .aggregate(getAggFunction(), getWindowFunction())
                .print("result");

        env.execute();
    }

    private static ProcessWindowFunction<String, String, String, TimeWindow> getWindowFunction() {
        return new ProcessWindowFunction<String, String, String, TimeWindow>() {
            @Override
            public void process(String s, ProcessWindowFunction<String, String, String, TimeWindow>.Context context, Iterable<String> elements, Collector<String> out) throws Exception {
                long endTs = context.window().getEnd();
                long startTs = context.window().getStart();
                String windowStart = DateFormatUtils.format(startTs, "yyyy-MM-dd HH:mm:ss.SSS");
                String windowEnd = DateFormatUtils.format(endTs, "yyyy-MM-dd HH:mm:ss.SSS");

                long count = elements.spliterator().estimateSize();


                out.collect("key=" + s + "的窗口[" + windowStart + "," + windowEnd + ")包含" + count + "条数据===>" + elements.toString());

            }
        };
    }

    private static AggregateFunction<WaterSensor, Integer, String> getAggFunction() {
        return new AggregateFunction<WaterSensor, Integer, String>() {
            @Override
            public Integer createAccumulator() {
                System.out.println("init..");
                return 0;
            }

            @Override
            public Integer add(WaterSensor value, Integer accumulator) {
                System.out.println("add..");
                System.out.println(value);

                return value.vc + accumulator;
            }

            @Override
            public String getResult(Integer accumulator) {
                System.out.println("getResult..");
                return accumulator.toString();
            }

            @Override
            public Integer merge(Integer a, Integer b) {
                System.out.println("merge..");
                return a + b;
            }
        };
    }
}
