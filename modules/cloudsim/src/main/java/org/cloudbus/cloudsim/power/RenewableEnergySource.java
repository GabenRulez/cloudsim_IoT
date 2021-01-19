package org.cloudbus.cloudsim.power;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;



public class RenewableEnergySource {

    static DateTimeFormatter DATE_TIME_FORMATTER_NO_SECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    BSRNDataLoader bsrnDataLoader;

    LinkedHashMap<String, Float> dirRadiationsByDate;
    List<String> dirKeys;
    List<Float> dirValues;
    LinkedHashMap<String, Float> difRadiationsByDate;
    List<String> difKeys;
    List<Float> difValues;

    protected PhotovoltaicFarm photovoltaicFarm;

    public RenewableEnergySource(PhotovoltaicFarm photovoltaicFarm, BSRNDataLoader bsrnDataLoader){
        this.photovoltaicFarm = photovoltaicFarm;
        this.bsrnDataLoader = bsrnDataLoader;
        dirRadiationsByDate = bsrnDataLoader.getDirRadiationsByDate();
        dirKeys = new ArrayList<>(dirRadiationsByDate.keySet());
        dirValues = new ArrayList<>(dirRadiationsByDate.values());
        difRadiationsByDate = bsrnDataLoader.getDifRadiationsByDate();
        difKeys = new ArrayList<>(difRadiationsByDate.keySet());
        difValues = new ArrayList<>(difRadiationsByDate.values());
    }
    /*
        DONE:
        - get data from BSRNDataLoader and apply it to the PhotovoltaicFarm (done)
        - set start date (equivalent of 0 timestamp, this is arbitrary choice made by us) (done)
        - implement getEnergyInTimeFrame function (done).
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
    double getEnergyInTimeFrame(double currentTime, double timeDiff) {
        double start = currentTime - timeDiff;
        String startKey = getStartKey((int) Math.floor(start));
        String endKey = getEndKey((int) Math.ceil(currentTime));
        System.out.println(startKey);
        System.out.println(endKey);
        double dirRoundedUpSum = getRadiationRoundedUpSum(dirValues, dirKeys, startKey, endKey);
        double difRoundedUpSum = getRadiationRoundedUpSum(difValues, difKeys, startKey, endKey);
        double dirStartEnergyDelta = computeStartEnergyDelta(dirRadiationsByDate, startKey, start);
        double difStartEnergyDelta = computeStartEnergyDelta(difRadiationsByDate, startKey, start);
        double dirEndEnergyDelta = computeEndEnergyDelta(dirRadiationsByDate, endKey, currentTime);
        double difEndEnergyDelta = computeEndEnergyDelta(difRadiationsByDate, endKey, currentTime);
        double dirRadiation = dirRoundedUpSum - dirStartEnergyDelta - dirEndEnergyDelta;
        double difRadiation = difRoundedUpSum - difStartEnergyDelta - difEndEnergyDelta;
//        System.out.println("timeDiff: " + timeDiff + " dirRadiation: " + dirRadiation + " difRadiation: " + difRadiation);
        return photovoltaicFarm.calculateEnergy(timeDiff, dirRadiation, difRadiation);
    }

    private double getRadiationRoundedUpSum(List<Float> values, List<String> keys, String startKey,
                                                   String endKey) {
        return values.subList(keys.indexOf(startKey), keys.indexOf(endKey) + 1).stream()
                .mapToDouble(Float::doubleValue)
                .sum();
    }

    private double computeEndEnergyDelta(LinkedHashMap<String, Float> radiationsBydDate, String endKey,
                                                double end) {
        float energy = radiationsBydDate.get(endKey);
        double deltaTime = 60 - (end - (Math.floor(end / 60.0) * 60.0));
//        System.out.println(deltaTime);
        return energy * (deltaTime / 60.0);
    }

    private double computeStartEnergyDelta(LinkedHashMap<String, Float> radiationsBydDate, String startKey,
                                                  double start) {
        float energy = radiationsBydDate.get(startKey);
        double deltaTime = start - (Math.floor(start / 60.0) * 60.0);
//        System.out.println(deltaTime);
        return energy * (deltaTime / 60.0);
    }

    private String getStartKey(int start) {
        LocalDateTime timeStamp = getTimestamp(start);
        timeStamp = timeStamp.withSecond(0);
        return timeStamp.format(DATE_TIME_FORMATTER_NO_SECONDS);
    }

    private String getEndKey(int end) {
        LocalDateTime timeStamp = getTimestamp(end);
        timeStamp = timeStamp.plusMinutes(1).withSecond(0);
        return timeStamp.format(DATE_TIME_FORMATTER_NO_SECONDS);
    }

    private LocalDateTime getTimestamp(int start) {
        LocalDateTime startDateTime = LocalDateTime.parse(dirKeys.iterator().next(), DATE_TIME_FORMATTER_NO_SECONDS);
        return startDateTime.plusSeconds(start);
    }

    // below method has been created only for test purposes
//    public static void main(String[] args) {
//        RenewableEnergySource renewableEnergySource = new RenewableEnergySource();
//        double currentTime = 2400.01;
//        double timeDiff = 300.0;
//        double radiation = renewableEnergySource.getEnergyInTimeFrame(currentTime, timeDiff);
//        double result = photovoltaicFarm.calculateEnergy(timeDiff, radiation);
//        System.out.println(result);
//    }
}
