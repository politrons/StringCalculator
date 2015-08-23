package impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pabloperezgarcia on 22/8/15.
 */
public class StringCalculator {

    private String defaultSplit = "\\,|\\n";

    private String pattern = "^(?:\\/\\/(.)*\\n(\\d+(?:(?:,|\\n)\\d+)*$))|^(\\d+(?:(?:,|\\n)\\d+)*$)";

    private static final String[] NO_NUMBERS = {};

    /**
     * Entry point where we receive the String numbers and we return the number sum.
     *
     * @param entryNumbers
     * @return
     * @throws NegativeNumberException
     */
    public int add(String entryNumbers) throws NegativeNumberException {
        calcCustomDelimiters(entryNumbers);
        List<String> numbersList = Stream.of(getNumbers(entryNumbers)).filter(x -> !x.isEmpty()).collect(Collectors.toList());
        String[] numbers = numbersList.toArray(new String[numbersList.size()]);
        return numbers.length == 0 ? 0 : sumNumbers(Stream.of(numbers).mapToInt(Integer::parseInt).toArray());
    }

    /**
     * Using regex we match the number string and in case the string match the pattern we use the defaultSplit to return the array of numbers
     *
     * @param number
     * @return
     */
    private String[] getNumbers(final String number) {
        Matcher matcher = Pattern.compile(pattern).matcher(number);
        if (matcher.find()) {
            String finalNumber = matcher.group(hasCustomDelimiters(number) ? 1 : 0);
            return finalNumber.split(defaultSplit);
        }
        return NO_NUMBERS;
    }

    /**
     * Using regex, we calc if the numbers has custom delimiters so we can extract the custom delimiters to be used in main regular expression.
     * Also we calc the split value to be used once we have the number string to separate the values
     *
     * @param number
     */
    public void calcCustomDelimiters(String number) {
        StringBuilder customDelimiters = new StringBuilder("[");
        StringBuilder customSplit = new StringBuilder();
        boolean delimitersFound = false;
        String separation = "";
        LinkedList<String> delimitersPatterns = new LinkedList<>(Arrays.asList("\\[(.*?)\\]", "^\\/\\/(.*?)\\n"));
        for (String delimiterPattern : delimitersPatterns) {
            Matcher matcher = Pattern.compile(delimiterPattern).matcher(number);
            while (matcher.find()) {
                delimitersFound = true;
                customDelimiters.append(matcher.group(1));
                customSplit.append(separation);
                customSplit.append("\\").append(matcher.group(1).replaceAll("^(.)+$", "$1"));
                separation = "|";
            }
            if (delimitersFound) {
                customDelimiters.append("]*");
                String delimiters = customDelimiters.toString();
                defaultSplit = customSplit.toString();
                this.pattern = "^(?:\\/\\/(?:.)*\\n(\\d+(?:" + delimiters + "+\\d+)*$))|^(\\d+(?:" + delimiters + "+\\d+)*$)";
                return;
            }
        }
    }


    private boolean hasCustomDelimiters(final String number) {
        return Pattern.compile("\\[(.*?)\\]").matcher(number).find() ||
                Pattern.compile("^\\/\\/(.*?)\\n").matcher(number).find();
    }

    /**
     * Using Stream first we check if there´s any negative number, and if it so we will throw an exception with negative numbers
     * Also if there´s no negative numbers, we sum all element of the array with the filter that element must not be higher than 1000
     *
     * @param numbers
     * @return
     */
    public int sumNumbers(int[] numbers) throws NegativeNumberException {
        int[] negativeNumbers = IntStream.of(numbers).filter(n -> n < 0).toArray();
        if (negativeNumbers.length > 0) {
            throw new NegativeNumberException(String.format("Negatives not allowed %s", Arrays.toString(negativeNumbers)));
        }
        return IntStream.of(numbers).filter(n -> n <= 1000).sum();
    }
}
