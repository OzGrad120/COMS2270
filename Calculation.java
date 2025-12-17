package hw4;

import java.util.ArrayList;

/**
 *  A class that represents a calculation with an integer value and its string representation.
 *  
 * @author Osazee Osunde
 */
public class Calculation {
/**
 * The value used in a calculation.
 */
    private int value;
    
/**
 * The expression as a String
 */
    private String expression;

/**
 * Constructs a Calculation from a single integer.
 * @param v the integer value. 
 */
    public Calculation(int v) {
        this.value = v;
        this.expression = Integer.toString(v);
    }

/**
 * Constructs a Calculation by combining two Calculations with an operator.
 * @param v1 the first operand
 * @param v2 the second operand
 * @param op the operator used
 */
    public Calculation(Calculation v1, Calculation v2, char op) {
        this.expression = "(" + v1.expression + " " + op + " " + v2.expression + ")";
        if (op == '+') {
            this.value = v1.value + v2.value;
        } 
        else if (op == '-') {
            this.value = v1.value - v2.value;
        } 
        else if (op == '*') {
            this.value = v1.value * v2.value;
        } 
        else if (op == '/') {
            this.value = v1.value / v2.value;
        } 
        else if (op == '^') {
            this.value = (int) Math.pow(v1.value, v2.value);
        } 
        else {
            throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

/**
 * Returns the integer value of the Calculation
 * @return the integer value
 */
    public int intValue() {
        return value;
    }
    
/**
 * Return the string representation of the Calculation 
 * @return the string expression
 */
    @Override
    public String toString() {
        return expression;
    }

/**
 * Recursive method to find all solutions that equal the target.
 * @param expressions The list of current calculation objects.
 * @param target The target integer value.
 * @param results The list to store valid string expressions.
 */
    public static void generateTarget(ArrayList<Calculation> expressions, int target, ArrayList<String> results) {
        if (expressions.size() == 1) {
            if (expressions.get(0).intValue() == target) {
                results.add(expressions.get(0).toString());
            }
            return;
        }
        for(int i = 0; i < expressions.size(); ++i) {
        	ArrayList<Calculation> sublist = new ArrayList<Calculation>();
        	for(int j = 0; j < expressions.size(); j++) {
        		if(i != j) {
        			sublist.add(expressions.get(j));
        		}
        	}
        	generateTarget(sublist, target, results);
        }
        
        for(int i = 0; i < expressions.size(); ++i) {
        	for(int j = 0; j < expressions.size(); ++j) {
        		if(i != j) {
        			Calculation left = expressions.get(i);
        			Calculation right = expressions.get(j);
        			int a = left.intValue();
        			int b = right.intValue();
        			
        			char[] operators = {'+', '-', '*', '/', '^'};
        			
        			for (char op : operators) {
        				boolean validOperation = true;
        				Calculation newCalc = null;
        				
        				if(op == '+') {
        					newCalc = new Calculation(left, right, '+');
        				}
        				else if(op == '-') {
        					newCalc = new Calculation(left, right, '-');
        				}
        				else  if(op == '*') {
        					newCalc = new Calculation(left, right, '*');
        				}
        				else if(op == '/') {
        					if(b == 0 || a % b != 0) {
        						validOperation = false;
        					}
        					else {
        						newCalc = new Calculation(left, right, '/');
        					}
        				}
        				else if(op == '^') {
        					int powerResult = (int) Math.pow(a, b);
        					if(powerResult == Integer.MAX_VALUE || powerResult == Integer.MIN_VALUE) {
        						validOperation = false;
        					}
        					else {
        						newCalc = new Calculation(left, right, '^');
        					}
        				}
        				
        				if(validOperation && newCalc != null) {
        					ArrayList<Calculation> newList = new ArrayList<Calculation>();
        					for(int k = 0; k < expressions.size(); ++k) {
        						if (k != i && k != j) {
        							newList.add(expressions.get(k));
        						}
        					}
        					newList.add(newCalc);
        					generateTarget(newList, target, results);
        					
        				}
        			}
        		}
        	}
        }
    }
}
       