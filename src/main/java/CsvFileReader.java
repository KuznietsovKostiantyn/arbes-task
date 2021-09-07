import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvFileReader {

    public void parseFile(String fileName){

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            TelephoneBillCalculator telephoneBillCalculator = new TelephoneBillCalculatorImpl();
            while ((line = br.readLine()) != null)
            {
                telephoneBillCalculator.calculate(line);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
