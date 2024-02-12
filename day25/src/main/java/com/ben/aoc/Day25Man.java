package com.ben.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import org.javatuples.Pair;

public class Day25Man {
	
	
	Computer ascii;
	String[] instructions;
	List<Long> output;
	List<Long> inputs;
	
	static final Long FIVE_SECONDS = 5000l;
	
	//Responses
    static final String COMMAND = "Command?";
	static final String ROOM_PREFIX = "== ";
	static final String ROOM_SUFFIX = " ==";
	static final String DOORS_LIST = "Doors here lead:";
	static final String ITEMS_LIST = "Items here:";
	static final String LIST_PREFIX = "- ";
	
	//Commands
	static final String NORTH = "north";
	static final String EAST = "east";
	static final String SOUTH = "south";
	static final String WEST = "west";
	static final String TAKE = "take ";
	static final String DROP = "drop ";
	static final String INV = "inv ";
	
	//DANGER ITEMS
	Set<String> dangerItems = new HashSet<String>();
	
	Map<String, Room> rooms = new HashMap<String, Room>();
	Set<String> items = new HashSet<String>();
	Set<String> availableItems = new HashSet<String>();
	Room previousRoom = null;
	Room currentRoom = null;
	String directionTravelled = null;
	
	int outputPosition = 0;
	String location = "";
	boolean poisenedItem = false;
	String lastItem = "";
	
	public Day25Man(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
		dangerItems.add("infinite loop");
		dangerItems.add("molten lava");
	}

	public long puzzle1() {
		String[] ins = Arrays.copyOf(instructions, instructions.length);
		ascii = new Computer(ins);
		
		inputs = new ArrayList<Long>();
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			ascii.intCode(inputs);
			List<Long> output = ascii.getOutputs();
			parseOutput(output);
			
			String line = scanner.nextLine();
			if(line.equals("exit")) {
				break;
			}
			line = line + (char)10;
			for(char c : line.toCharArray()) {
				int i = c;
				inputs.add((long) i);
			}
			
		}
		
		for(int j = 0; j<30; j++) {
			Thread t = new Thread(ascii);
			ascii.setInputs(inputs);
			t.start();
			long threadStartTime = System.currentTimeMillis();
			boolean finished = false;
			while(!finished) {
				long currentTime = System.currentTimeMillis();
				if(!t.isAlive()) {
					List<Long> output = ascii.getOutputs();
					parseOutput(output);
					List<Long> command = getCommand();
					inputs.addAll(command);
					finished = true;
				}else if(currentTime - threadStartTime > FIVE_SECONDS) {
					System.out.println("Interupting");
					t.interrupt();
					reset();
					finished = true;
				}
			}
		}
		
		return 0;
	}
	
	private List<Long> getCommand() {
		List<Long> commandList = new ArrayList<Long>();
		String command = "";
		
		if(!availableItems.isEmpty()) {
			for(String item : availableItems) {
				command = TAKE + item;
				items.add(item);
				lastItem = item;
				availableItems.remove(item);
				break;
			}
		}else {
			command = nextMovement();
			directionTravelled = command;
			previousRoom = currentRoom;
		}
		command = command + (char)10;
		System.out.print(command);
		for(char c : command.toCharArray()) {
			int i = c;
			commandList.add((long) i);
		}
		
		return commandList;
	}

	private void parseOutput(List<Long> output) {
		List<String> lines = new ArrayList<String>();
		
		String line = "";
		for(int i = outputPosition; i<output.size(); i++) {
			char c = (char) output.get(i).intValue();
			if(c != (char)10) {
				line = line + c;
			}else {
				lines.add(line);
				line = "";
			}
		}
		outputPosition = output.size()-1;
		
		Room room = null;
		
		for(int i = 0; i < lines.size(); i++) {
			String l = lines.get(i);
			if(l.startsWith(ROOM_PREFIX)) {
				availableItems = new HashSet<String>();
				String name = "";
				String description = "";
				name = l.substring(ROOM_PREFIX.length(), l.length() - ROOM_SUFFIX.length());
				
				i++;
				l = lines.get(i);
				while(!l.isBlank()) {
					description = description + l;
					i++;
					l = lines.get(i);
				}
				room = rooms.getOrDefault(name, new Room(name, description));
				if(directionTravelled != null) {
					room.addNeighbour(previousRoom,Compass.getOpposite(directionTravelled));
					previousRoom.addNeighbour(room, directionTravelled);
				}
				rooms.put(name, room);
				currentRoom = room;
				location = name;
				
			}else if(l.equals(DOORS_LIST)) {
				i++;
				l = lines.get(i);
				while(l.startsWith(LIST_PREFIX)) {
					String direction = l.substring(LIST_PREFIX.length());
					room.addDirection(direction);
					i++;
					l = lines.get(i);
				}
			}else if (l.equals(ITEMS_LIST)) {
				i++;
				l = lines.get(i);
				while(l.startsWith(LIST_PREFIX)) {
					String item = l.substring(LIST_PREFIX.length());
					if(!dangerItems.contains(item)) {
						availableItems.add(item);
					}
					i++;
					l = lines.get(i);
				}
			}
		}
		
		lines.stream().forEach(System.out::println);
	}
	
	private void reset() {
		dangerItems.add(lastItem);
		rooms = new HashMap<String, Room>();
		items = new HashSet<String>();
		availableItems = new HashSet<String>();
		directionTravelled = null;
		
		outputPosition = 0;
		poisenedItem = false;
		lastItem = "";
		
		String[] ins = Arrays.copyOf(instructions, instructions.length);
		ascii = new Computer(ins);
		
		inputs = new ArrayList<Long>();
	}
	
	private String nextMovement() {
		Queue<Pair<String, List<String>>> queue = new ArrayDeque<Pair<String,List<String>>>();
		queue.add(new Pair<String, List<String>>(location, new ArrayList<String>()));
		Set<String> alreadyVisited = new HashSet<String>();
		
		Pair<String, List<String>> current = null;
		
		while(!queue.isEmpty()) {
			current = queue.remove();
			Room room = rooms.get(current.getValue0());
			List<String> path = current.getValue1();
			List<String> newDirections = new ArrayList<String>();
			for(String direction : room.getAvailableDirections()) {
				if(!room.getNeighbours().containsKey(direction)) {
					path.add(direction);
					break;
				}else {
					newDirections.add(direction);
				}
			}
			alreadyVisited.add(current.getValue0());
			for(String direction : newDirections) {
				Room nextRoom = room.getNeighbours().get(direction);
				if(!alreadyVisited.contains(nextRoom.toString())) {
					List<String> newPath = new ArrayList<String>();
					newPath.addAll(path);
					newPath.add(direction);
					Pair<String, List<String>> next = new Pair<String, List<String>>(nextRoom.toString(), newPath);
					queue.add(next);
				}
			}
		}
		
		return current.getValue1().get(0);
	}
	
	
	
}