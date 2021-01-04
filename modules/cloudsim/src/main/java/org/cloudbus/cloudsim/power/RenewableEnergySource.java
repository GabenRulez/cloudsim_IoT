package org.cloudbus.cloudsim.power;

public class RenewableEnergySource {
    /*
        TODO:
        - get data from BSRNDataLoader and apply it to the PhotovoltaicFarm
        - set start date (equivalent of 0 timestamp, this is arbitrary choice made by us)
        - implement getEnergyInTimeframe function.
        Remember that data from BSRNDataLoader is in minute intervals, and timestamps are seconds.
        For example, if currentTime = 90 and timeDiff = 60, we need to get half energy from first minute and half energy from second minute.
        If currentTime = 180 and timeDiff = 60, we just need to get whole energy from third minute.
    */

    /**
     * Gets the energy in given timeframe.
     *
     * @param currentTime current timestamp [s]
     * @param timeDiff interval length [s]
     * @return energy in [currentTime - timeDiff, currentTime] interval
     */
    double getEnergyInTimeframe(double currentTime, double timeDiff) {
        return 0.0;
    }
}
