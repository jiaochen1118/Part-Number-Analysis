package com.supplyframe.mapreduce;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupComparator extends WritableComparator {

	protected GroupComparator() {
		super(KeyObject.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		KeyObject obj1 = (KeyObject) a;
		KeyObject obj2 = (KeyObject) b;

		return obj1.getIpText().toString().compareTo(obj2.getIpText().toString());
	}
}
