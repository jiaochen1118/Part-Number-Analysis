package com.supplyframe.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class KeyObject implements WritableComparable<KeyObject> {
	
	private Text ipStart;
	
	public KeyObject() {
		setIpStart(new Text());
	}

	public KeyObject(Text ip) {
		ipStart = ip;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		ipStart.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		ipStart.write(out);
	}

	@Override
	public int compareTo(KeyObject o) {
		return ipStart.compareTo(o.ipStart);
	}

	public void setIpStart(Text ipText) {
		ipStart = ipText;
	}

	public Text getIpText() {
		return ipStart;
	}

}
