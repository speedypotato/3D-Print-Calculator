package calculator;

public class PlasticType implements Comparable {
	private String filamentName;
	private double filamentDensity;
	private double costPerKilogram;
	private double filamentDiameter;
	
	/**
	 * Constructs a new PlasticType
	 * @param name the name of the filament
	 * @param density the density of the filament in g/cm^3.
	 * @param costPerKilo the cost of the filament per 1 kg.
	 * @param filamentDiameter the filament's diameter.
	 */
	public PlasticType(String name, double density, double costPerKilo, double filamentDiameter) {
		this.filamentName = name;
		this.filamentDensity = density;
		this.costPerKilogram = costPerKilo;
		this.filamentDiameter = filamentDiameter;
	}
	
	/**
	 * Gets the name of the filament
	 * @return the name of the filament
	 */
	public String getName() {
		return filamentName;
	}
	
	/**
	 * Gets the density of the filament
	 * @return the density of the filament
	 */
	public double getDensity() {
		return filamentDensity;
	}
	
	/**
	 * Gets the cost of the plastic per kilogram
	 * @return the cost of the plastic per kilogram
	 */
	public double getCostPerKilogram() {
		return costPerKilogram;
	}
	
	/**
	 * Gets the filament's diameter
	 * @return the diameter of the filament
	 */
	public double getfilamentDiameter() {
		return filamentDiameter;
	}

	/**
	 * Sets the name of the filament
	 * @return the name of the filament
	 */
	public void setName(String name) {
		this.filamentName = name;
	}
	
	/**
	 * Sets the density of the filament
	 * @param density the density of the filament
	 */
	public void setDensity(double density) {
		this.filamentDensity = density;
	}
	
	/**
	 * Sets the cost of the plastic per kilogram
	 * @param cost the cost of the filament
	 */
	public void setCostPerKilogram(double cost) {
		this.costPerKilogram = cost;
	}
	
	/**
	 * Sets the filament's diameter
	 * @param diameter the diameter of the filament
	 */
	public void setFilamentDiameter(double diameter) {
		this.filamentDiameter = diameter;
	}
	
	/**
	 * Compares Plastics by name
	 * @param that the plastic to compare to
	 * @return a compareTo int
	 */
	public int compareTo(Object that) {
		return filamentName.compareTo(((PlasticType)that).getName());
	}
	
	/**
	 * Checks if filaments have the same name
	 * @param that the filament to compare to
	 * @return if the filaments have the same name
	 */
	public boolean equals(PlasticType that) {
		if (this.compareTo(that) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Simply returns a String of filament data
	 */
	public String toString() {
		String s = filamentName + ", " + filamentDensity + "g/cm^3, $" + costPerKilogram + "/kg, " + filamentDiameter + "mm";
		return s;
	}
	
	/**
	 * Simple hash
	 */
	public int hashCode() {
		return filamentName.hashCode();
	}
}
