package cat.udl.eps.softarch.mytournamentx.utils;

/**
 * This class contains math utils functions
 */
public class MathUtils {

    /**
     * This function returns the factorial of a given number
     * @param num
     * @return result of the factorial
     */
    public static int factorial(int num) throws Exception {
        if(num < 0)
            throw new Exception("You fucked up!");
        int res = 1;
        for(int i = num; i > 0; i--) {
            res *= i;
        }
        return res;
    }

    /**
     * Returns the floor of two integers in integer format
     * @param n1
     * @param n2
     * @return floor of two numbers
     */
    public static int intDivisonFloor(float n1, float n2){
        return (int)Math.floor(n1 /n2);
    }

    /**
     * Returns the top of two integers in integer format
     * @param n1
     * @param n2
     * @return top of two numbers
     */
    public static int intDivisonTop(float n1, float n2){
        return intDivisonFloor(n1 + 1, n2);
    }
}
