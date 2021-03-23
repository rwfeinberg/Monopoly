package ooga.configuration;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ooga.exceptions.FilePathException;

public class CSVLoader {

  private final String DATA_DIRECTORY = "./resources/";
  private final String FILE_EXTENSION = ".csv";

  private String pathToFile;
  private List<String> allValues;

  public CSVLoader(String path, String language) {
    this.pathToFile = DATA_DIRECTORY + path + language + FILE_EXTENSION;
    allValues = new ArrayList<>();
    readGameProperties();
  }

  private void readGameProperties() {
    try (CSVReader csvReader = new CSVReader(new FileReader(pathToFile))) {
      String[] nextLine = csvReader.readNext();
      while (nextLine != null) {
        allValues.add(String.join(" ", nextLine));
        nextLine = csvReader.readNext();
      }
    } catch (CsvValidationException | IOException e) {
      throw new FilePathException("Bad path to file", e);
    }

  }

  public List<String> getValues() {
    return allValues;
  }


}
