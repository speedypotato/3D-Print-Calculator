package calculator;

import java.util.*;
import java.io.*;

/**
 * Computes the cost of a 3D print
 * @author SomeTechNoob
 * @version 2.1.1
 */
public class CalculatorRunner {
	public static final double ONE_KILO = 1000;
	
	private double electricityRate;
	private double minPrintPrice;
	private double profitMultiplier;
	private String calculatorType;
	private TreeSet<Printer> predefinedPrinters;
	private TreeSet<PlasticType> predefinedPlastics;
	
	/**
	 * Creates a new CalculatorRunner
	 */
	public CalculatorRunner() {
		predefinedPrinters = new TreeSet<>();
		predefinedPlastics = new TreeSet<>();
	}
	
	/**
	 * Runs the calculator
	 */
	public static void main(String args[]) {
		System.out.println("Start v2.1.1");
		CalculatorRunner cr = new CalculatorRunner();
		
		//Load config
		System.out.print("Loading Configuration ... ");
		boolean cSuccess = cr.loadConfig();
		if (cSuccess) System.out.println("Done");
		else {
			System.out.println("Failed");
			throw new IllegalArgumentException("Config File Not Found.");
		}
		
		//Load predefined printers
		System.out.print("Loading Predefined Printers ... ");
		boolean pSuccess = cr.loadPrinters();
		if (pSuccess) System.out.println("Done");
		else {
			System.out.println("Failed");
			throw new IllegalArgumentException("Printer File Not Found.");
		}
		
		//Load predefined filaments
		System.out.print("Loading Predefined Filaments ... ");
		boolean success = cr.loadFilaments();
		if (success) System.out.println("Done");
		else {
			System.out.println("Failed");
			throw new IllegalArgumentException("Filament File Not Found.");
		}
		
		//Listing Printers
		Scanner s = new Scanner(System.in);
		Printer printer;
		ArrayList<Printer> indexedPrinters = new ArrayList<>(cr.predefinedPrinters);
		if (cr.predefinedPrinters.size() == 1) {
			System.out.println("Only 1 printer profile.  Automatically Selected.");
			printer = indexedPrinters.get(0);
		} else {
			System.out.println("\nAvailable Printers(Sorted alphabetically): ");
			int pCounter = 1;
			for (Printer p : cr.predefinedPrinters) {
				System.out.println(pCounter + ". " + p.getName());
				pCounter++;
			}
			//User input for Printer selection
			System.out.print("\nEnter the number next to the desired printer: ");
			while (!s.hasNextInt()) {
				s.next();
				System.out.print("Invalid Input. Try again. ");
			}
			int pIndex = s.nextInt() - 1;
			if (pIndex < 0 || pIndex >= indexedPrinters.size()) {
				s.close();
				throw new IllegalArgumentException("Invalid option entered.");
			}
			printer = indexedPrinters.get(pIndex);
			System.out.println(printer.toString() + " selected.");
		}
		
		//Listing Filaments
		System.out.println("\nAvailable Filament Calculators(Sorted alphabetically): ");
		int counter = 1;
		for (PlasticType p : cr.predefinedPlastics) {
			System.out.println(counter + ". " + p.getName());
			counter++;
		}
		
		//Getting user input.  Some input checking added.
		ArrayList<PlasticType> indexedPlastics = new ArrayList<>(cr.predefinedPlastics);
		System.out.print("\nEnter the number next to the desired filament calculator: ");
		while (!s.hasNextInt()) {
			s.next();
			System.out.print("Invalid Input. Try again. ");
		}
		int index = s.nextInt() - 1;
		if (index < 0 || index >= indexedPlastics.size()) {
			s.close();
			throw new IllegalArgumentException("Invalid option entered.");
		}
		//v2.1.1 length vs weight if/else
		double weight = 0;
		double length = 0;
		if (cr.calculatorType.equals("weight")) {
			System.out.print("Enter the calculated weight(g) needed for the print: ");
			while (!s.hasNextDouble()) {
				s.next();
				System.out.print("Invalid Input.  Try again. ");
			}
			weight = s.nextDouble();
		} else {
			System.out.print("Enter the calculated length of filament(mm) needed for the print: ");
			while (!s.hasNextDouble()) {
				s.next();
				System.out.print("Invalid Input. Try again. ");
			}
			length = s.nextDouble();
		}
		System.out.print("Enter the print time's hours: ");
		while (!s.hasNextDouble()) {
			s.next();
			System.out.print("Invalid Input. Try again. ");
		}
		double hrs = s.nextDouble();
		
		System.out.print("Enter the print time's minutes: ");
		while (!s.hasNextDouble()) {
			s.next();
			System.out.print("Invalid Input. Try again. ");
		}
		double mins = s.nextDouble();
		s.close();
		
		double estCost;
		//v2.1.1 length vs weight if/else
		if (cr.calculatorType.equals("weight")) {
			estCost = cr.costCalcWeight(indexedPlastics.get(index), weight, hrs, mins, printer.getWatts());
		} else {
			estCost = cr.costCalc(indexedPlastics.get(index), length, hrs, mins, printer.getWatts());
		}
		double totalPrice = estCost * cr.profitMultiplier + cr.minPrintPrice;
		
		System.out.println("\n~" + printer.getName() + " | " + indexedPlastics.get(index).getName() + " Print Cost~");
		System.out.printf("Estimated electrical & plastic cost: $%,.2f\n", estCost);
		System.out.printf("Cost after markup and minimum cost: $%,.2f", totalPrice);
		System.out.println();
	}
	
