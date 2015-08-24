package impl;

import impl.exceptions.NegativeNumberException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pabloperezgarcia on 22/8/15.
 */
public class StringCalculator {

    public static final String MULTI_DELIMITER = "\\[(.*?)\\]";
    public static final String SINGLE_DELIMITER = "^\\/\\/(.*?)\\n";
    public static final String CUSTOM_PATTERN = "^(?:\\/\\/(?:.)*\\n(-?\\d+(?:delimiter+-?\\d+)*$))|^(-?\\d+(?:delimiter+-?\\d+)*$)";
    public static final String DELIMITER = "delimiter";
    private static final int[] NO_NUMBERS = {};

    private String defaultSplit = "\\,|\\n";
    private String defaultPattern = "^(?:\\/\\/(.)*\\n(-?\\d+(?:(?:,|\\n)-?\\d+)*$))|^(-?\\d+(?:(?:,|\\n)-?\\d+)*$)";

    /**
     * Entry point where we receive the String numbers and we return the number sum.
     *
     * @param entryNumbers
     * @return
     * @throws impl.exceptions.NegativeNumberException
     */
    public int add(String entryNumbers) throws NegativeNumberException {
        calcCustomDelimiters(entryNumbers);
        return sumNumbers(entryNumbers);
    }

    /**
     * Using Stream first we check if there´s any negative number, and if it so we will throw an exception with negative numbers
     * Also if there´s no negative numbers, we sum all element of the array with the filter that element must not be higher than 1000
     *
     * @param entryNumbers
     * @return
     */
    private int sumNumbers(String entryNumbers) throws NegativeNumberException {
        int[] numbers;
        return IntStream.of(numbers = getNumbers(entryNumbers))
                .peek(n -> {
                    if (n < 0) throw new NegativeNumberException(String.format("Negatives not allowed %s",
                            Arrays.toString(IntStream.of(numbers).filter(k -> k < 0).toArray())));
                }).filter(n -> n <= 1000).sum();
    }

    /**
     * Using regex we match the number string and in case the string match the defaultPattern we use the defaultSplit to return the array of numbers
     *
     * @param numbers
     * @return
     */
    private int[] getNumbers(String numbers) {
        Matcher matcher = Pattern.compile(defaultPattern).matcher(numbers);
        if (matcher.find()) {
            numbers = matcher.group(hasCustomDelimiters(numbers) ? 1 : 0);
            return Stream.of(numbers.split(defaultSplit)).mapToInt(Integer::parseInt).toArray();
        }
        return NO_NUMBERS;
    }

    /**
     * Using regex, we calc if the numbers has custom delimiters so we can extract the custom delimiters to be used in main regular expression.
     * Also we calc the split value to be used once we have the number string to separate the values
     *
     * @param number
     */
    private void calcCustomDelimiters(String number) {
        StringBuilder customDelimiters = new StringBuilder("[");
        StringBuilder customSplit = new StringBuilder("[");
        boolean delimitersFound = false;
        for (String delimiterPattern : new LinkedList<>(Arrays.asList(MULTI_DELIMITER, SINGLE_DELIMITER))) {
            Matcher matcher = Pattern.compile(delimiterPattern).matcher(number);
            while (matcher.find()) {
                delimitersFound = true;
                customDelimiters.append(matcher.group(1));
                customSplit.append(matcher.group(1));
            }
            if (delimitersFound) {
                customDelimiters.append("]*");
                customSplit.append("]+");
                updateDefaultSplitAndPattern(customDelimiters, customSplit);
                return;
            }
        }
    }

    private void updateDefaultSplitAndPattern(final StringBuilder customDelimiters, final StringBuilder customSplit) {
        defaultSplit = customSplit.toString();
        defaultPattern = CUSTOM_PATTERN.replace(DELIMITER, customDelimiters.toString());
    }

    /**
     * Check if number has custom delimiters
     *
     * @param number
     * @return
     */
    private boolean hasCustomDelimiters(final String number) {
        return Pattern.compile("\\[(.*?)\\]").matcher(number).find() ||
                Pattern.compile("^\\/\\/(.*?)\\n").matcher(number).find();
    }

}
