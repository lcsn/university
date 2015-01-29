package de.lsn.fh.msc.mining;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Id3NumberAttribute extends Id3Attribute {

	public Id3NumberAttribute(String name) {
		this.name = name;
	}
	
	@Override
	public TreeSet<String> getValueSet() {
		TreeSet<String> newSet = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
			}
		});
		newSet.addAll(values.stream().distinct().collect(Collectors.toSet()));
		return newSet;
	}

}