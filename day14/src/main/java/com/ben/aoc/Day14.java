package com.ben.aoc;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day14 {
	private static final String ORE = "ORE";
	private static final String FUEL = "FUEL";
	private static final long TRILLION = 1000000000000L;

	private static final String REG_EX_CHEMICAL= "(\\d+ [A-Z]+,?)";
	
	Map<String, Reaction> reactions = new HashMap<String, Reaction>();
	Map<String, Long> leftovers;
	long oreFor1Fuel;
	
	public Day14(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		Queue<String> queue = new ArrayDeque<String>();
		
		for(int i = 0; i<lines.size(); i++) {
			String line = lines.get(i);
			Map<String, Integer>inputs = new HashMap<String, Integer>();
			Matcher reactionMatcher = Pattern.compile(REG_EX_CHEMICAL).matcher(line);
			boolean usesOre = false;
			while(reactionMatcher.find()) {
				String chemical = reactionMatcher.group();
				int quantity = Integer.parseInt(chemical.substring(0, chemical.indexOf(" ")));
				String name = chemical.substring(chemical.indexOf(" ") + 1).replaceAll(",", "");
				
				if(!reactionMatcher.hitEnd()) {
					inputs.put(name, quantity);
					if(name.equals(ORE)) {
						usesOre = true;
					}
				}else {
					Reaction r = new Reaction(name, quantity, inputs);
					reactions.put(name, r);
					if(usesOre) {
						queue.add(name);
					}
				}
			}
		}
		Set<String> processed = new HashSet<String>();
		processed.add(ORE);

		while(!queue.isEmpty()) {
			int depth = 0;
			String name = queue.poll();
			processed.add(name);
			Reaction reaction = reactions.get(name);
			for(String input : reaction.inputs.keySet()) {
				int inputDepth = 0;
				if(!input.equals(ORE)) {
					Reaction r = reactions.get(input);
					inputDepth = r.depth;
				}
				depth = Math.max(inputDepth + 1, depth);
			}
			reaction.depth = depth;
			List<String> seen = reactions.values().stream().filter(r -> r.usesChemical(processed)).map(r -> r.name).toList();
			queue.addAll(seen);
			queue.removeAll(processed);
		}
	}

	public long puzzle1() {
		leftovers = new HashMap<String, Long>();
		oreFor1Fuel = getOreNeeded(1);
		
		
		
		return oreFor1Fuel;
	}


	public long puzzle2() {
		long result = 0;
		
		long minimum = TRILLION / oreFor1Fuel;
		long maximum = 2*minimum;
		
		boolean found = false;
		while (!found) {
			long testValue = (minimum + maximum)/2;
			long testValue2 = testValue+1;
			
			leftovers = new HashMap<String, Long>();
			long oreNeeded = getOreNeeded(testValue);
			if(oreNeeded == TRILLION) {
				return testValue;
			}else if(oreNeeded > TRILLION) {
				maximum = testValue;
			}else {
				leftovers = new HashMap<String, Long>();
				long oreNeeded2 = getOreNeeded(testValue2);
				if(oreNeeded2 > TRILLION) {
					return testValue;
				}else {
					minimum = testValue;
				}
			}
			
		}
		

		return result;
	}
	
	public long getOreNeeded(long requiredFuel) {
		Reaction fuel = reactions.get(FUEL);
		int layer = fuel.depth - 1;
		
		Map<String, Long> requirements = fuel.getRequirements(requiredFuel);
		
		while (layer > 0) {
			Map<String, Long> temp = new HashMap<String, Long>();
			Set<String> toRemove = new HashSet<String>();
			//Each product required
			for(Entry<String, Long> requirement : requirements.entrySet()) {
				String requirementName = requirement.getKey();
				Long neededResource = requirement.getValue();
				Reaction r = reactions.get(requirementName);
				//Go in depth order
				if(r.depth == layer) {
					//About to produce X, but already have some, so reduce by that many
					if(leftovers.containsKey(requirementName)) {
						long leftover = leftovers.get(requirementName);
						if(neededResource >= leftover) {
							neededResource -= leftover;
							leftovers.put(requirementName, 0L);
						}else {
							leftovers.put(requirementName, leftover - neededResource);
							neededResource = 0L;
						}
					}
					
					//What do I need to produce x+d products?
					Map<String, Long> newRequirements = r.getRequirements(neededResource);
					for(Entry<String, Long> newRequirement : newRequirements.entrySet()) {
						long quantity = newRequirement.getValue();
						if(temp.containsKey(newRequirement.getKey())) {
							temp.put(newRequirement.getKey(), temp.get(newRequirement.getKey()) + quantity);
						}else {
							temp.put(newRequirement.getKey(), quantity);
						}
					}
					//How many have I actually made?
					long productsMade = r.getMinimumToMake(neededResource)*r.quantityProduced;
					long extraMade = productsMade - neededResource;
					
					//Store leftovers
					if(leftovers.containsKey(requirementName)) {
						leftovers.put(requirementName, leftovers.get(requirementName) + extraMade);
					}else {
						leftovers.put(requirementName, extraMade);
					}
					
					toRemove.add(requirementName);
				}
			}
			for(Entry<String, Long> newRequirement : temp.entrySet()) {
				if(requirements.containsKey(newRequirement.getKey())) {
					requirements.put(newRequirement.getKey(), requirements.get(newRequirement.getKey()) + newRequirement.getValue());
				}else {
					requirements.put(newRequirement.getKey(), newRequirement.getValue());
				}
			}
			for(String s : toRemove) {
				requirements.remove(s);
			}
			layer --;
		}

		return requirements.get(ORE);
	}
	
}
