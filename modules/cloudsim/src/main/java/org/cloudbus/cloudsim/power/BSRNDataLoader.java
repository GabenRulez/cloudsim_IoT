package org.cloudbus.cloudsim.power;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.LinkedHashMap;


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
            float dirRadiation = dirRadiationStr.equals("") ? 0 : Float.parseFloat(nextLine[dirRadiationDataIdx]);
            float difRadiation = difRadiationStr.equals("") ? 0 : Float.parseFloat(nextLine[difRadiationDataIdx]);

            dirRadiationsByDate.put(nextLine[0], dirRadiation);
            difRadiationsByDate.put(nextLine[0], difRadiation);
        }
    }


    public BSRNDataLoader() throws IOException {
        this("BRB_radiation_2019-04.tab");
    }

    public LinkedHashMap<String, Float> getDirRadiationsByDate(){
        return dirRadiationsByDate;
    }

    public LinkedHashMap<String, Float> getDifRadiationsByDate() {
        return difRadiationsByDate;
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
