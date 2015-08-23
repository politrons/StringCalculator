package impl;

import impl.exceptions.NegativeNumberException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * This Unit test will try to cover all possible scenarios for the inputs, and sum of those of String calculator
 * @author Pablo Perez Garcia
 */
public class StringCalculatorTest {


    @Test
    public void emptyStringReturn0() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("");
        assertThat(number, is(0));
    }

    @Test
    public void oneNumberComaSeparationReturn1() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1");
        assertThat(number, is(1));
    }

    @Test
    public void twoNumbersComaSeparationReturn3() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1,2");
        assertThat(number, is(3));
    }

    @Test
    public void fiveNumbersComaSeparationReturn15() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1,2,3,4,5");
        assertThat(number, is(15));
    }

    @Test
    public void twoNumbersComaAndNewLineSeparationReturn6() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1\n2,3");
        assertThat(number, is(6));
    }

    @Test
    public void threeNumbersWithOneCustomDelimitersSeparationReturn6() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("//[%]\n1%2%3");
        assertThat(number, is(6));
    }

    @Test
    public void threeNumbersWithTwoCustomDelimitersSeparationReturn6() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("//[*][%]\n1*2%3");
        assertThat(number, is(6));
    }

    @Test
    public void threeNumbersWithTwoCustomDelimitersSeparationReturn5() throws NegativeNumberException {
        StringCalculator stringCalculator = new StringCalculator();
        int number = stringCalculator.add("//[***][$$$]\n1***2$$$2");
        assertThat(number, is(5));
    }

        @Test
    public void fourNumbersWithTwoCustomDelimitersSeparationAndOneNotDefinedReturn0() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("//[*][%]\n1*2%3&4");
        assertThat(number, is(0));
    }


    @Test
    public void threeNumbersWithOneCustomDelimiterWithoutBracketsSeparationReturn6() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("//%\n1%2%3");
        assertThat(number, is(6));
    }

    @Test
    public void oneNumbersWithWrongEndingReturn0() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1,\n");
        assertThat(number, is(0));
    }

    @Test
    public void threeNumbersWithWrongSeparationsReturn0() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1,\n2,3");
        assertThat(number, is(0));
    }

    @Test
    public void sumOnlyLowerThan1000Numbers() throws NegativeNumberException {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        int number = stringCalculator.add("1,2,3,1003\n1002,1000");
        assertThat(number, is(1006));
    }

    @Test
    public void exceptionNumbersLowerThan0() {
        impl.StringCalculator stringCalculator = new impl.StringCalculator();
        try {
            stringCalculator.add("1, -2, 3, 1003, 1002\n-1000");
        } catch (NegativeNumberException e) {
            assertTrue(e.getMessage().equals("Negatives not allowed [-2, -1000]"));
        }
    }


}
