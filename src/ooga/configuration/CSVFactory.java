package ooga.configuration;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The purpose of this class is to serve as factory that delivers a list of list of strings where
 * each inner list corresponds to a line that is read in file that an instantiation of this class
 * is being directed towards.
 * @author wyattfocht
 */
public class CSVFactory {

  private final String[] EXTENSIONS = {".zip", ".csv"};

  private String filePath;
  private List<List<String>> allLinesOfCSV;
  private boolean successfulRead;

  /**
   * This is a constructor for a CSVFactory object.
   * @param path String representing the direct path to the file that is to be read, excluding the 
   *             file extension of the file itself
   */
  public CSVFactory(String path) {
    this.filePath = path;
    successfulRead = false;
    readCSV();
  }

  private void readCSV() {
    allLinesOfCSV = new ArrayList<>();
    CSVReader csvReader = null;
    for (int i = 0; i < EXTENSIONS.length; i++) {
      if (i == 0) {
        try {
          ZipFile zip = new ZipFile(filePath + EXTENSIONS[i]);
          for (ZipEntry entry : Collections.list(zip.entries())) {
            csvReader = new CSVReader(new InputStreamReader(zip.getInputStream(entry)));
          }
        } catch (IOException e) {
          continue;
        }
      } else {
        try {
          csvReader = new CSVReader(new FileReader(filePath + EXTENSIONS[1]));
        } catch (IOException e) {
          //do nothing
        }
      }
      if (csvReader != null) {
        readPropertiesFromCSV(csvReader);
      }
    }
  }

  private void readPropertiesFromCSV(CSVReader csvReader) {
    try {
      String[] nextLine = csvReader.readNext();
      while (nextLine != null) {
        allLinesOfCSV.add(Arrays.asList(nextLine));
        nextLine = csvReader.readNext();
      }
    } catch (CsvValidationException | IOException e) {
      successfulRead = false;
    }
  }

  /**
   * This method returns a comprehensive list containing all the lines of the file that is being
   * read by CSVFactory
   * @return a List of Lists of Strings where each inner list represents a singular line within the
   * original csv (or zip of csv) that is being read
   */
  public List<List<String>> getLinesOfCSV() {
    if (allLinesOfCSV != null) {
      return allLinesOfCSV;
    } else {
      return Collections.emptyList();
    }
  }
  /**
   * @return boolean representing whether the read of the file was successful (able to be parsed
   * with no exceptions)
   */
  public boolean isSuccessfulRead() {
    return successfulRead;
  }


}
