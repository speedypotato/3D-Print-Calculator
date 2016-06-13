package calculator;

/**
 * Simulates a Printer
 * @author SomeTechNoob
 * @version 2.1.0
 */
public class Printer implements Comparable {
	private String name;
	private double wattage;
	
	/**
	 * Creates a new Printer
	 * @param name the name of the printer
	 * @param wattage ideally the average amount of power the printer uses in watts
	 */
	public Printer(String name, double wattage) {
		this.name = name;
		this.wattage = wattage;
	}
	
	/**
	 * Compares printers using names
	 * @param o the printer to compare to
	 * @return an int for compareTo
	 */
	public int compareTo(Object o) {
		Printer that = (Printer)o;
		return this.getName().compareTo(that.getName());
	}
	
	/**
	 * Checks if printers are equals based on compareTo
	 * @param o the Printer to compare to
	 * @return if the printers are equal or not
	 */
	public boolean equals(Object o) {
		Printer that = (Printer)o;
		if (this.compareTo(that) == 0) return true;
		else return false;
	}
	
	/**
	 * Gets the name of the printer
	 * @return the name of the printer
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the wattage of the printer
	 * @return the wattage of the printer
	 */
	public double getWatts() {
		return wattage;
	}
	
	/**
	 * Sets the name of the printer
	 * @param name the new name of the printer
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the wattage of the printer
	 * @param watts the new wattage of the printer
	 */
	public void setWatts(double watts) {
		this.wattage = watts;
	}
	
	/**
	 * Simply returns a String of printer data
	 * @return a string of information about the printer
	 */
	public String toString() {
		String s = name + "(" + wattage + "w)";
		return s;
	}
	
	/**
	 * Simple hash
	 */
	public int hashCode() {
		return name.hashCode();
	}
}
