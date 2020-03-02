/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package Visualizer;

import java.util.HashMap;

import simulator.Simulator;
import storage.Storage;
import storage.WeatherType;
import storage.Window;

/**
 * A class which initializes the vantage vue simulator, containing the main method for the class overall.
 * @author Rory Fisher.
 *
 */
public class WeatherVisualizer {

	/**
	 * Activates the front and backend of the Vue simulator.
	 * 
	 * @param args Command line args, currently unused.
	 */
	public static void main(String[] args) {
		Simulator aSimulator = new Simulator();
		
		//Initialize the simulator.
		HashMap<WeatherType, Double[]> Months = aSimulator.getInitialSets(Window.months);
		HashMap<WeatherType, Double[]> Days = aSimulator.getInitialSets(Window.days);
		HashMap<WeatherType, Double[]> Hours = aSimulator.getInitialSets(Window.hours);
		aSimulator.newDay();
		//Set up data to create a "Testing" copy of the storage.
		Storage aStorage = new Storage(Hours,Days,Months);
		//Run the storage generation with the test data.
		WeatherVisualizerWindow myWindow = new WeatherVisualizerWindow(aStorage, aSimulator);
		//Generate the window.
		myWindow.display();
	}

}
