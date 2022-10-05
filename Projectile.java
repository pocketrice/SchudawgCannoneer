import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Projectile {
   final double ACCELERATION_Y = 980; // CM
    final double ACCELERATION_X = 0;
    static double deltaX;
    static double deltaY = 0;
    static double maxRange;
    static double initialVelocity;
    static int greenGuys = 3;
    static int victories = 0, losses = 0;
    static int roundCount = 1;
    static boolean isIterationOverrideEnabled = false;
    static boolean isVelocityOverrideEnabled = false;
    static boolean isFirstTimeAskingVelocity = true;

    static final double TABLE_HEIGHT = 84.3; // This probably shouldn't exist
    String cannonName = "5A"; // This probably also shouldn't exist

    public static final String ANSI_RESET = "\u001B[0m";
    public static final  String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static Scanner input = new Scanner(System.in);


    public static void main(String[] args) throws InterruptedException {
        final long startTime = System.nanoTime();
        Projectile cannon = new Projectile();

        cannon.print(ANSI_PURPLE + "Good day, soldier. Today is the day your physics training has all been for!");
        cannon.print("The following program will help you calibrate your Schudawg cannon for combat.");
        cannon.print("\t\t\t\t\t\t\t\t\t\t\t\t\t- The General\n\n" + ANSI_RESET);

        while (true)
        {
            isIterationOverrideEnabled = false;

        cannon.print("=================================================================================");
        cannon.print("\n\t\t\t\t\t\t\t\tBATTLE " + roundCount + "\n");
        cannon.print("=================================================================================\n\n");

        if (!isVelocityOverrideEnabled)
        {
        cannon.print(ANSI_CYAN + "To calculate cannon's max range, type in the initial velocity (in cm/s)." + ANSI_RESET);

        initialVelocity = cannon.prompt("v\u2080 = ", "Error: choice out of range.", 0, true, Double.POSITIVE_INFINITY, false, true);
        maxRange = cannon.calculateMaxRange(initialVelocity);
        cannon.fancyCalculation(500);
        System.out.printf(ANSI_GREEN + "\nSuccessfully calculated max range (%.3f cm)!\n\n", maxRange);

        if (isFirstTimeAskingVelocity) {
            if (cannon.prompt(ANSI_CYAN + "Do you want to keep initial velocity the same for all battles or retype each time?" + ANSI_RESET, "Error: invalid choice.", new String[]{"keep", "retype"}).matches("keep")) {
                isVelocityOverrideEnabled = true;
            }
            isFirstTimeAskingVelocity = false;
        }
            System.out.println(ANSI_BLUE + "\n\n==========================================================================================" + ANSI_RESET);
        }


        cannon.print(ANSI_BLUE + "To calculate desired cannon angle, type in the distance between the cannon and the opposition (in cm)." + ANSI_RESET);
        /*cannon.print(ANSI_PURPLE + "Alternatively, type \"INFO\" to see currently stored information." + ANSI_RESET);
        if (input.next().matches("INFO"))
        {
            cannon.print("Current info:\nMax range: " + maxRange + " cm");
        }*/

        /*while (true)
        {*/
            deltaX = cannon.prompt("∆x = ", "Error: choice out of range or is unreasonable.", 0, true, 1000, true, true);
            cannon.fancyCalculation(500);
            if (deltaX > maxRange)
            {
                System.out.println(ANSI_RED + "\nWARNING: your cannon cannot launch that far!!\n\n\n" + ANSI_RESET);
                isIterationOverrideEnabled = true;
            }

            if (!isIterationOverrideEnabled)
            {
                System.out.printf(ANSI_GREEN + "\nSuccessfully calculated optimal angle (%.3f degrees)!\n\n", cannon.calculateInitialAngle(deltaX, initialVelocity));
                String roundFeedback = cannon.prompt(ANSI_CYAN + "Was the attack a success or no dice? Perhaps even a stalemate? Alternatively type 'END_WAR' to end the game." + ANSI_RESET, "Error: invalid answer.", new String[]{"yes", "no", "stalemate", "END_WAR"});

                if (roundFeedback.matches("END_WAR"))
                {
                    final long timePlayed = System.nanoTime() - startTime;

                    cannon.print(ANSI_PURPLE + "\n\n\n==========================================================================================\n");
                    cannon.print("\nYou have fought valiantly in the name of physics. Now let's see how you did.\n");
                    cannon.print("Total rounds played: " + roundCount);
                    cannon.print("Total green guys: " + greenGuys);

                    cannon.print("\nTotal victories: " + victories);
                    cannon.print("Total losses: " + losses);
                    System.out.printf("You played for a total of %.2fs.", (float)timePlayed / 1000000000); // Yes, you really do need to divide by that much to get seconds.

                    cannon.print("\n\n\nThanks for playing!" + ANSI_RESET);
                    cannon.print(ANSI_PURPLE + "\n==========================================================================================");
                    System.exit(0);
                }
                else if (roundFeedback.matches("stalemate"))
                {
                    cannon.print(ANSI_RESET + "\n\nStalemate -- no Green Guys lost (" + greenGuys + " left)\n\n\n\n");
                }
                else if (roundFeedback.matches("yes"))
                {
                    greenGuys++;
                    victories++;
                    cannon.print(ANSI_GREEN + "\n\n+1 Green Guy (" + greenGuys + " left)\n\n\n\n" + ANSI_RESET);
                }
                else
                {
                    greenGuys--;
                    losses++;
                    cannon.print(ANSI_RED + "\n\n-1 Green Guy (" + greenGuys + " left)\n\n\n\n" + ANSI_RESET);
                }

                if (greenGuys == 0)
                {
                    System.out.println(ANSI_RED + "\n==========================================================================================\n");
                    System.out.println(ANSI_RED + "Oh no -- you have lost all of your army. Better luck next time...");
                    System.out.println(ANSI_RED + "\n==========================================================================================");
                    System.exit(0);
                }

                roundCount++;
            }

            /*if (deltaX < 100 || deltaX > 700)
            {
                System.out.println(ANSI_RED + "Warning: that ∆x value (" + deltaX + " cm) seems unreasonable. Reinput?");
                if (!input.nextBoolean())
                {
                    break;
                }
            }
            break;
        }*/
        }
    }

    public String prompt(String message, String errorMessage, String[] keywords)
    {
        while (true)
        {
            System.out.println(message);
            String nextInput = input.next();

            List<String> keywordsList = Arrays.asList(keywords); // Convert to list to allow for checking String[] for a keyword
            if (nextInput.matches(String.join("|",keywords)) || keywordsList.get(0) == "")
            {
                return nextInput;
            }
            else
            {
                System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
            }
        }
    }

    public double prompt(String message, String errorMessage, double min, boolean isMinInclusive, double max, boolean isMaxInclusive, boolean isNoLine /* Replace this for future use; this is me being lazy */)
    {
        while (true)
        {
            if (!(message.matches("NO_MESSAGE"))) {
                if (isNoLine) // REMOVE IF STATEMENT AND JUST KEEP PRINTLN LINE FOR FUTURE USE
                {
                    System.out.print(message);
                }
                else
                {
                    System.out.println(message);
                }
            }

            double nextInput = input.nextDouble();

            if (nextInput >= min && nextInput <= max)
            {
                if (!(!isMinInclusive && nextInput == min) || !(!isMaxInclusive && nextInput == max)) // Two extremities don't occur
                {
                    return nextInput;
                }
                else
                {
                    System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
                }
            }
            else
            {
                System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
            }
        }
    }

    public void print(String message)
    {
        System.out.println(message);
    }

    public void fancyCalculation(long delay) throws InterruptedException { // "Calculate" in a very cool way at the expense of wasting time unnecessarily. Nice
        int recursionCount = 0;
        System.out.print(ANSI_YELLOW + "\nCalculating... /");

        while (recursionCount < 2) {
            TimeUnit.MILLISECONDS.sleep(delay);
            System.out.print("\b\u2014");
            TimeUnit.MILLISECONDS.sleep(delay);
            System.out.print("\b\\");
            TimeUnit.MILLISECONDS.sleep(delay);
            System.out.print("\b|");
            TimeUnit.MILLISECONDS.sleep(delay);
            System.out.print("\b/");
            recursionCount++;
        }
        System.out.print("\b");
    }

    public double calculateMaxRange(double initialVelocity)
    {
        // v0 = sqrt(Rg/sin(2θ))
        // R = (v0)^2*sin(2θ)/g

        // NOTE FOR FUTURE: JAVA USES RAD, NOT DEG -- SO "PERFECT" ANGLE OF 45deg SHOULD BE CONVERTED!!!!
        return Math.pow(initialVelocity, 2) * (Math.sin(Math.PI / 2) / ACCELERATION_Y); // centimeters; θ = π/4

    }

    public double calculateInitialAngle(double deltaX, double initialVelocity)
    {
        // R = ((v0)^2*sin(2θ))/g
        // Rg = v0^2*sin(2θ)
        // Rg/v0^2 = sin(2θ)
        // sin^-1(Rg/v0^2) = 2θ
        // sin^-1(Rg/v0^2)/2 = θ
        return Math.toDegrees(Math.asin(deltaX * ACCELERATION_Y / Math.pow(initialVelocity, 2)) / 2);
    }
}
