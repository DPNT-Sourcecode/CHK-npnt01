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
    public void checkInvalidScenario() {
        assertThat(checkoutSolution.checkout("FFFFGH"), equalTo(-1));
    }
}
