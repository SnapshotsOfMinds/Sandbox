package computer.vision;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Recognize handwritten digits 0 - 9. Part of {@link ConnectedComponents}.
 * <p>
 * There are a few important aspects to note. The first of which is when the file is read in, it is a 140 x 140 image.
 * After it is read in, it is converted to a 280 x 280 image to avoid having parts get cut off during dilation and erosion operations.
 * Next, it is important to note that both 4- and 8-neighbor definitions are used when doing most of the morphological operations.
 * The “cleanImage” method is also implemented which will assist in getting rid of stray pixels. After the setup is complete, the first
 * operation performed is getting an image containing only the possible bays and lakes. After, the connected components algorithm is used to
 * label those “holes.” After it is determined if it is a lake or a bay. If it happens to be a bay the location of the lid is computed.
 * Depending on where the lid is located, a value is added to an ArrayList which will keep track of the lids. This helps to determine some
 * numbers that have the same numbers of bays and lakes.
 */
public class RecognizeDigit {
    // Size of the image doubled
    private static final int SIZE = 280;

    // Variables used in determining the digits
    private static int lakes = 0, bays = 0, lids = 0;
    private static double rowCenterLocation = 0.0, colCenterLocation = 0.0;

    // Arrays that contain either the full image or partial images
    private static int[][] input = new int[SIZE][SIZE];
    private static int[][] tempImage = new int[SIZE][SIZE];
    private static int[][] lidImage = new int[SIZE][SIZE];
    private static int[][] hole = new int[SIZE][SIZE];
    private static int[][] tempHole = new int[SIZE][SIZE];
    private static int[][] imageParts = new int[SIZE][SIZE];

    // Lists that use labeled images to store things
    private static ArrayList<Integer> lidLocations = new ArrayList<>();
    private static ArrayList<Integer> labelsUsed = new ArrayList<>();

    /**
     * Controls the flow of the program.
     *
     * @param args - an empty array in this program
     */
    public static void main(String[] args) {
        read(input);
        input = mapToBiggerArray(input);

        double[] values = getCenter(rowCenterLocation, colCenterLocation, input);
        rowCenterLocation = values[0];
        colCenterLocation = values[1];
        cleanImage(input);

        imageParts = getComponents(input);
        cleanImage(imageParts);
        imageParts = labeledParts(imageParts);

        gatherData();
        determineDigit();
    }

