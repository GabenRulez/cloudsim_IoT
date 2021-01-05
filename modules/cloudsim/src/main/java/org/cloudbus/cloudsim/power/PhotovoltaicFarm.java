package org.cloudbus.cloudsim.power;

public class PhotovoltaicFarm {

    public double calculateEnergy(double timeDelta, double radiation) {
        // In order to get energy produced by solar panels we used following equation: E = A x r x H x PR
        // where:
        int numberOfPanels = 10;
        // A - total panel area [m^2]
        int panelArea = 10;
        // r - solar panel yield [%]
        double solarPanelYield = 0.8;
        // H assumption: instead of annual average solar radiation on tilted panel average solar radiation
        // in given period of time was used
        // PR - performance ratio, constant for losses (range between 0.5 and 0.9, default value = 0.75)
        double performanceRatio = 0.75;

        double averageSolarRadiationInGivenPeriodOfTime = radiation / timeDelta;
        double powerInKiloWh = panelArea * numberOfPanels * solarPanelYield * averageSolarRadiationInGivenPeriodOfTime * performanceRatio;
        return 3600000 * powerInKiloWh; // power in W*sec = J
    }


}
