package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day4 {
	
	int minimum;
	int maximum;
	
	public Day4(int minimum, int maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public long puzzle1() {
		long result = 0;

		for(int i = minimum; i <= maximum; i++) {
			if(repeatingDigits(i) && notDecreasing(i)) {
				result ++;
			}
		}

		return result;
	}

	public long puzzle2() {
		long result = 0;

		for(int i = minimum; i <= maximum; i++) {
			if(doubleDigits(i) && notDecreasing(i)) {
				result ++;
			}
		}
		return result;
	}
	
	public boolean repeatingDigits(int password) {
		int previousDigit = 10;//set to 10, that way it can't equal the next digit
		
		while(password > 0) {
			int endDigit = password % 10;
			if(endDigit == previousDigit) {
				return true;
			}
			previousDigit = endDigit;
			password = password / 10;
		}
		
		return false;
	}
	
	public boolean notDecreasing(int password) {
		
		int previousDigit = 9;
		
		while(password > 0) {
			int endDigit = password % 10;
			if(endDigit > previousDigit) {
				return false;
			}
			previousDigit = endDigit;
			password = password / 10;
		}
		return true;
	}
	
	public boolean doubleDigits(int password) {
		
		List<Integer> digits = new ArrayList<Integer>();
		
		while(password > 0) {
			int endDigit = password % 10;
			digits.add(endDigit);
			password = password / 10;
		}
		
		for(int i = 0; i<digits.size()-1; i++) {
			int digit = digits.get(i);
			if(digit == digits.get(i+1)){
				if(i>0 && digits.get(i-1) != digit && i<digits.size()-2 && digits.get(i+2) != digit) {
					return true;
				}
				if(i==0 && digits.get(i+2) != digit) {
					return true;
				}
				if(i >= digits.size()-2 && digits.get(i-1) != digit) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
