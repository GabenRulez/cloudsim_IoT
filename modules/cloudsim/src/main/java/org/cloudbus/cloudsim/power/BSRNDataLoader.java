package org.cloudbus.cloudsim.power;

import com.opencsv.CSVReader;

import javax.sound.sampled.Line;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;


public class BSRNDataLoader {

    private final LinkedHashMap<String, Float> energiesByDate;

    public BSRNDataLoader(String filename) throws IOException {
        energiesByDate = new LinkedHashMap<>();

        InputStream is = BSRNDataLoader.class.getResourceAsStream(filename);
        Reader reader = new InputStreamReader(is);


        CSVReader csvReader = new CSVReader(reader, '\t');

        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null && !nextLine[0].startsWith("Date")) {
            //skip those lines
        }

        int radiationDataIdx = 0;
        while(!nextLine[radiationDataIdx].equals("DIR [W/m**2]")) radiationDataIdx++;

        while((nextLine = csvReader.readNext()) != null){

            String radiationStr = nextLine[radiationDataIdx];
            float radiation = radiationStr.equals("") ? 0 : Float.parseFloat(nextLine[radiationDataIdx]);

            energiesByDate.put(nextLine[0], radiation);

        }
    }


    public BSRNDataLoader() throws IOException {
        this("BRB_radiation_2019-04.tab");
    }

    public LinkedHashMap<String, Float> getEnergiesByDate(){
        return energiesByDate;
    }
    public static void main(String[] args) throws IOException {
        new BSRNDataLoader();
//        String datasetFileName = args.length > 0 ? args[0] : "BRB_radiation_2019-04.tab";
//
//        InputStream fileInputStream = BSRNDataLoader.class.getResourceAsStream(datasetFileName);
//        System.out.println(fileInputStream);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//
//        bufferedReader
//            .lines()
//            .filter(line -> {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//                try {
//                    dateFormat.parse(line.split("\t")[0]);
//                    return true;
//                } catch (ParseException e) {
//                    return false;
//                }
//            })
//            .map(s -> {
//                String[] splitStrings = s.split("\t");
//                return Arrays.asList(splitStrings);
//            })
//            .forEach(System.out::println);
    }

}
