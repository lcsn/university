package de.lsn.fh.msc.mining;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ID3 {

	public static void process(CSV csv, String targetAsString) {
		DefaultMutableTreeNode root = buildId3Tree(new DefaultMutableTreeNode(), targetAsString, csv.getHeaders(), csv.toArray(), "ROOT", new HashSet<String>());
		traverse(root);
	}
	
	@SuppressWarnings("unchecked")
	public static DefaultMutableTreeNode buildId3Tree(DefaultMutableTreeNode node, String targetAsString, String[] headers, Object[][] data, String path, HashSet<String> cache) {
		System.out.println(">>>"+path);
		TreeMap<String, Id3Attribute> attributeMap = toAttributeMap(headers, data);
		if (!attributeMap.isEmpty()) {
			Id3Attribute target = attributeMap.remove(targetAsString);
			if (target.getEntropy() > 0) {
				String attributeName = null;
				double _conditionalEntropy = 0.0d;
				double conditionalEntropy = 0.0d;
				Id3AttributeEntropyTask entropyTask = new Id3AttributeEntropyTask(target, attributeMap.values());
				HashMap<String, HashMap<String, Id3Attribute>> attributeEntropyMap = entropyTask.entropies();
				for (String attribute : attributeEntropyMap.keySet()) {
					if (cache.contains(attribute)) {
						continue;
					}
					HashMap<String, Id3Attribute> valueEntropyMap = attributeEntropyMap.get(attribute);
					for (String value : valueEntropyMap.keySet()) {
						Id3Attribute a = valueEntropyMap.get(value);
						if (!Double.isNaN(a.getEntropy())) {
							conditionalEntropy+=(new Fraction(a.getValues().size(), target.getValues().size()).toDouble()*a.getEntropy());
						}
					}
					System.out.println(attribute.toUpperCase()+" : "+conditionalEntropy);
					if (StringUtils.isEmpty(attributeName) || _conditionalEntropy > conditionalEntropy) {
						attributeName = attribute;
						_conditionalEntropy = conditionalEntropy;
					}
					conditionalEntropy = 0.0d;
				}
				Id3Attribute attribute1 = attributeMap.remove(attributeName);
				List<Object> nodeData = new ArrayList<Object>();
				nodeData.add(path);
				nodeData.add(attributeEntropyMap.get(attributeName));
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeData);
				node.add(child);
				path+="|"+attributeName;
				cache.add(attributeName);
				for (String value : attribute1.getValueSet()) {
					buildId3Tree(child, targetAsString, headers, removeData(data, attribute1.getIndicatedValueMap().get(value)), path+"::"+value, (HashSet<String>) cache.clone());
				}
			}
			else {
				List<Object> nodeData = new ArrayList<Object>();
				nodeData.add(path);
				nodeData.add(target.getValueSet());
				node.add(new DefaultMutableTreeNode(nodeData));
			}
		}
		return node;
	}
	
	private static Object[][] removeData(Object[][] data, TreeSet<Integer> treeSet) {
		Object[][] _data = new Object[treeSet.size()][];
		int j = 0;
		for (int i = 0; i < data.length; i++) {
			if (treeSet.contains(i)) {
				_data[j++] = data[i];
			}
		}
		return _data;
	}

	@SuppressWarnings("unchecked")
	public static void traverse(DefaultMutableTreeNode root) {
		Enumeration<DefaultMutableTreeNode> childEnumeration = root.preorderEnumeration();
		while (childEnumeration.hasMoreElements()) {
			DefaultMutableTreeNode node = childEnumeration.nextElement();
			System.out.println(node.getPath()[node.getPath().length-1]);
		}
	}

	public static TreeMap<String, Id3Attribute> toAttributeMap(String[] headers, Object[][] data) {
		TreeMap<String, Id3Attribute> attributeMap = new TreeMap<String, Id3Attribute>();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				Id3Attribute attribute;
				String key = headers[j];
				if (attributeMap.containsKey(key)) {
					attribute = attributeMap.get(key);
				}
				else {
					if (NumberUtils.isNumber((String) data[i][j])) {
						attribute = new Id3NumberAttribute(key);
					}
					else {
						attribute = new Id3StringAttribute(key);
					}
					attributeMap.put(key, attribute);
				}
				attribute.getValues().add((String) data[i][j]);
			}
		}
		return attributeMap;
	}
	
}
