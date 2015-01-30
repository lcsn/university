package de.lsn.fh.msc.mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.border.EtchedBorder;

public abstract class Id3Attribute implements Cloneable {

	protected String name;
	protected List<String> values = new ArrayList<String>();
	private HashMap<String, TreeSet<Integer>> indicatedValueMap;
	
	public void indicate() {
		indicatedValueMap = new HashMap<String, TreeSet<Integer>>();
		for (int i = 0; i < values.size(); i++) {
			String key = values.get(i);
			TreeSet<Integer> indexSet;
			if (indicatedValueMap.containsKey(key)) {
				indexSet = indicatedValueMap.get(key);
			}
			else {
				indexSet = new TreeSet<Integer>();
				indicatedValueMap.put(key, indexSet);
			}
			indexSet.add(i);
		}
	}
	
	public Id3Attribute divideBy(String value) {
		return divideBy(getIndicatedValueMap().get(value));
	}

	public abstract TreeSet<String> getValueSet();
	
	public HashMap<String, Id3Attribute> divideBy(Id3Attribute a) {
		a.indicate();
		HashMap<String, Id3Attribute> valueEntropyMap = (HashMap<String, Id3Attribute>) a.getValueSet().stream().collect(Collectors.toMap(Function.identity(), v -> this.divideBy(a.getIndicatedValueMap().get(v))));
		return valueEntropyMap;
	}

	public Id3Attribute divideBy(TreeSet<Integer> treeSet) {
		Id3Attribute _this = null;
		try {
			_this = (Id3Attribute) this.clone();
			List<String> _values = new ArrayList<String>();
			for (Integer index : treeSet) {
				_values.add(values.get(index));
			}
			_this.setValues(_values);
			_this.indicate();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return _this;
	}

	public double getEntropy() {
		return InformationHelper.entropy(this);
	}
	
	public Map<String, Long> getPropertyRateMap() {
		return getValues().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public HashMap<String, TreeSet<Integer>> getIndicatedValueMap() {
		return indicatedValueMap;
	}

	@Override
	public String toString() {
		return name.toUpperCase()+":[Werte:["+collectValueSet()+"], Index:["+collectIndicatedValueMap()+"]]";
	}

	private String collectIndicatedValueMap() {
		List<String> l = new ArrayList<String>();
		for (String key : getIndicatedValueMap().keySet()) {
			TreeSet<Integer> treeSet = getIndicatedValueMap().get(key);
			l.add(key.toUpperCase()+":{"+treeSet.stream().map(String::valueOf).reduce((u, v) -> u+", "+v).orElse("N.A.")+"}");
		}
//		String indicesAsString = l.stream().reduce("",  String::concat, (u, v) -> u+", "+v);
		String indicesAsString = l.stream().collect(Collectors.joining(", "));
		return indicesAsString;
//		return l.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

	private String collectValueSet() {
		return getValueSet().stream().reduce((u, v) -> u+", "+v).orElse("N.A.");
	}

}
