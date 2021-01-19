package org.cloudbus.cloudsim.power;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * A class responsible for loading and parsing data from BSRN datasets. Stores radiation parameters in hashmaps.
 */

public class BSRNDataLoader {

    private final LinkedHashMap<String, Float> dirRadiationsByDate;
    private final LinkedHashMap<String, Float> difRadiationsByDate;

    public BSRNDataLoader(String filename) throws IOException {
        dirRadiationsByDate = new LinkedHashMap<>();
        difRadiationsByDate = new LinkedHashMap<>();

        InputStream is = BSRNDataLoader.class.getResourceAsStream(filename);
        Reader reader = new InputStreamReader(is);

        CSVReader csvReader = new CSVReader(reader, '\t');

        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null && !nextLine[0].startsWith("Date")) {
            //skip commented lines
        }

        int dirRadiationDataIdx = 0;
        while(!nextLine[dirRadiationDataIdx].equals("DIR [W/m**2]")) dirRadiationDataIdx++;

        int difRadiationDataIdx = 0;
        while(!nextLine[difRadiationDataIdx].equals("DIF [W/m**2]")) difRadiationDataIdx++;

        while((nextLine = csvReader.readNext()) != null){

            String dirRadiationStr = nextLine[dirRadiationDataIdx];
            String difRadiationStr = nextLine[difRadiationDataIdx];
            float dirRadiation = dirRadiationStr.equals("") ? 0 : Math.max(0, Float.parseFloat(nextLine[dirRadiationDataIdx]));
            float difRadiation = difRadiationStr.equals("") ? 0 : Math.max(0, Float.parseFloat(nextLine[difRadiationDataIdx]));

            dirRadiationsByDate.put(nextLine[0], dirRadiation);
            difRadiationsByDate.put(nextLine[0], difRadiation);
        }
    }


    public BSRNDataLoader() throws IOException {
        this("BUD_radiation_2020-07.tab");
    }

    public LinkedHashMap<String, Float> getDirRadiationsByDate(){
        return dirRadiationsByDate;
    }

    public LinkedHashMap<String, Float> getDifRadiationsByDate() {
        return difRadiationsByDate;
    }


}
