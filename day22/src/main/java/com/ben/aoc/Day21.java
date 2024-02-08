package com.ben.aoc;

import java.math.BigInteger;
import java.util.List;
import com.ben.aoc.maths.LinearCongruenceFunction;

public class Day21 {
	
	private static final int DECK_SIZE = 10007;
	private static final long BIG_DECK_SIZE = 119315717514047l;
	private static final long SHUFFLE_COUNT = 101741582076661l;
	
	long pos2020 = 2020;
	
	private static final String CUT = "cut ";
	private static final String INCR_DEAL = "deal with increment ";
	private static final String NEW_DEAL = "deal into new stack";
	
	List<String> lines;
	
	
	public Day21(String fileName) {
		lines = Util.readFile(getClass(), fileName);
	}

	public long puzzle1() {
		BigInteger m = BigInteger.valueOf(DECK_SIZE);
		LinearCongruenceFunction lcf = composeInput(m);
		
		BigInteger result = lcf.solveX(BigInteger.valueOf(2019));
		return result.longValue();

	}
	
	public long puzzle2() {
		BigInteger twentytwenty = BigInteger.valueOf(2020);
		
		BigInteger m = BigInteger.valueOf(BIG_DECK_SIZE);
		LinearCongruenceFunction lcf = composeInput(m);
		
		BigInteger composeCount = BigInteger.valueOf(SHUFFLE_COUNT);
		lcf = lcf.selfCompose(composeCount);
		
		BigInteger result = twentytwenty.subtract(lcf.getb());
		result = result.multiply(lcf.geta().modInverse(m)).mod(m);
		
		return lcf.solveInverse(twentytwenty).longValue();
	}
	

	private LinearCongruenceFunction composeInput(BigInteger m) {
		LinearCongruenceFunction lcf = new LinearCongruenceFunction(BigInteger.ONE, BigInteger.ZERO, m);
		
		for(String line : lines) {
			if(line.startsWith(CUT)) {
				int n = Integer.parseInt(line.substring(CUT.length()));
				LinearCongruenceFunction cut = new LinearCongruenceFunction(BigInteger.ONE, BigInteger.valueOf(n*-1), m);
				lcf = cut.compose(lcf);
			}else if(line.startsWith(INCR_DEAL)) {
				int n = Integer.parseInt(line.substring(INCR_DEAL.length()));
				LinearCongruenceFunction incr = new LinearCongruenceFunction(BigInteger.valueOf(n), BigInteger.ZERO, m);
				lcf = incr.compose(lcf);
			}else if(line.equals(NEW_DEAL)) {
				BigInteger negOne = BigInteger.valueOf(-1);
				LinearCongruenceFunction deal = new LinearCongruenceFunction(negOne, negOne, m);
				lcf = deal.compose(lcf);
			}
		}
		return lcf;
	}
}
