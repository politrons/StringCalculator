
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pabloperezgarcia on 22/8/15.
 */
public class StringCalculator {

    String defaultSplit = "\\,";

    String defaultDelimiters = "(?:([,.|\\n])";

    public int add(String entryNumbers) {
        entryNumbers = entryNumbers.replace("\n", ",");
        calcCustomDelimiters(entryNumbers);
        String[] numbers = getNumbers(entryNumbers);
        return numbers == null ? 0 : sumNumbers(Stream.of(numbers).mapToInt(Integer::parseInt).toArray());
    }

    private String[] getNumbers(final String number) {
        String pattern = "\\d+" + defaultDelimiters + "\\d+)*$";
        Matcher matcher = Pattern.compile(pattern).matcher(number);
        if (matcher.find()) {
            System.out.println("total number:" + matcher.group(0));
            String finalNumber = matcher.group(0);
            return finalNumber.split(defaultSplit);
        }
        return null;
    }

    public void calcCustomDelimiters(String number) {
        String customDelimitersPattern = "\\[(.*?)\\]";
        boolean delimiterFound = false;
        StringBuilder customDelimiters = new StringBuilder("(?:([");
        StringBuilder customSplit = new StringBuilder();
        String prefix = "";
        Matcher matcher = Pattern.compile(customDelimitersPattern).matcher(number);
        while (matcher.find()) {
            delimiterFound = true;
            customDelimiters.append(matcher.group(1));
            customSplit.append(prefix);
            customSplit.append("\\").append(matcher.group(1));
            prefix = "|";
        }
        customDelimiters.append("])");
        if (delimiterFound) {
            defaultDelimiters = customDelimiters.toString();
            defaultSplit = customSplit.toString();
        }
    }

    /**
     * Using Stream we sum all element of the array with the filter that element must not be higher than 1000
     *
     * @param numbers
     * @return
     */
    public int sumNumbers(int[] numbers) {
        return IntStream.of(numbers).filter(n -> n <= 1000).sum();
    }
}
