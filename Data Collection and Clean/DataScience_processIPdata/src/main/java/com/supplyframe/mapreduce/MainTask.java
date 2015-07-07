package com.supplyframe.mapreduce;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MainTask extends Configured implements Tool {
	
	/*
	public static final String JSON_FILE = "/home/ykao/botProfile/crawler-user-agents.json";
	public static final String IP_FILE = "/home/ykao/botProfile/ip_range.txt";
	public static SupplyFrameFilterUtil FILTER_OBJ;
	*/

	@Override
	public int run(String[] args) throws Exception {

		Job job = new Job(getConf(), "OpenX Data Processing");

		job.setJarByClass(MainTask.class);

		job.setMapOutputKeyClass(KeyObject.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		//job.setInputFormatClass(MyInputFormat.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(Ip2LocationMapper.class);
		// job.setPartitionerClass(HashPartitioner.class);
		job.setGroupingComparatorClass(GroupComparator.class);
		job.setReducerClass(Ip2LocationReducer.class);

		job.setNumReduceTasks(1);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		
		// get Bot profile
		/*
		SupplyFrameFilterUtil filterObj = new SupplyFrameFilterUtil();
		filterObj.buildPatternData(JSON_FILE);
		filterObj.buildIpRange(IP_FILE);
		*/
		
		Configuration conf = new Configuration();
		conf.set("mapred.max.split.size", "134217728");
		conf.set("mapreduce.input.fileinputformat.split.maxsize", "134217728");
		conf.set("mapred.job.reuse.jvm.num.tasks", "1");
		conf.set("mapreduce.job.jvm.numtasks", "1");
		
		// serilaize filterObj for Mappers
		/*
		String serializedObj = "";
		try {
		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
		     ObjectOutputStream so = new ObjectOutputStream(bo);
		     so.writeObject(filterObj);
		     so.flush();
		     serializedObj = bo.toString();
		 } catch (Exception e) {
		     System.out.println(e);
		 }
		conf.set("serializedObj", serializedObj);
		*/
		
		int exitCode = ToolRunner.run(conf, new MainTask(), args);
		System.exit(exitCode);
	}
}
