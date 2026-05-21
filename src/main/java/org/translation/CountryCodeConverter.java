package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
// ATA: Changed the alphabetical order of imports.

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private final Map<String, String> codeToCountry;
    private final Map<String, String> countryToCode;
    // ATA: added instance variables

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources' folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource(filename)).toURI()));
            codeToCountry = new HashMap<>();
            countryToCode = new HashMap<>();

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\t");

                String countryName = parts[0].trim();
                String countryCode = parts[2].trim();

                codeToCountry.put(countryCode, countryName);
                countryToCode.put(countryName, countryCode);
                // ATA: added csv info into instance variables codeToCountry, countryToCode.
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // ATA: added upper/lower case sensitivity.
        return codeToCountry.get(code.toUpperCase());
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // ATA: used the instance variable created early to get country.
        return countryToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // ATA: used the instance variable created early to get size.
        return codeToCountry.size();
    }
}
