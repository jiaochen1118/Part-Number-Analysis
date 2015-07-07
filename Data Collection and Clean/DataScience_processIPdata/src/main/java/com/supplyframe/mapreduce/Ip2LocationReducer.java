package com.supplyframe.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Ip2LocationReducer extends
		Reducer<KeyObject, Text, NullWritable, Text> {

	private Text text = new Text();

	@Override
	protected void reduce(KeyObject key, Iterable<Text> value, Context context)
			throws IOException, InterruptedException {

		context.write(NullWritable.get(), text);
		for (Text valueObject : value) {
			text.set(valueObject.toString());
			context.write(NullWritable.get(), text);
		}
		context.write(NullWritable.get(), text);
		context.getCounter(LogCounter.REDUCE_TOTAL_SESSION_WRITE).increment(1);
	}
}
