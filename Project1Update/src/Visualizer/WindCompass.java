/**
 * 
 * 
 */
package Visualizer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import storage.WeatherType;


public class WindCompass extends JPanel {
	/**
	 * The numbers format used in an upperPanelWindow's UI.
	 */
	private DecimalFormat myFormat = new DecimalFormat("#0.0");

	/**
	 * The specific types of weather information our interface is ready to display.
	 */
	private WeatherType[] supportedTypes = { WeatherType.wind, WeatherType.winddir, WeatherType.rain,
			WeatherType.barometric, WeatherType.temp, WeatherType.humidity, WeatherType.outtemp,
			WeatherType.outhumidity};

	/**
	 * A string driven map of the types of weather data.
	 */
	private Map<WeatherType, Double> myData;
	/**
	 * A map which associates the JLabels for displaying each element of weather
	 * information.
	 */

	private String windDir;
	private String windLabel;
	private String windSpeed;
	private Map<WeatherType, JLabel> myDisplayCompass;
	JPanel compassPanel;
	private JLabel lblWindSpeed;	
	
	private JLabel N;
	private JLabel S;
	private JLabel E;
	private JLabel W;
	private JLabel NW;
	private JLabel NE;
	private JLabel SW;
	private JLabel SE;
	


	/**
	 * Generates a window that is used to display current weather information.
	 * The input map MUST contain values for the wind, wind direction, rain, barometric pressure,
	 * temp, humidity, outdoor temperature, or outdoor humidity, otherwise this method will throw
	 * an illegal argument exception.
	 * 
	 * @param theData the Weather Data, in accordance with our data types.
	 */
	public WindCompass(Map<WeatherType, Double> theData) {
		super();
		myData = new HashMap<>();
		myData.putAll(theData);
		setupDisplay();
		//windSpeedButtons();


		setPreferredSize(new Dimension(250, 190));
		//		//display();
		add(createCompass());

	}

	/**
	 * Testing Version of the constructor, initializes the upper display with
	 * default data.
	 */
	public WindCompass() {
		super();

		myData = new HashMap<>();
		myData.put(supportedTypes[0], 15.2); // wind
		myData.put(supportedTypes[1], 23.2); // wind direction
		myData.put(supportedTypes[2], 0.3);
		myData.put(supportedTypes[3], 1.05);
		myData.put(supportedTypes[4], 72.3);
		myData.put(supportedTypes[5], 30.7);
		myData.put(supportedTypes[6], 52.2);
		myData.put(supportedTypes[7], 86.3);
		myData.put(WeatherType.windchill, 50.0);
		setupDisplay();
		setPreferredSize(new Dimension(250, 190));
		//display();
		add(createCompass());
	}

	/**
	 * Generates and stores the JLabels in a organized and easily accessed way.
	 */
	private void setupDisplay() {
		// Setup Wind Display

		myDisplayCompass = new HashMap<>();
		for (int i = 0; i < supportedTypes.length; i++) {
			JLabel windLabel = new JLabel(getCompassLabel(i));			
			myDisplayCompass.put(supportedTypes[i], windLabel);
			
		}

	}