    /**
     * Reads a raw image file and stores it in a given array
     *
     * @param f - the array to store the image in
     */
    private static void read(int[][] f) {
        String directoryPath = "\\ConnectedComponents\\numbersRAW\\";

        // The "<filename>.raw" is what to change when testing a new file
        try (InputStream in = RecognizeDigit.class.getClassLoader().getResourceAsStream(directoryPath + "9curve.raw")) {
            byte[] b = new byte[1];
            int i, j;
            for (i = 0; i < 140; i++) {
                for (j = 0; j < 140; j++) {
                    if (in.read(b) == -1) {
                        throw new IOException("Unable to read raw file.");
                    }
                    if (b[0] != 0) {
                        f[i][j] = 0;
                    } else {
                        f[i][j] = 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * A way to map the original input array into an array doubled in size.
     * This is to prevent the image from getting too big when performing
     * operations to determine what the image is
     *
     * @param f - the array to make bigger
     * @return - the bigger array
     */
    private static int[][] mapToBiggerArray(int[][] f) {
        int[][] bigger = new int[SIZE * 2][SIZE * 2];
        for (int row = 0; row < bigger.length; row++) {
            for (int col = 0; col < bigger.length; col++) {
                bigger[row][col] = 0;
            }
        }
        for (int row = 70; row < f.length + 70; row++) {
            for (int col = 70; col < f.length + 70; col++) {
                bigger[row][col] = f[row - 70][col - 70];
            }
        }
        return bigger;
    }

    /**
     * Attempt to get rid of stray pixels in the given array
     *
     * @param f - the array to clean up
     */
    private static void cleanImage(int[][] f) {
        int[][] temp = new int[SIZE][SIZE];
        int[][] temp2 = new int[SIZE][SIZE];
        dilationFour(f, temp);
        erosionFour(temp, temp2);
        erosionFour(temp2, temp);
        dilationFour(temp, f);
    }

    /**
     * Dilate with a 4-neighbor definition
     *
     * @param f - the array to dilate
     * @param g - the array to store the dialted image in
     */
    private static void dilationFour(int[][] f, int[][] g) {
        // This function performs dilation operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                g[i][j] = f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] > 0 ? 1 : 0;
            }
        }
    }

    /**
     * Dilate with a 8-neighbor definition
     *
     * @param f - the array to dilate
     * @param g - the array to store the dialted image in
     */
    private static void dilationEight(int[][] f, int[][] g) {
        // This function performs dilation operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                g[i][j] = f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1]
                        + f[i - 1][j - 1] + f[i - 1][j + 1] + f[i + 1][j - 1] + f[i + 1][j + 1] > 0 ? 1 : 0;
            }
        }
    }


    /**
     * Erode with a 4-neighbor definition
     *
     * @param f - the array to erode
     * @param g - the array to store the eroded image in
     */
    private static void erosionFour(int[][] f, int[][] g) {
        // This function performs erosion operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                if (f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] == 5) {
                    g[i][j] = 1;
                } else {
                    g[i][j] = 0;
                }
            }
        }
    }

    /**
     * Erode with a 8-neighbor definition
     *
     * @param f - the array to erode
     * @param g - the array to store the eroded image in
     */
    private static void erosionEight(int[][] f, int[][] g) {
        // This function performs erosion operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                if (f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1]
                        + f[i - 1][j - 1] + f[i - 1][j + 1] + f[i + 1][j - 1] + f[i + 1][j + 1] == 9) {
                    g[i][j] = 1;
                } else {
                    g[i][j] = 0;
                }
            }
        }
    }

    /**
     * Performs set difference on the given arrays
     *
     * @param f - the first array
     * @param g - the second array
     * @return - the difference between f and g
     */
    private static int[][] setDifference(int[][] f, int[][] g) {
        int[][] result = new int[SIZE][SIZE];
        clear(result);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (f[i][j] == 1 && g[i][j] == 0) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }

    /**
     * Determines the locations of the lids for the image
     */
    private static void getLidPositions() {
        double lidRowLocation = 0.0, lidColLocation = 0.0;
        double[] values = getCenter(lidRowLocation, lidColLocation, lidImage);

        lidRowLocation = values[0];
        lidColLocation = values[1];

        if (lidRowLocation < rowCenterLocation) {
            if (lidColLocation < colCenterLocation) {
                lidLocations.add(1);
            } else {
                lidLocations.add(2);
            }
        } else {
            if (lidColLocation < colCenterLocation) {
                lidLocations.add(3);
            } else {
                lidLocations.add(4);
            }
        }
        clear(lidImage);
    }

    /**
     * Gets the center location of the given image
     *
     * @param theRow - the row used in the calculation
     * @param theCol - the column used in the calculation
     * @param image - the image being looped through
     * @return - an array containing the row and column values
     */
    private static double[] getCenter(double theRow, double theCol, int[][] image) {
        double[] values = new double[2];
        int area = 0;
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                if (image[row][col] == 1) {
                    area++;
                    theRow += row;
                    theCol += col;
                }
            }
        }

        values[0] = theRow / area;
        values[1] = theCol / area;
        return values;
    }

    /**
     * Finds the candidates for lakes and bays
     *
     * @param f - the image to work on
     * @return - the image containing the lake and bay candidates
     */
    private static int[][] getComponents(int[][] f) {
        int[][] temp = new int[SIZE][SIZE];
        int[][] temp2 = new int[SIZE][SIZE];
        dilationFour(f, temp);
        erosionFour(temp, temp2);

        for (int i = 0; i <= 35; i++) {
            if (i % 2 == 0) {
                dilationFour(temp2, temp);
            } else {
                dilationEight(temp, temp2);
            }

        }

        for (int i = 0; i <= 35; i++) {
            if (i % 2 == 0) {
                erosionFour(temp2, temp);
            } else {
                erosionEight(temp, temp2);
            }
        }

        return tempImage = setDifference(temp2, f);
    }

    /**
     * Set the given array to be all 0
     *
     * @param f - the array to clear
     */
    private static void clear(int[][] f) {
        int i, j;
        for (i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                f[i][j] = 0;
            }
        }
    }

    /**
     * Check to see if the array is all zeros or not
     *
     * @param f - the array to check
     * @return - true if it is all zero, false otherwise
     */
    private static boolean isZero(int[][] f) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (f[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the labeled component image
     *
     * @param f - the component array
     * @return - the labeled component array
     */
    private static int[][] labeledParts(int[][] f) {
        ConnectedComponents components = new ConnectedComponents(f);
        f = components.getOutput();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (f[row][col] != 0 && !labelsUsed.contains(f[row][col])) {
                    labelsUsed.add(f[row][col]);
                }
            }
        }

        return f;
    }

    /**
     * Try to determine what the original digit is based on the lakes, bays, and lids.
     */
    private static void determineDigit() {
        System.out.printf("Bays: %d\nLakes: %d\nLids: %d\n", bays, lakes, lids);

        if (lakes == 2) {
            System.out.println("The number is closest to 8");
        } else if (lakes == 0) {
            if (bays == 0) {
                System.out.println("The number is closest to 1");
            } else if (bays == 1) {
                System.out.println("The number is closest to 7");
            } else if (bays == 2) {
                if (lidLocations.contains(4)) {
                    System.out.println("The number is closest to 2");
                } else if (lidLocations.contains(2)) {
                    System.out.println("The number is closest to 5");
                } else if (lidLocations.contains(1) && lidLocations.contains(3)) {
                    System.out.println("The number is closest to 4");
                } else {
                    System.out.println("The number could not be determined");
                }
            } else if (bays == 3) {
                System.out.println("The number is closest to 3");
            } else if (bays == 4) {
                if (lidLocations.size() == 4) {
                    System.out.println("The number is closest to 7");
                } else {
                    System.out.println("The number is closest to 4");
                }
            } else {
                System.out.println("The number could not be determined");
            }
        } else if (lakes == 1) {
            if (bays == 0) {
                System.out.println("The number is closest to 0");
            } else if (bays == 1) {
                if (lidLocations.contains(1) || lidLocations.contains(2)) {
                    System.out.println("The number is closest to 6");
                } else if (lidLocations.contains(3) || lidLocations.contains(4)) {
                    System.out.println("The number is closest to 9");
                } else {
                    System.out.println("The number could not be determined");
                }
            } else if (bays == 3) {
                if (lidLocations.contains(1)) {
                    System.out.println("The number is closest to 2");
                } else if (lidLocations.contains(2)) {
                    System.out.println("The number is closest to 4");
                } else {
                    System.out.println("The number could not be determined");
                }

            } else {
                System.out.println("The number could not be determined");
            }
        } else {
            System.out.println("The number could not be determined");
        }
    }

    /**
     * Attempts to calculate the number of lakes and bays the image has, then
     * calls getLidPositions if a bay is found
     */
    private static void gatherData() {
        int[][] temp = new int[SIZE][SIZE];

        for (int label = 0; label < labelsUsed.size(); label++) {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    temp[row][col] = imageParts[row][col];
                }
            }

            for (int row = 0; row < temp.length; row++) {
                for (int col = 0; col < temp[row].length; col++) {
                    if (temp[row][col] == labelsUsed.get(label)) {
                        hole[row][col] = 1;
                    }
                }
            }

            dilationFour(hole, tempHole);
            tempImage = setDifference(tempHole, hole);
            lidImage = setDifference(tempImage, input);

            if (isZero(lidImage)) {
                lakes++;
            } else {
                bays++;
                lids++;
                getLidPositions();
            }
            clear(hole);
        }
    }

    /**
     * Method to display arrays for debugging
     *
     * @param f - the array to be output
     * @param name - a way to distinguish which array is printed
     */
    public static void displayArrays(int[][] f, String name) {
        int row, col;
        System.out.println("");
        System.out.println(name);
        for (row = 0; row < f.length; row++) {
            for (col = 0; col < f[row].length; col++) {
                System.out.print(f[row][col]);
            }
            System.out.println("");
        }
        System.out.println("");
    }
}