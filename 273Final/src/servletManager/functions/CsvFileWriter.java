package servletManager.functions;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Spurthy on 5/13/2015.
 */
public class CsvFileWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";




    public static void writeCsvFile(String ht1,String ht2, String fromDate,String toDate, String email) {



        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("C:\\SJSU\\SecondSem\\283\\Project2\\273Final\\dataToS3.csv");
                fileWriter.append(ht1);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(ht2);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(fromDate);
                fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(toDate);
            fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(email);
              System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }
}