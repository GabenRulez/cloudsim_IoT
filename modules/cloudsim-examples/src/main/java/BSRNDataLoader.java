import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class BSRNDataLoader {
    public static void main(String[] args) {
        String datasetFileName = args[0];

        InputStream fileInputStream = BSRNDataLoader.class.getResourceAsStream(datasetFileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        bufferedReader
            .lines()
            .filter(line -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
