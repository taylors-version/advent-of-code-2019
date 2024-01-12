package com.ben.aoc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class Reaction {
	
	String name;
	int quantityProduced;
	Map<String, Integer> inputs;
	Integer depth;
	
	public Reaction(String name, int quantityProduced) {
		this(name, quantityProduced, new HashMap<String, Integer>());		
	}
	
	public Reaction(String name, int quantityProduced, Map<String, Integer> inputs) {
		this.name = name;
		this.quantityProduced = quantityProduced;
		this.inputs = inputs;
	}
	
	public void addInput(String inputName, Integer quantity) {
		inputs.put(inputName, quantity);
	}
	
	public boolean usesChemical(Set<String> chemicals) {

		
		return chemicals.containsAll(inputs.keySet());
	}
	
	public Map<String, Long> getRequirements(long requiredOutput){
		Map<String, Long> requirements = new HashMap<String, Long>();
		long multiple = getMinimumToMake(requiredOutput);
		
		for(Entry<String, Integer> entry : inputs.entrySet()) {
			requirements.put(entry.getKey(), entry.getValue() * multiple);
		}
		return requirements;
	}
	
	public long getMinimumToMake(long requiredOutput) {
		return (long) Math.ceil((double)requiredOutput / quantityProduced);
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Reaction)) {
			return false;
		}
		Reaction r = (Reaction) o;
		return this.name.equals(r.name);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Entry<String, Integer> input : inputs.entrySet()) {
			result.append(input.getValue()).append(" ").append(input.getKey()).append(", " );
		}
		result.deleteCharAt(result.length()-2);
		result.append("=> ").append(quantityProduced).append(" ").append(name);
		return result.toString();
	}
}