	/**
	 * Returns a JPanel thar creates the outline of the compass
	 * @return
	 */
	public JPanel createCompass() {

		compassPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Shape circle = new Ellipse2D.Double(25, 40, 205, 205);
				g2.draw(circle);

			}
		};// end of paint component

		compassPanel.setPreferredSize(new Dimension(250, 250));
		compassPanel.setLayout(null);
		//windSpeedButtons();
		permanentCompassLabels();

		JLabel lblMPH = new JLabel("MPH");
		lblMPH.setBounds(105, 250, 35, 14);
		compassPanel.add(lblMPH);

		JLabel lblWindSpeed = new JLabel("Wind Speed / Direction");
		lblWindSpeed.setFont(new Font("Tahoma", 12 , 12));
		lblWindSpeed.setBounds(40, 10, 200, 20);
		compassPanel.add(lblWindSpeed);

		return compassPanel;

	}

	/**
	 * This method creates and adds the permanent labels on the compass panel that don't move.
	 */
	public void permanentCompassLabels() {
		JLabel lblN = new JLabel("N");
		lblN.setBounds(125, 50, 18, 14);
		compassPanel.add(lblN);

		JLabel lblS = new JLabel("S");
		lblS.setBounds(125, 220, 18, 14);
		compassPanel.add(lblS);

		JLabel lblW = new JLabel("W");
		lblW.setBounds(40, 130, 18, 14);
		compassPanel.add(lblW);

		JLabel lblE = new JLabel("E");
		lblE.setBounds(205, 130, 18, 14);
		compassPanel.add(lblE);
		
		windSpeedButtons();
	}


	/**
	 * Automatically generates an appropriate string for the specific measurement
	 * and its units.
	 * 
	 * @param index The data measurements index.
	 * @return A string representing the current data in a human readable form.
	 */

	public String getCompassLabel(int index) {

		String toReturn;
		if (index == 6) {
			toReturn = "          " + supportedTypes[index] + " (w/Windchill): " + 
					myFormat.format(myData.get(supportedTypes[index]))
			+ " " + supportedTypes[index].getUnits() + "\n (" + myFormat.format(myData.get(WeatherType.windchill)) + " "
			+ supportedTypes[index].getUnits() + ")";
		} // When outdoor temps are displayed, it is displayed both with and without
		// windchill. The - 6 is a default wind chill value that will be updated.
		else if (index == 1) { toReturn = getWindDirection(myData.get(supportedTypes[index]));}

		//Use a word to describe the wind direction.
		else
			toReturn = myFormat.format(myData.get(supportedTypes[index]));

		return toReturn;
		//return windDir;
	}

	//Returns the wind direction 
	public String getWindDir() {
		return windDir;
	}
	//Returns a string of the wind speed
	public String getWindSpeed() {
		return windSpeed;
	}


	/**
	 * Takes an angle that is in degrees, and returns a direction starting from straight north, and rotating degrees clockwise.
	 * AKA, the "Degrees from north" method.
	 * @param theDegree A double representing a value that is between from 0 to 360.
	 * @return A string describing the direction the given angle points in.
	 */

	public String getWindDirection(Double theDegree) {

		if (theDegree<0 || theDegree>360)
			throw new IllegalArgumentException();
		if (theDegree<30 && theDegree>=0 || theDegree>=330 && theDegree <=360) windDir="N";
		else if (theDegree>=30 && theDegree<60) windDir = "NE";
		else if (theDegree>=60 && theDegree<120) windDir = "E";
		else if (theDegree>=120 && theDegree<150) windDir =  "SE";
		else if (theDegree>=150 && theDegree<210)  windDir = "S"; 
		else if (theDegree>=210 && theDegree<240)  windDir = "SW"; 
		else if (theDegree>=240 && theDegree<300)  {windDir = "W";

		} else { windDir = "NW"; }
		return windDir;
	}

	private void windSpeedButtons() {
	
		N = new JLabel("N");		
		N.setBounds(125, 70, 30,30 );
		compassPanel.add(N);

		NW = new JLabel("NW");
		NW.setBounds(65, 65, 30, 30);
		compassPanel.add(NW);

		NE = new JLabel("NE");		
		NE.setBounds(175, 70, 30, 30);
		compassPanel.add(NE);

		E = new JLabel("E");
		E.setBounds(178, 122, 30, 30);
		compassPanel.add(E);

		SE = new JLabel("SE");
		SE.setBounds(185, 175, 30, 30);
		compassPanel.add(SE);

		S = new JLabel("S");
		S.setBounds(125, 185, 30, 30);
		compassPanel.add(S);

		SW = new JLabel("SW");
		SW.setBounds(55, 175, 30, 30);
		compassPanel.add(SW);

		W = new JLabel("W");
		W.setBounds(70, 129, 20, 18);
		compassPanel.add(W);

	}
	

	/**
	 * Updates the data and display labels.
	 * 
	 * @param A map of "Weather data types" to the newest data values. May be
	 *          partially filled, in which case only the correct display elements
	 *          will be updated.
	 */
	public void updateData(Map<WeatherType, Double> someData) {
		myData.put(WeatherType.windchill, someData.get(WeatherType.windchill));
		//First, update the data on windchill, as it is not supported by its own UI panel, and is instead a sub-element of another panel.
		for (int i = 0; i < supportedTypes.length; i++) {
			WeatherType type = supportedTypes[i]; // Get the Datatype.
			if (someData.containsKey(type)) { // See if it was included in the list of updated data.
				myData.put(type, someData.get(type)); // Update our local copy of the data.
				String toSet2 = getCompassLabel(i);

				myDisplayCompass.get(type).setText(toSet2);
				windDir = myDisplayCompass.get(supportedTypes[1]).getText();
				windSpeed = myDisplayCompass.get(supportedTypes[0]).getText();
								
				JLabel[] arrayOfLables = {N,NW,NE,S,SW,SE,W,E};
				List<JLabel> labels = new ArrayList<>(Arrays.asList(arrayOfLables));	

				JLabel updateLabel = labels.get(i);				
				String toSet = windSpeed;
				updateLabel.setVisible(false);
				
				
				if (updateLabel.getText().equals(windDir)){
					updateLabel.setVisible(true);
					updateLabel.setText(toSet);
	
					
				}
				
			} 
		}
	}

}



///**
//* These create the permanent Compass Labels
//*/
//private JLabel windSpeedLabels(String windDir) {
//	JLabel label = new JLabel(windDir);
////	if(windDir.equals("N")) label.setBounds(112, 70, 11, 10);
////	 if(windDir.equals("NW"))label.setBounds(65, 65, 25, 20);
////	 else if(windDir.equals("NE"))label.setBounds(150, 70, 18, 10);
////	 else if(windDir.equals("E"))label.setBounds(155, 105, 11, 10);
////	 else if(windDir.equals("SE"))label.setBounds(150, 150, 18, 10);
////	 else if(windDir.equals("S"))label.setBounds(112, 150, 11, 10);
////	 else if(windDir.equals("SW")) {label.setBounds(70, 150, 18, 10);
////	 } else {
////		 
////		 label.setBounds(70, 105, 11, 10);
////		 }
//	 
//			 
//	return label;
//}

//N = new JLabel("N");		
//N.setBounds(112, 70, 11, 10);
//compassPanel.add(N);
//
//NW = new JLabel("NW");
//NW.setBounds(65, 65, 25, 20);
//compassPanel.add(NW);
//
//NE = new JLabel("NE");		
//NE.setBounds(150, 70, 18, 10);
//compassPanel.add(NE);
//
//E = new JLabel("E");
//E.setBounds(155, 105, 11, 10);
//compassPanel.add(E);
//
//SE = new JLabel("SE");
//SE.setBounds(150, 150, 18, 10);
//compassPanel.add(SE);
//
//S = new JLabel("S");
//S.setBounds(112, 150, 11, 10);
//compassPanel.add(S);
//
//SW = new JLabel("SW");
//SW.setBounds(70, 150, 18, 10);
//compassPanel.add(SW);

//W = new JLabel("W");
//W.setBounds(70, 105, 16, 14);
//compassPanel.add(W);










