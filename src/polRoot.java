import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
Welcome to Polynomial root finder porgram.
@author Mostafa Vahidi
 */
public class polRoot {

    private int maxIt = 10000;
    private float pointA;
    private float pointB;
    private String fileName;
    private double eps = Math.pow(2.0, -23.0);
    private double delta = Math.ulp(0.0);
    private float root;
    private int numIterations = 0;


    public static void main(String[] args) throws Exception {
        polRoot polRoot = new polRoot();
        polRoot.commandHandler(args);
    }

    /*
    UserChoice error code meaning:
    1: -newt
    2: -sec
    3: -bisection
    4: First param is not right
    5: -newt params are not right
    6: -sec params are not right
    7: -bisection params are not right
    8: -Hybrid
    9: -Hybrid params are not right
     */
    /*
    Command handler method for taking in command line arguments.
     */
    public void commandHandler(String[] args) throws IOException {
        int userChoice = 4;
        try {
            if (args[0].contains("-newt")) {
                System.out.println("solving through Newton method!");
                if (args.length == 4) {
                    try {
                        maxIt = Integer.parseInt(args[1]);
                        pointA = Float.parseFloat((args[2]));
                        fileName = args[3];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 1;
                } else if (args.length == 3) {
                    System.out.println("Newt 3 args length reached");
                    try {
                        pointA = Float.parseFloat((args[1]));
                        fileName = args[2];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 1;
                } else {
                    System.out.println("Newt 3 args length reached");
                    userChoice = 5;
                }
                System.out.println("Nothing was reached!!!!!!!!!!!!!!!!!!!");
                userChoice = 1;
            } else if (args[0].contains("-sec")) {
                System.out.println("solving through Secant method!");
                if (args.length == 5) {
                    try {
                        maxIt = Integer.parseInt(args[1]);
                        pointA = Float.parseFloat((args[2]));
                        pointB = Float.parseFloat((args[3]));
                        fileName = args[4];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 2;
                } else if (args.length == 4) {
                    try {
                        pointA = Float.parseFloat((args[1]));
                        pointB = Float.parseFloat((args[2]));
                        fileName = args[3];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 2;
                } else {
                    userChoice = 6;
                }
            } else if (args[0].contains("-hybrid")){
                System.out.println("Solving through hybrid method!");
                if (args.length == 5) {
                    try {
                        maxIt = Integer.parseInt(args[1]);
                        pointA = Float.parseFloat((args[2]));
                        pointB = Float.parseFloat((args[3]));
                        fileName = args[4];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 8;
                } else if (args.length == 4) {
                    try {
                        pointA = Float.parseFloat((args[1]));
                        pointB = Float.parseFloat((args[2]));
                        fileName = args[3];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 8;
                } else {
                    userChoice = 9;
                }
            } else {
                System.out.println("solving through Bisection method!");
                if (args.length == 4) {
                    try {
                        maxIt = Integer.parseInt(args[0]);
                        pointA = Float.parseFloat((args[1]));
                        pointB = Float.parseFloat((args[2]));
                        fileName = args[3];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 3;
                } else if (args.length == 3) {
                    try {
                        pointA = Float.parseFloat((args[0]));
                        pointB = Float.parseFloat((args[1]));
                        fileName = args[2];
                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.println("You did not enter enough params for the program to run.");
                    }
                    userChoice = 3;
                } else {
                    userChoice = 7;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You did not enter enough params for the program to run.");
        }

        System.out.println(fileName);
        File toProcess = new File(fileName);
        processFile(toProcess, userChoice);

    }

    /*
    Processing the input .pol file and then sending found vectors into correct methods for finding root.
     */
    public void processFile(File toProcess, int userChoice) throws IOException {
        Scanner fileScanner = new Scanner(toProcess);
        int polArrSize = fileScanner.nextInt() + 1;
        float[] polArr = new float[polArrSize];

        int polArrIterator = 0;
        while (fileScanner.hasNextFloat()){
            polArr[polArrIterator] = fileScanner.nextFloat();
            polArrIterator++;
        }

        if (userChoice == 1){
            root = newton(polArr, this.pointA, this.maxIt, this.eps, this.delta);
        } else if (userChoice == 2){
            root = secant(polArr, this.pointA, this.pointB, this.maxIt, this.eps);
        } else if (userChoice == 3){
            root = bisection(polArr, this.pointA, this.pointB, this.maxIt, this.eps);
        } else if (userChoice == 4){
            System.out.println("First parameter input into command line is not right.");
        } else if (userChoice == 5){
            System.out.println("-newt method parameters are not right.");
        } else if (userChoice == 6){
            System.out.println("-sec method parameters are not right.");
        } else if (userChoice == 7){
            System.out.println("Bisection method parameters are not right.");
        } else if (userChoice == 8){
            root = secant(polArr, this.pointA, this.pointB, this.maxIt, this.eps);
        } else if (userChoice == 9){
            System.out.println("-Hybrid method parameters are not right.");
        }

        outputToFile();


    }

    /*
    Secant method algorithm.
     */
    public float secant(float[] f, float x, float x1, int maxIter, double eps){
        int k = 0;
        float x2 = 0;
        float dx = 1.0f;
        while((Math.abs(dx) > eps) && (k<maxIter) && solve(f, x2)!= 0){
            float d = solve(f, x1) - solve(f, x);
            x2 = x1 - solve(f, x1)*(x1-x)/d;
            x = x1;
            x1 = x2;
            dx = x1-x;
            k++;
            numIterations++;
        }
        if (k==maxIter){
            System.out.println("Maximum number of iterations reached!");
            return -10000000000f;
        }
        System.out.println("Root reached.");
        return x1;
    }

    /*
    newton method algorithm.
     */
    public float newton(float[] f, float x, int maxIter, double eps, double delta){
        float fx = solve(f, x);

        for (int it = 0; it < maxIter; it++){
            float fd = solve(derive(f), x);

            if (Math.abs(fd) < delta){
                System.out.println("Small slope!");
                return x;
            }

            float d = fx / fd;
            x = x - d;
            fx = solve(f, x);

            if (Math.abs(d) < eps){
                System.out.println("Algorithm has converged after " + it + "iterations!");
                return x;
            }
            numIterations++;
        }

        System.out.println("Max iterations reached without convergence...");
        return -10000000000f;
    }

    /*
    Bisection method algorithm.
     */
    public float bisection(float[] f, float a, float b, int maxIter, double eps){

        float fa = solve(f, a);
        //System.out.println("This is the value of f(a):" + fa + ", f(" + a + ")");
        float fb = solve(f, b);
        //System.out.println("This is the value of f(b):" + fb + ", f(" + b + ")");

        if (fa * fb >= 0){
            System.out.println("Inadequate values for a and b");
            return (float)-1.0;
        }

        float error = b - a;

        for (int it = 0; it < maxIter; it++){
            error = error / 2;
            float c = a + error;
            float fc = solve(f, c);

            if (Math.abs(error) < eps || fc == 0){
                System.out.println("Algorithm has converged after " + it + " iterations!");
                return c;
            }

            if (fa * fc < 0){
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }
            numIterations++;
        }

        System.out.println("Max iterations reached without convergence...");
        return -10000000000f;
    }

    /*
    Outputting the results to the .sol file.
     */
    public void outputToFile() throws IOException {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File(s + "//polynomialSolution.sol");

        System.out.println("Solution file has been created/updated.");

        FileWriter writer = new FileWriter(file);
        if (root == -10000000000f){
            writer.write("root not found" + ", num iterations: " + numIterations + ", convergence failed.");
        } else {
            writer.write("root: " + root + ", num iterations: " + numIterations + ", convergence succeeded.");
        }
        writer.close();

    }

    /*
    Getting the f(x)
     */
    public float solve(float[] polArr, float x){
        int highestExp = polArr.length - 1;
        float toReturn = 0;
        for (int i = 0; i < polArr.length; i++){
            toReturn += polArr[i] * Math.pow(x, highestExp - i);
        }
        return toReturn;
    }

    /*
    Getting the f'(x)
     */
    public float[] derive(float[] polArr){
        float[] derived = new float[polArr.length - 1];
        for (int i = 0; i < polArr.length - 1; i++){
            derived[i] = polArr[i] * (polArr.length - i - 1);
        }
        return derived;
    }

    /*
    Swap helper method.
     */
    public void swap(float a, float b){
        a = a + b;
        b = a - b;
        a = a - b;
    }
}
