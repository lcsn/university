package de.lsn.fh.msc.mining;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Id3AttributeEntropyTask implements Id3Task<String, HashMap<String, Id3Attribute>> {

	private Id3Attribute target;
	private Collection<Id3Attribute> attributes;

	public Id3AttributeEntropyTask(Id3Attribute target, Collection<Id3Attribute> attributes) {
		this.target = target;
		this.attributes = attributes;
	}

	@Override
	public HashMap<String, HashMap<String, Id3Attribute>> entropies() {
		HashMap<String, HashMap<String, Id3Attribute>> attributeEntropyMap = (HashMap<String, HashMap<String, Id3Attribute>>) attributes.stream().collect(Collectors.toMap(Id3Attribute::getName, a -> target.divideBy(a)));
		return attributeEntropyMap;
	}
	
}
