package org.flink.transform.split;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.flink.Bean.WaterSensor;
import org.flink.functions.WaterSensorMapFunction;

public class SideOutputDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("node06", 8888)
                .map(new WaterSensorMapFunction());

        OutputTag<WaterSensor> s1Tag = new OutputTag<>("s1", Types.POJO(WaterSensor.class));
        OutputTag<WaterSensor> s2Tag = new OutputTag<>("s2", Types.POJO(WaterSensor.class));

        SingleOutputStreamOperator<WaterSensor> mainStream = sensorDS.process(new ProcessFunction<WaterSensor, WaterSensor>() {
            @Override
            public void processElement(WaterSensor value, ProcessFunction<WaterSensor, WaterSensor>.Context ctx, Collector<WaterSensor> out) throws Exception {
                String id = value.getId();
                if (id.equals("s1")) {
                    ctx.output(s1Tag, value);
                } else if (id.equals("s2")) {
                    ctx.output(s2Tag, value);
                } else {
                    out.collect(value);
                }
            }
        });

        mainStream.getSideOutput(s1Tag).print("s1Stream: ");
        mainStream.getSideOutput(s2Tag).print("s2Stream: ");

        mainStream.print("main stream: ");

        env.execute();

    }
}
