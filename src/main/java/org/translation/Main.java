package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    private static final String QUIT = "quit";
    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        Translator translator = new JSONTranslator(null);
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {

        CountryCodeConverter countryConverter = new CountryCodeConverter();
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();

        while (true) {
            String country = promptForCountry(translator);
            if (QUIT.equals(country)) {
                break;
            }
            String countryCode = countryConverter.fromCountryCode(country);
            String language = promptForLanguage(translator, country);
            if (QUIT.equals(language)) {
                break;
            }
            String languageCode = languageConverter.fromLanguageCode(language);
            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            String textTyped = INPUT_SCANNER.nextLine();

            if ("quit".equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        CountryCodeConverter countryConverter = new CountryCodeConverter();

        List<String> countryNames = new ArrayList<>();
        for (String code : countries) {
            countryNames.add(countryConverter.fromCountryCode(code));
        }

        Collections.sort(countryNames);

        for (String name : countryNames) {
            System.out.println(name);
        }

        System.out.println("select a country from above:");

        return INPUT_SCANNER.nextLine();
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> languageCodes = translator.getCountryLanguages(country);
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();

        List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            languageNames.add(languageConverter.fromLanguageCode(code));
        }

        Collections.sort(languageNames);

        for (String name : languageNames) {
            System.out.println(name);
        }

        System.out.println("select a language from above:");

        return INPUT_SCANNER.nextLine();
    }
}
