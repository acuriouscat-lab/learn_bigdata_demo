package org.flink.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FileSourceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        FileSource<String> fileSource = FileSource
                .forRecordStreamFormat(new TextLineInputFormat(), new Path("learn_flink_tutorial/input/word.txt"))
                .build();
        env.fromSource(fileSource, WatermarkStrategy.noWatermarks(), "fileSource")
                .flatMap((s, out) -> {
                    for (String word : s.split(" ")) {
                        out.collect(new Tuple2<String, Integer>(word, 1));
                    }
                }, Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(key -> key.getField(0))
                .sum(1)
                .print();
        env.execute();

    }
}
