package org.cloudbus.cloudsim.power;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class BSRNDataLoader {
    public static void main(String[] args) {
        String datasetFileName = args.length > 0 ? args[0] : "BRB_radiation_2019-04.tab";

        InputStream fileInputStream = BSRNDataLoader.class.getResourceAsStream(datasetFileName);
        System.out.println(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        bufferedReader
            .lines()
            .filter(line -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                try {
                    dateFormat.parse(line.split("\t")[0]);
                    return true;
                } catch (ParseException e) {
                    return false;
                }
            })
            .map(s -> {
                String[] splitStrings = s.split("\t");
                return Arrays.asList(splitStrings);
            })
            .forEach(System.out::println);
    }
}
