import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Pablo Perez Garcia
 */
public class StringCalculatorTest {


    @Test
    public void emptyStringReturn0() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("");
        assertThat(number, is(0));
    }

        @Test
    public void treeNumbersComaSeparationReturn6() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("1,2,3");
        assertThat(number, is(6));
    }

    @Test
    public void oneNumberComaSeparationReturn1() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("1");
        assertThat(number, is(1));
    }

    @Test
    public void twoNumbersComaSeparationReturn3() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("1,2");
        assertThat(number, is(3));
    }

    @Test
    public void twoNumbersComaAndNewLineSeparationReturn6() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("1\n2,3");
        assertThat(number, is(6));
    }

    @Test
    public void threeNumbersWithTwoCustomDelimitersSeparationReturn6() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("//[*][%]\n1*2%3");
        assertThat(number, is(6));
    }

    @Test
    public void oneNumbersWithWrongEndingReturn0() {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("1,\\n");
        assertThat(number, is(0));
    }

    @Test
    public void sumOnlyLowerThan1000Numbers() {
        StringCalculator stringCalculator = new StringCalculator();
        int[] numbers = {1, 2, 3, 1003, 1002, 1000};
        int number = stringCalculator.sumNumbers(numbers);
        assertThat(number, is(1006));
    }


}
