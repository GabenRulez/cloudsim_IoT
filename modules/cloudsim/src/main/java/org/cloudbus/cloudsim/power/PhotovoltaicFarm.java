package org.cloudbus.cloudsim.power;

import java.util.concurrent.ThreadLocalRandom;

public class PhotovoltaicFarm {


    private final int numberOfPanels;
    private final int panelArea;
    private final double solarPanelYield;
    private final double performanceRatio;
    /*private final double solarPanelAngle;
    private final double northing;  // odchylenie na północ (chyba tak jest po angielsku)*/

    public PhotovoltaicFarm(int numberOfPanels, int panelArea, double solarPanelYield, double performanceRatio/*, double solarPanelAngle, double northing*/){
        this.numberOfPanels = numberOfPanels;
        this.panelArea = panelArea;
        this.solarPanelYield = solarPanelYield;
        this.performanceRatio = performanceRatio;
        /*this.solarPanelAngle = solarPanelAngle;
        this.northing = northing;*/

        checkIfParametersAreWrong();
    }

    public PhotovoltaicFarm(){
        System.out.println("PhotovoltaicFarm constructor called without parameters.");

        this.numberOfPanels = ThreadLocalRandom.current().nextInt(1,25);
        this.panelArea = ThreadLocalRandom.current().nextInt(1,5);
        this.solarPanelYield = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        this.performanceRatio = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        /*this.solarPanelAngle = ThreadLocalRandom.current().nextDouble(-Math.PI /2, Math.PI /2); // Angle measured in radians between normal and solar panel
        this.northing = ThreadLocalRandom.current().nextDouble(0.0, Math.PI /2); // Not sure about a range of northing will be in*/
        checkIfParametersAreWrong();

        System.out.printf("Class got random attributes 'numberOfPanels'=%d 'panelArea'=%d 'solarPanelYield'=%g 'performanceRatio'=%g%n", numberOfPanels, panelArea, solarPanelYield, performanceRatio);
    }


    public double calculateEnergy(double timeDelta, double radiation) {
        // In order to get energy produced by solar panels we used following equation: E = A x r x H x PR
        // where:
    //int numberOfPanels = 10;
        // A - total panel area [m^2]
    //int panelArea = 10;
        // r - solar panel yield [%]
    //double solarPanelYield = 0.8;
        // H assumption: instead of annual average solar radiation on tilted panel average solar radiation
        // in given period of time was used
        // PR - performance ratio, constant for losses (range between 0.5 and 0.9, default value = 0.75)
    //double performanceRatio = 0.75;

        double averageSolarRadiationInGivenPeriodOfTime = radiation / timeDelta;
        double powerInKiloWh = panelArea * numberOfPanels * solarPanelYield * averageSolarRadiationInGivenPeriodOfTime * performanceRatio;
        return 3600000 * powerInKiloWh; // power in W*sec = J
    }

    private void checkIfParametersAreWrong(){
        String errorString = "Arguments passed to PhotovoltaicFarm constructor were incorrect.";
        if(numberOfPanels < 0)                                  throw new IllegalArgumentException(errorString + " 'numberOfPanels' shouldn't be lesser than 0. Got '" + numberOfPanels + "'.");
        if(panelArea < 0)                                       throw new IllegalArgumentException(errorString + " 'panelArea' shouldn't be lesser than 0. Got '" + panelArea + "'.");
        if(solarPanelYield < 0.0 || solarPanelYield > 1.0)      throw new IllegalArgumentException(errorString + " 'solarPanelYield' should be between 0.0 and 1.0. Got '" + solarPanelYield + "'.");
        if(performanceRatio < 0.0 || performanceRatio > 1.0)    throw new IllegalArgumentException(errorString + " 'performanceRatio' should be between 0.0 and 1.0. Got '" + performanceRatio + "'.");
        /*if(solarPanelAngle < -Math.PI /2 || solarPanelAngle > Math.PI /2) throw new IllegalArgumentException(errorString + " 'solarPanelAngle' should be between -PI/2 and PI/2. Got '" + solarPanelAngle + "'.");
        if(northing < 0 || northing > Math.PI/2)                throw new IllegalArgumentException(errorString + " 'northing' should be between 0.0 and PI/2. Got '" + northing + "'.");*/
    }
}