	/**
	 * Loads program configuration
	 * @return if config loading was successful
	 */
	public boolean loadConfig() {
		try {
			String loc = new File("").getAbsolutePath();
			loc += "\\Config.txt";
			//Creates a reader
			BufferedReader br = new BufferedReader(new FileReader(loc));
			
			//Skips the initial line
			br.readLine();
			
			String configInfo = br.readLine();
			//Grabs the first double, which is the electricity rate in $/kWh
			electricityRate = Double.parseDouble(configInfo.substring(0, configInfo.indexOf(",")));
			minPrintPrice = Double.parseDouble(configInfo.substring(configInfo.indexOf(",") + 1, configInfo.lastIndexOf(",")));
			profitMultiplier = Double.parseDouble(configInfo.substring(configInfo.lastIndexOf(",") + 1));
			
			//Skips the line containing info on calculations based on weight/length
			br.readLine();
			//determines whether to use weight or length calculator
			calculatorType = br.readLine().toLowerCase();
			
			br.close();
		} catch (IOException x) {
			System.out.println("Error: " + x.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Loads the printers predefined in Printers.txt
	 * @return if printer loading was successful
	 */
	public boolean loadPrinters() {
		try {
			String loc = new File("").getAbsolutePath();
			loc += "\\Printers.txt";
			//Creates a reader
			BufferedReader br = new BufferedReader(new FileReader(loc));
			
			//Skips the initial line
			br.readLine();
			
			//loops through the file line by line
			while (true) {
				String printerInfo = br.readLine();
				
				if (printerInfo == null) break;
				
				//Grabs the first String, which is the name of the printer
				String printerName = printerInfo.substring(0, printerInfo.indexOf(","));
				
				//Gets the wattage, which is after the name
				String printerWatts = printerInfo.substring(printerInfo.indexOf(",") + 1);
				double printerWattsConverted = Double.parseDouble(printerWatts);
				
				//Creates a new Printer object
				predefinedPrinters.add(new Printer(printerName, printerWattsConverted));
			}
			br.close();
		} catch (IOException x) {
			System.out.println("Error: " + x.getMessage());
			return false;
		}
		return true;
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
	 * @param wattage the amount of wattage the printer uses
	 * @return the total cost of the print
	 */
	public double costCalc(PlasticType p, double length, double hrs, double mins, double wattage) {
		double volume = p.getfilamentDiameter() * length;
		double totalFilCost = (p.getDensity() / ONE_KILO) * (p.getCostPerKilogram() / ONE_KILO) * volume;
		double timeCost = (electricityRate / ONE_KILO) * (mins / 60 + hrs) * wattage;
		return totalFilCost + timeCost;
	}
	
	/**
	 * Calculates the cost of the print based on weight
	 * @param p the type of plastic used
	 * @param weight the weight of filament used
	 * @param hrs the amount of hours the print is estimated to take
	 * @param mins the amount of minutes the print is estimated to take
	 * @param wattage the amount of wattage the printer uses
	 * @return the total cost of the print
	 */
	public double costCalcWeight(PlasticType p, double weight, double hrs, double mins, double wattage) {
		double plasticCost = p.getCostPerKilogram() * (weight / ONE_KILO);
		double timeCost = (electricityRate / ONE_KILO) * (mins / 60 + hrs) * wattage;
		return plasticCost + timeCost;
	}
}
