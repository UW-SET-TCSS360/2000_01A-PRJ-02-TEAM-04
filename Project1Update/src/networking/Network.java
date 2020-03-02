/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package networking;

import java.util.HashMap;

import simulator.Simulator;
import storage.Storage;
import storage.WeatherType;

/**A simulator of a network which regularly requests the data from the simulator, passes it to storage, 
 * and informs the window that the new data has arrived.
 * 
 * */
public class Network {

	Storage myStorage;
	Simulator mySimulator;
	/**
	 * Initializes the network with access to the storage elements used by the front end.
	 * @param theStorage
	 */
	public Network(Storage theStorage, Simulator theSimulator) {
		myStorage = theStorage;
		mySimulator = theSimulator;
	}

	/**
	 * A method that requests that he simulator produce new values, returns true if the
	 * simulator is still able to produce new values.
	 * @return True if the storage has been sent new data.
	 */
	public boolean checkSimulator() {
		mySimulator.updateCurrent();
		
		return true;
		//Currently, as this network is simulated, it will always succeed at its task barring an exception.
	    //Later versions would return false when the front end has no new data to update to.
	}

	/**
	 * Retrieves the most up to date values and sends them to the storage system.
	 */
	public void sendToStorage() {
		HashMap<WeatherType, Double> aMap =  mySimulator.getCurrent();
		for (WeatherType t : aMap.keySet()) {
			myStorage.add(t, aMap.get(t));
		}
	}

}
