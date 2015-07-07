package com.supplyframe.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


// pull out the ip ranges and latitude, longitude from data.tsv
public class Ip2LocationMapper extends
Mapper<LongWritable, Text, KeyObject, Text> {
	private Text ipText = new Text();

	private KeyObject keyObject = new KeyObject(ipText);

	private Text outputText = new Text();
	
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
        
        FieldObj fieldObj = new FieldObj ();
        fieldObj.setFields(line);
        
        //if (!fieldObj.country.equals("usa")) return;
        //if (!(fieldObj.city.equals("seattle") || fieldObj.city.equals("los angeles"))) return;
		
		
		ipText.set(fieldObj.ipStart);
		try {
			keyObject.setIpStart(ipText);
		} catch (Exception e) {
			
		}
		
		String tempOutput = StringUtils.join(
				new String[] { fieldObj.ipStart,
						fieldObj.url},'\t');
						/*fieldObj.ipEndLong,
						fieldObj.country,
						fieldObj.city}, '\t');*/
		
		outputText.set(tempOutput);

		context.write(keyObject, outputText);
	}
}
