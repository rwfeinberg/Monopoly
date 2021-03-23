package ooga.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ooga.exceptions.FilePathException;
import ooga.model.Property;


public class PropertyFactory implements PropertyReader {

  private final String DATA_DIRECTORY = "./data/";
  private final String FILE_EXTENSION = ".csv";
  private final int ITEMS_PER_LINE = 3;
  private final String[] DEFAULT_VALUES = {"Property","WHITE","-1"};

  private String pathToTiles;
  private List<Property> allProperties;
  private List<String> propertyNames;

  public PropertyFactory(String pathToTiles) {
    this.pathToTiles = DATA_DIRECTORY + pathToTiles + FILE_EXTENSION;
    propertyNames = new ArrayList<>();
    readGameProperties();
  }

  private void readGameProperties() {
    allProperties = new ArrayList<>();
    try (CSVReader csvReader = new CSVReader(new FileReader(pathToTiles))) {
      String[] nextLine = csvReader.readNext();
      while (nextLine  != null) {
        if (nextLine.length != ITEMS_PER_LINE) nextLine = DEFAULT_VALUES;
        allProperties.add(handleLine(nextLine));
        nextLine = csvReader.readNext();
      }
    } catch (CsvValidationException | IOException e) {
      throw new FilePathException("Bad path to file", e);
    }

  }

  private Property handleLine(String[] nextLine) {
    String name = nextLine[0];
    propertyNames.add(name);
    String color = nextLine[1];
    int startingRent = Integer.parseInt(nextLine[2]);
    return createProperty(name,color,startingRent);
  }

  private Property createProperty(String name, String color, int initialRent){
    return new Property(name,color,initialRent);
  }

  public List<String> getPropertyNames(){
    if (propertyNames !=null){
      return propertyNames;
    }
    else{
      return Collections.emptyList();
    }
  }


  @Override
  public List<Property> getProperties() {
    if (allProperties != null) {
      return allProperties;
    } else {
      return Collections.emptyList();
    }
  }




}
