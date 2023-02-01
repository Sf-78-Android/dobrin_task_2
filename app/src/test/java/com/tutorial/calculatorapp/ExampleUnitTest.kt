package com.tutorial.calculatorapp

import com.tutorial.calculatorapp.constants.Constants
import com.tutorial.calculatorapp.model.Calculator
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val myCalculator = Calculator()
    private val calc1 = "2+2"
    private val calc2 = "199-188"
    private val calc3 = "4*4"
    private val calc4 = "81/9"
    private val calc5 = "81-81/9"
    private val calc6 = "2+2*2"
    private val calc7 = "0.5*0.5"
    private val calc8 = "7-9*2"
    private val calc9 = "7/7/7/7"
    private val calc10 = "9+9+9+"
    private val calc11 = "3!+3!"
    private val calc12 = "7-12/3!"
    private val calc13 = "2.5!"
    private val calc14 = "0!"
    private val calc15 = "${Constants.SQRT_SIGN}4+${Constants.SQRT_SIGN}4"
    private val calc16 = "${Constants.SQRT_SIGN}4-${Constants.SQRT_SIGN}4"
    private val calc17 = "${Constants.SQRT_SIGN}4/${Constants.SQRT_SIGN}4"
    private val calc18 = "${Constants.SQRT_SIGN}4!+${Constants.SQRT_SIGN}4!"
    private val calc19 = "${Constants.SQRT_SIGN}9!+${Constants.SQRT_SIGN}9!"


    @Test
    fun sqrtSqrtFactorialAddition2_isCorrect() {
        val result = myCalculator.onEqual(calc19)
        assert(result.equals(12.0))
    }
    @Test
    fun sqrtSqrtFactorialAddition_isCorrect() {
        val result = myCalculator.onEqual(calc18)
        assert(result.equals(4.0))
    }
    @Test
    fun sqrtDivision_isCorrect() {
        val result = myCalculator.onEqual(calc17)
        assert(result.equals(1.0))
    }

    @Test
    fun sqrtSubtraction_isCorrect() {
        val result = myCalculator.onEqual(calc16)
        assert(result.equals(0.0))
    }

    @Test
    fun addition_isCorrect() {
        val result = myCalculator.onEqual(calc15)
        assert(result.equals(4.0))
    }

    @Test
    fun subtraction_isCorrect() {
        val result = myCalculator.onEqual(calc2)
        assert(result.equals(11.0))
    }

    @Test
    fun multiplication_isCorrect() {
        val result = myCalculator.onEqual(calc3)
        assert(result.equals(16.0))
    }
@Test
    fun multiplicationWinDecimal_isCorrect() {
        val result = myCalculator.onEqual(calc7)
        assert(result.equals(0.25))
    }

    @Test
    fun division_isCorrect() {
        val result = myCalculator.onEqual(calc4)
        assert(result.equals(9.0))
    }
 @Test
    fun checkOperatorPriorityIsCorrect() {
        val result = myCalculator.onEqual(calc5)
        assert(result.equals(72.0))
    }
@Test
    fun checkCalculationWithOneTypeOfNumber() {
        val result = myCalculator.onEqual(calc6)
        assert(result.equals(6.0))
    }

@Test
    fun checkNegativeNumberOutput() {
        val result = myCalculator.onEqual(calc8)
        assert(result.equals(-11.0) )
    }
@Test
    fun checkDecimalOutput() {
        val result = myCalculator.onEqual(calc9)
        assert(result.equals(0.020408163265306)) { println(result.toString()) }

}
@Test
    fun removesSignAtEndAndCalculationIsCorrect() {
        val result = myCalculator.onEqual(calc10)
        assert(result.equals(27.0))
}
    @Test
    fun factorial_isCorrect() {
        val result = myCalculator.onEqual(calc11)
        assert(result.equals(12.0))
    }

 @Test
    fun factorialBeforeDivision_isCorrect() {
        val result = myCalculator.onEqual(calc12)
        assert(result.equals(5.0))
    }
@Test
    fun ignoreDecimal_isCorrect() {
        val result = myCalculator.onEqual(calc13)
        assert(result.equals(2.0))
    }

@Test
    fun factorialOfZeroReturnsZero_isCorrect() {
        val result = myCalculator.onEqual(calc14)
        assert(result.equals(0.0))
    }



}