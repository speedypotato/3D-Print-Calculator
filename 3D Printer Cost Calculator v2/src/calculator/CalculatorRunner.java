package calculator;

import java.util.*;
import java.io.*;

/**
 * Computes the cost of a 3D print
 * @author SomeTechNoob
 * @version 2.0.0
 */
public class CalculatorRunner {
	private TreeSet<PlasticType> predefinedPlastics;
	
	//Avg. Electricity Cost in $/kWh
	public static final double ELECTRICITY_RATE = 0.1559;
	public static final double PSU_WATTS = 430;
	public static final double ONE_KILO = 1000;
	//minimum price is in $0.00
	public static final double MINIMUM_PRICE = 3;
	public static final double PROFIT_MULTIPLIER = 3;
	
	/**
	 * Creates a new CalculatorRunner
	 */
	public CalculatorRunner() {
		predefinedPlastics = new TreeSet<>();
	}
	
	/**
	 * Runs the calculator
	 */
	public static void main(String args[]) {
		System.out.println("Start v2.0.0");
		CalculatorRunner cr = new CalculatorRunner();
		
		//Load predefined filaments
		System.out.print("Loading Predefined Filaments ... ");
		boolean success = cr.loadFilaments();
		if (success) System.out.println("Done");
		else System.out.println("Failed");
		
		System.out.println("\nAvailable Filament Calculators(Sorted alphabetically): ");
		int counter = 1;
		for (PlasticType p : cr.predefinedPlastics) {
			System.out.println(counter + ". " + p.getName());
			counter++;
		}
		
		//Getting user input.  Some input checking added.
		ArrayList<PlasticType> indexedPlastics = new ArrayList<>(cr.predefinedPlastics);
		Scanner s = new Scanner(System.in);
		System.out.print("\nEnter the number next to the desired filament calculator: ");
		while (!s.hasNextInt()) {
			s.next();
			System.out.print("Invalid Input. Try again.");
		}
		int index = s.nextInt() - 1;
		if (index < 0 || index >= indexedPlastics.size()) {
			s.close();
			throw new IllegalArgumentException("Invalid option entered.");
		}
		
		System.out.print("Enter the calculated length of filament(mm) needed for the print: ");
		while (!s.hasNextDouble()) {
			s.next();
			System.out.print("Invalid Input. Try again.");
		}
		double length = s.nextDouble();
		
		System.out.print("Enter the print time's hours: ");
		while (!s.hasNextDouble()) {
			s.next();
			System.out.print("Invalid Input. Try again.");
		}
		double hrs = s.nextDouble();
		
		System.out.print("Enter the print time's minutes: ");
		while (!s.hasNextDouble()) {
			s.next();
			System.out.print("Invalid Input. Try again.");
		}
		double mins = s.nextDouble();
		s.close();
		
		double estCost = cr.printCalc(indexedPlastics.get(index), length, hrs, mins);
		double totalPrice = estCost * PROFIT_MULTIPLIER + MINIMUM_PRICE;
		
		System.out.println("\n~" + indexedPlastics.get(index).getName() + " Print Cost~");
		System.out.printf("Estimated electrical & plastic cost: $%,.2f\n", estCost);
		System.out.printf("Cost after markup and minimum cost: $%,.2f", totalPrice);
		System.out.println();
	}
	
	/**
	 * Loads the filaments predefined in Plastics.txt
	 * @return if filament loading was successful
	 */
	public boolean loadFilaments(){
		try {
			String loc = new File("").getAbsolutePath();
			loc += "\\Plastics.txt";
			//Creates a reader
			BufferedReader br = new BufferedReader(new FileReader(loc)); //"src/calculator/Plastics.txt"
			
			//Skips the initial line
			br.readLine();
			
			//loops through the file line by line
			while (true) {
				String plasticInfo = br.readLine();
				
				if (plasticInfo == null) break;
				
				//Grabs the first String, which is the name of the filament
				String plasticName = plasticInfo.substring(0, plasticInfo.indexOf(","));
				
				//Gets the data after the name and converts it to a double
				String plasticDoubles = plasticInfo.substring(plasticInfo.indexOf(",") + 1);
				String[] infoArray = plasticDoubles.split(",");
				ArrayList<Double> infoArrayConverted = new ArrayList<>();
				for (String temp : infoArray) {
					double convert = Double.parseDouble(temp);
					infoArrayConverted.add(convert);
				}
				
				//Creates a new PlasticType object
				predefinedPlastics.add(new PlasticType(plasticName, infoArrayConverted.get(0), infoArrayConverted.get(1), infoArrayConverted.get(2)));
			}
			br.close();
		} catch (IOException x) {
			System.out.println("Error: " + x.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Calculates the cost of the print
	 * @param p the type of plastic used
	 * @param length the length of filament as reported by the slicer
	 * @param hrs the amount of hours the print is estimated to take
	 * @param mins the amount of minutes (minus hours inputted earlier) the print is estimated to take
	 */
	public double printCalc(PlasticType p, double length, double hrs, double mins) {
		double volume = p.getfilamentDiameter() * length;
		double totalFilCost = (p.getDensity() / ONE_KILO) * (p.getCostPerKilogram() / ONE_KILO) * volume;
		double timeCost = (ELECTRICITY_RATE / ONE_KILO) * (mins / 60 + hrs) * PSU_WATTS;
		return totalFilCost + timeCost;
	}
}
