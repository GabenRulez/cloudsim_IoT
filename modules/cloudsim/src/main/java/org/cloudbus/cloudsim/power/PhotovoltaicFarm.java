package org.cloudbus.cloudsim.power;

import java.util.concurrent.ThreadLocalRandom;

public class PhotovoltaicFarm {

    private final int numberOfPanels;
    private final double panelArea;
    private final double solarPanelYield;
    private final double performanceRatio;
    private final double solarPanelAngle;
    private final double efficiency;
    private double angleOfIncidenceOfSunsRays = 0; // i angle in equation
    private double angleZ = 3 * Math.PI / 2; // Z angle in equation

    public PhotovoltaicFarm(int numberOfPanels, double panelArea, double solarPanelYield, double performanceRatio, double solarPanelAngle, double angleOfIncidenceOfSunsRays, double angleZ, double efficiency){
        this.numberOfPanels = numberOfPanels;
        this.panelArea = panelArea;
        this.solarPanelYield = solarPanelYield;
        this.performanceRatio = performanceRatio;
        this.solarPanelAngle = solarPanelAngle;
        this.angleOfIncidenceOfSunsRays = angleOfIncidenceOfSunsRays;
        this.angleZ = angleZ;
        this.efficiency = efficiency;

        checkIfParametersAreWrong();
    }

    public PhotovoltaicFarm(int numberOfPanels, double panelArea, double solarPanelYield, double performanceRatio, double efficiency){
        this.numberOfPanels = numberOfPanels;
        this.panelArea = panelArea;
        this.solarPanelYield = solarPanelYield;
        this.performanceRatio = performanceRatio;
        this.efficiency = efficiency;
        this.solarPanelAngle = Math.PI / 2;
        checkIfParametersAreWrong();
    }

    public PhotovoltaicFarm(){
        System.out.println("PhotovoltaicFarm constructor called without parameters.");

        this.numberOfPanels = ThreadLocalRandom.current().nextInt(1,25);
        this.panelArea = ThreadLocalRandom.current().nextDouble(0.5,4);
        this.solarPanelYield = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        this.performanceRatio = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        this.solarPanelAngle = Math.PI / 2;// ThreadLocalRandom.current().nextDouble(-Math.PI /2, Math.PI /2); // betha angle measured in radians between earth surface and solar panel
        this.efficiency = 0.18;
        checkIfParametersAreWrong();

        System.out.printf("Class got random attributes 'numberOfPanels'=%d 'panelArea'=%g 'solarPanelYield'=%g 'performanceRatio'=%g%n", numberOfPanels, panelArea, solarPanelYield, performanceRatio);
    }

    private boolean isZero(double value, double threshold){
        return value >= -threshold && value <= threshold;
    }

    public double calculateEnergy(double timeDelta, double dirRadiation, double difRadiation) {
        double modulatingFunction = 1 - (isZero(difRadiation, 0.001) ?
                difRadiation : (difRadiation / (difRadiation + dirRadiation * Math.cos(angleZ))));
        double inclinedAverageSolarRadiationInGivenPeriodOfTime = difRadiation
                * Math.pow(Math.cos(solarPanelAngle / 2), 2)
                * (1 + modulatingFunction * Math.pow(Math.sin(solarPanelAngle / 2), 3))
                * (1 + modulatingFunction * Math.pow(Math.cos(angleOfIncidenceOfSunsRays), 2)
                * Math.pow(Math.sin(angleZ), 3));
        double powerInKiloWh = panelArea * numberOfPanels * inclinedAverageSolarRadiationInGivenPeriodOfTime * timeDelta * solarPanelYield * performanceRatio * efficiency;
        return 3600000 * powerInKiloWh; // power in W*sec = J
    }

    private void checkIfParametersAreWrong(){
        String errorString = "Arguments passed to PhotovoltaicFarm constructor were incorrect.";
        if(numberOfPanels < 0)                                  throw new IllegalArgumentException(errorString + " 'numberOfPanels' shouldn't be lesser than 0. Got '" + numberOfPanels + "'.");
        if(panelArea < 0)                                       throw new IllegalArgumentException(errorString + " 'panelArea' shouldn't be lesser than 0. Got '" + panelArea + "'.");
        if(solarPanelYield < 0.0 || solarPanelYield > 1.0)      throw new IllegalArgumentException(errorString + " 'solarPanelYield' should be between 0.0 and 1.0. Got '" + solarPanelYield + "'.");
        if(performanceRatio < 0.0 || performanceRatio > 1.0)    throw new IllegalArgumentException(errorString + " 'performanceRatio' should be between 0.0 and 1.0. Got '" + performanceRatio + "'.");
        if(solarPanelAngle < -Math.PI /2 || solarPanelAngle > Math.PI /2) throw new IllegalArgumentException(errorString + " 'solarPanelAngle' should be between -PI/2 and PI/2. Got '" + solarPanelAngle + "'.");
        if(efficiency < 0.0 || efficiency > 1.0)                throw new IllegalArgumentException(errorString + "'efficiency' should be between 0.0 and 1.0.");
    }
}
