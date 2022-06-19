package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutSolutionTest {
    private CheckoutSolution checkoutSolution;

    @Before
    public void setUp() {
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    public void checkValidScenario1() {
        assertThat(checkoutSolution.checkout("AAABB"), equalTo(175));
    }

    @Test
    public void checkValidScenario2() {
        assertThat(checkoutSolution.checkout("AABBCD"), equalTo(180));
    }

    @Test
    public void checkValidScenario3() {
        assertThat(checkoutSolution.checkout("AAABBCD"), equalTo(210));
    }

    @Test
    public void checkInvalidScenario1() {
        assertThat(checkoutSolution.checkout("FFFFxGH"), equalTo(-1));
    }

    @Test
    public void checkInvalidScenario2() {
        assertThat(checkoutSolution.checkout(""), equalTo(0));
    }

    @Test
    public void checkInvalidScenario2A() {
        assertThat(checkoutSolution.checkout(" "), equalTo(0));
    }

    @Test
    public void checkInvalidScenario3() {
        assertThat(checkoutSolution.checkout("AxA"), equalTo(-1));
    }

    @Test
    public void checkFreeItemScenario1() { assertThat(checkoutSolution.checkout("AAAAABBEE"), equalTo(310)); }

    @Test
    public void checkFreeItemScenario2() { assertThat(checkoutSolution.checkout("AAAAABBEEE"), equalTo(350)); }

    @Test
    public void checkFreeItemScenario3() { assertThat(checkoutSolution.checkout("AAAAABBEEEE"), equalTo(360)); }

    @Test
    public void discountScenario1() { assertThat(checkoutSolution.checkout("EEAAAAABBCDEE"), equalTo(395));}

    @Test
    public void discountScenario2() { assertThat(checkoutSolution.checkout("EEAAAAABBCDEEAAA"), equalTo(525));}

    @Test
    public void discountScenario3() { assertThat(checkoutSolution.checkout("AAAAABBCDEE"), equalTo(345));}

    @Test
    public void discountScenario4() { assertThat(checkoutSolution.checkout("AAAAABBEEEE"), equalTo(360));}

    @Test
    public void additionalDiscountScenario1(){assertThat(checkoutSolution.checkout("FF"), equalTo(20));}

    @Test
    public void additionalDiscountScenario2(){assertThat(checkoutSolution.checkout("FFF"), equalTo(20));}

    @Test
    public void additionalDiscountInvalidScenario3(){assertThat(checkoutSolution.checkout("FFFx"), equalTo(-1));}

    @Test
    public void additionalDiscountScenario3(){assertThat(checkoutSolution.checkout("FFFF"), equalTo(30));}

    @Test
    public void additionalDiscountScenario4(){assertThat(checkoutSolution.checkout("FFFFFF"), equalTo(40));}

    @Test
    public void additionalDiscountScenario5(){assertThat(checkoutSolution.checkout("FFFFFFF"), equalTo(50));}

    @Test
    public void additionalScenario1(){assertThat(checkoutSolution.checkout("RRRQQ"), equalTo(180));}

    @Test
    public void additionalScenario2(){assertThat(checkoutSolution.checkout("UUUU"), equalTo(120));}

    @Test
    public void additionalScenario3(){assertThat(checkoutSolution.checkout("VVVUUU"), equalTo(250));}

    @Test
    public void additionalScenario4(){assertThat(checkoutSolution.checkout("VVVUUU"), equalTo(250));}

    @Test
    public void additionalScenario5(){assertThat(checkoutSolution.checkout("MMMNNN"), equalTo(150));}

    @Test
    public void groupDiscountScenario1(){assertThat(checkoutSolution.checkout("MMMNNNSTX"), equalTo(195));}

    @Test
    public void groupDiscountScenario2(){assertThat(checkoutSolution.checkout("MMMNNNSTXSTX"), equalTo(252));}

    @Test
    public void groupDiscountScenario3(){assertThat(checkoutSolution.checkout("MMMNNNSTXAAA"), equalTo(325));}

    @Test
    public void groupDiscountScenario4(){assertThat(checkoutSolution.checkout("STXYXZSTXAAAEEB"), equalTo(345));}

    @Test
    public void groupDiscountScenario5(){assertThat(checkoutSolution.checkout("S"), equalTo(345));}

}



