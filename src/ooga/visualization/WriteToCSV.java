package ooga.visualization;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The WriteToCSV.java class is used to write whatever the programmer needs in one short line.
 * <p>
 * Assumptions - The file is in the data directory.
 * <p>
 * Dependencies - This class depends on Java's classes.
 */
public class WriteToCSV {

  private final String DATA_DIRECTORY = "./resources/";
  private final String FILE_EXTENSION = ".csv";

  private String pathToFile;

  /**
   * Constructor, finds the file and writes data to it.
   *
   * @param filename - name of the file to write to
   * @param data - A list of every line to be written to the file
   */
  public WriteToCSV(String filename, List<String[]> data) {
    try {
      FileWriter fileOut = new FileWriter(new File(DATA_DIRECTORY + filename + FILE_EXTENSION));
      CSVWriter writer = new CSVWriter(fileOut, ';',
          CSVWriter.NO_QUOTE_CHARACTER,
          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
          CSVWriter.DEFAULT_LINE_END);
      System.out.println(data.get(0)[0]);
      writer.writeAll(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
