package computer.vision;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Train a neural net to recognize letters A - J. Part of {@link BoundingBox}.
 */
public class NeuralNet {
    private static final String dirPath = "NeuralNet\\rawFiles\\";
    private static final String[] imageFiles = { "a.raw", "b.raw", "c.raw", "d.raw", "e.raw", "f.raw", "g.raw", "h.raw", "i.raw", "j.raw" };
    private static final ArrayList<int[][]> inputArrays = new ArrayList<>();
    private static final ArrayList<int[][]> alteredImages = new ArrayList<>();
    private static final int SIZE = 140;

    // Arrays to store weights
    private static double[][] InputL1Weights = new double[14][14];
    private static double[][] L1L2Weights = new double[14][12];
    private static double[][] L2OutputWeights = new double[12][10];

    // Arrays to calculate and store the input values
    private static double[] scaledValues = new double[14];
    private static double[] unscaledValues = new double[14];

    // Output arrays for each layer
    private static double[] L1Out = new double[14];
    private static double[] L2Out = new double[12];
    private static double[] output = new double[10];

    // What the letter is anticipated to be
    private static double[] anticipatedValues = new double[10];

    // Arrays to store the error values
    private static double[] L1NodeErrors = new double[14];
    private static double[] L2NodeErrors = new double[12];
    private static double[] outputNodeErrors = new double[10];

    /**
     * Controls the flow of the program.
     *
     * @param args - an empty array in this program
     */
    public static void main(String[] args) {
        initWeights();

        // Read in the images one time each so it doesn't have to be repeated
        for (String fileName : imageFiles) {
            inputArrays.add(read(dirPath + fileName));
        }

        // Store the 7x7 version of the images so the computation doesn't need
        // to be repeated multiple times
        for (int[][] image : inputArrays) {
            alteredImages.add(boundImage(preformThinning(cleanImage(image))));
        }

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < alteredImages.size(); j++) {
                // Set the expected value for the letter to be 1
                if (j > 0) {
                    anticipatedValues[j - 1] = 0.0;
                } else {
                    anticipatedValues[alteredImages.size() - 1] = 0.0;
                }
                anticipatedValues[j] = 1.0;
                convertToScale(alteredImages.get(j));

                // Train the net for a particular letter
                calculateOutputs();
                calculateErrors();
                changeInWeight();
            }
        }

        //Classify the images
        determineLetter("a_test.raw");
        determineLetter("b_test.raw");
        determineLetter("c_test.raw");
        determineLetter("d_test.raw");
        determineLetter("e_test.raw");
        determineLetter("f_test.raw");
        determineLetter("g_test.raw");
        determineLetter("h_test.raw");
        determineLetter("i_test.raw");
        determineLetter("j_test.raw");
    }

    /**
     * Initialize the weights on each line of the neural net. Each line is a
     * random weight between -0.5 and 0.5.
     */
    private static void initWeights() {
        // Input to Layer1
        for (int row = 0; row < InputL1Weights.length; row++) {
            for (int col = 0; col < InputL1Weights[0].length; col++) {
                InputL1Weights[row][col] = Math.random() - 0.5;
            }
        }
        // Layer1 to Layer2
        for (int row = 0; row < L1L2Weights.length; row++) {
            for (int col = 0; col < L1L2Weights[0].length; col++) {
                L1L2Weights[row][col] = Math.random() - 0.5;
            }
        }
        // Layer2 to Output
        for (int row = 0; row < L2OutputWeights.length; row++) {
            for (int col = 0; col < L2OutputWeights[0].length; col++) {
                L2OutputWeights[row][col] = Math.random() - 0.5;
            }
        }
    }

    /**
     * Go through each row and column of the 7x7 image and count the number of pixels
     * in each. These values will become the scaled input values between 0.1 and 0.9
     * to use for the net input. The 0th column is index 0 while the 0th row is
     * index 7.
     *
     * @param f - the 7x7 array
     */
    private static void convertToScale(int[][] f) {
        int counter;

        // Column pixel occurrences
        for (int i = 0; i < f.length; i++) {
            counter = 0;
            for (int j = 0; j < f[0].length; j++) {
                if (f[i][j] == 1) {
                    counter++;
                }
            }
            unscaledValues[i] = counter;
        }

        // Row pixel occurrences
        for (int i = 0; i < f.length; i++) {
            counter = 0;
            for (int j = 0; j < f[0].length; j++) {
                if (f[j][i] == 1) {
                    counter++;
                }
            }
            unscaledValues[i + 7] = counter;
        }

        double min = Integer.MAX_VALUE, max = -1;
        for (double value : unscaledValues) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        for (int i = 0; i < unscaledValues.length; i++) {
            scaledValues[i] = (0.8 * ((unscaledValues[i] - min) / (max - min))) + 0.1;
        }
    }

    /**
     * Calculate the change in weights for the layers and update the arrays
     * accordingly. The magnitude of correction is just set to 1 here for
     * simplicity.
     */
    private static void changeInWeight() {
        double magnitudeOfCorrection = 1;
        // Layer 2 nodes
        for (int i = 0; i < L2OutputWeights.length; i++) {
            // Output layer nodes
            for (int j = 0; j < L2OutputWeights[0].length; j++) {
                double weightAdjustment = magnitudeOfCorrection * outputNodeErrors[j] * output[j] * (1 - output[j]) * L2Out[i];
                // Update the layer 2 to output weight
                L2OutputWeights[i][j] += weightAdjustment;
            }
        }

        // Layer 1 nodes
        for (int i = 0; i < L1L2Weights.length; i++) {
            // Layer 2 nodes
            for (int j = 0; j < L1L2Weights[0].length; j++) {
                double weightAdjustment = magnitudeOfCorrection * L2NodeErrors[j] * L2Out[j] * (1 - L2Out[j]) * L1Out[i];
                // Update the layer 1 to layer 2 weights
                L1L2Weights[i][j] += weightAdjustment;
            }
        }

        // Input nodes
        for (int i = 0; i < InputL1Weights.length; i++) {
            // Layer 1 nodes
            for (int j = 0; j < InputL1Weights[0].length; j++) {
                double weightAdjustment = magnitudeOfCorrection * L1NodeErrors[j] * L1Out[j] * (1 - L1Out[j]) * scaledValues[i];
                // Update the input to layer 1 weights
                InputL1Weights[i][j] += weightAdjustment;
            }
        }
    }

    /**
     * Calculate the error values for each layer. The error for each layer is
     * the sum of the weight * error of the layer before it. In other words,
     * the error of layer 2 is the sum of the weight * error of the output layer etc..
     */
    private static void calculateErrors() {
        // Output node error values. anticipated values will contain
        // a one if its the letter we are expecting and a 0 if it is not
        for (int i = 0; i < outputNodeErrors.length; i++) {
            outputNodeErrors[i] = anticipatedValues[i] - output[i];
        }

        // Calculate layer 2 errors based on the output errors
        // The sum of weight * error of the previous layer
        for (int i = 0; i < L2NodeErrors.length; i++) {
            double error = 0;
            // Use the output node errors to calculate layer 2 errors
            for (int j = 0; j < L2OutputWeights[0].length; j++) {
                error += L2OutputWeights[i][j] * outputNodeErrors[j];
            }
            L2NodeErrors[i] = error;
        }

        // Layer 1 errors (sum of weight * error of layer 2)
        for (int i = 0; i < L1NodeErrors.length; i++) {
            double error = 0;
            // User layer 2 errors to calculate layer 1 errors
            for (int j = 0; j < L1L2Weights[0].length; j++) {
                error += L1L2Weights[i][j] * L2NodeErrors[j];
            }
            L1NodeErrors[i] = error;
        }
    }

    /**
     * Calculate the outputs for each layer of the network.
     */
    private static void calculateOutputs() {
        // For all input nodes (14 of them)
        for (int L1Node = 0; L1Node < InputL1Weights[0].length; L1Node++) {
            double weightedSum = 0;
            // Calculate the weighted sum for the input layer
            for (int inputNode = 0; inputNode < InputL1Weights.length; inputNode++) {
                // Input to L1 = W(j,i) = InputL1Weights[j][i]
                weightedSum += InputL1Weights[inputNode][L1Node] * scaledValues[inputNode];
            }
            // Computes g(x) for layer 1
            L1Out[L1Node] = 1.0 / (1 + Math.pow(Math.E, -weightedSum));
        }

        // For all L2 nodes (12 of them)
        for (int L2Node = 0; L2Node < L1L2Weights[0].length; L2Node++) {
            double weightedSum = 0;
            // Calculate the weighted sum for layer 1
            for (int L1Node = 0; L1Node < L1L2Weights.length; L1Node++) {
                // L1 to L2 = W(j,i) = L1L2Weights[j][i]
                weightedSum += L1L2Weights[L1Node][L2Node] * L1Out[L1Node];
            }
            // Computes g(x) for layer 2
            L2Out[L2Node] = 1.0 / (1 + Math.pow(Math.E, -weightedSum));
        }

        // For all output nodes (10 of them)
        for (int outputNode = 0; outputNode < L2OutputWeights[0].length; outputNode++) {
            double weightedSum = 0;
            // Calculate the weighted sum for layer 2
            for (int L2Node = 0; L2Node < L2OutputWeights.length; L2Node++) {
                // L2 to output = W(j,i) = L2OutputWeights[j][i]
                weightedSum += L2OutputWeights[L2Node][outputNode] * L2Out[L2Node];
            }
            // Computes g(x) for the output layer
            output[outputNode] = 1.0 / (1 + Math.pow(Math.E, -weightedSum));
        }
    }

    /**
     * Attempts to determine the letter within the given file. The first step is to
     * prepare the image (clean, thin, and bound it). Then g(x) is computed for each
     * layer based off the weighted sum of the layer before it.
     *
     * @param filename - the file containing the letter
     */
    private static void determineLetter(String filename) {
        convertToScale(boundImage(preformThinning(cleanImage(read(dirPath + filename)))));

        calculateOutputs();

        System.out.println("FILE: " + filename);
        System.out.println("OUTPUT:");

        Arrays.stream(output).forEach(val -> System.out.printf("%.3f  ", val));

        System.out.println("\n");
    }

    /**
     * Perform a thinning operation by using the two patterns:
     * 000 		 ?00
     * ?1?  and  110
     * 111		 ?1?
     * These two initial patterns are both rotated 90 degrees, then looked for again. This repeats until
     * they have been rotated all the way around (a total of 8 patterns). Each time, the center of the image
     * is marked, and the set difference between the marked point and the original image is done.
     *
     * @param f - the image being thinned
     * @return - the final version of the thinned image
     */
    private static int[][] preformThinning(int[][] f) {
        int[][] previousImage = null;
        int[][] image = new int[f.length][f[0].length];
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                image[i][j] = f[i][j];
            }
        }

        while (!equal(image, previousImage)) {
            int[][] temp = new int[image.length][image[0].length];
            previousImage = image;
            for (int i = 1; i < image.length - 1; i++) {
                for (int j = 1; j < image[0].length - 1; j++) {
                    if (image[i - 1][j - 1] == 0 && image[i - 1][j] == 0 && image[i - 1][j + 1] == 0 &&                             //000
                            image[i][j] == 1 && image[i + 1][j - 1] == 1 && image[i + 1][j] == 1 && image[i + 1][j + 1] == 1) {     //?1?
                        temp[i][j] = 1;                                                                                             //111
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }
                    if (image[i - 1][j] == 0 && image[i - 1][j + 1] == 0 && image[i][j + 1] == 0 &&                                 //?00
                            image[i][j - 1] == 1 && image[i][j] == 1 && image[i + 1][j] == 1) {                                     //110
                        temp[i][j] = 1;                                                                                             //?1?
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }

                    //Rotate original 90 degrees...
                    if (image[i - 1][j + 1] == 0 && image[i][j + 1] == 0 && image[i + 1][j + 1] == 0 &&                             //1?0
                            image[i][j] == 1 && image[i - 1][j - 1] == 1 && image[i][j - 1] == 1 && image[i + 1][j - 1] == 1) {     //110
                        temp[i][j] = 1;                                                                                             //1?0
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }
                    if (image[i + 1][j] == 0 && image[i + 1][j + 1] == 0 && image[i][j + 1] == 0 &&                                 //?1?
                            image[i][j - 1] == 1 && image[i][j] == 1 && image[i - 1][j] == 1) {                                     //110
                        temp[i][j] = 1;                                                                                             //?00
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }

                    //Rotate original 180 degrees...
                    if (image[i + 1][j - 1] == 0 && image[i + 1][j] == 0 && image[i + 1][j + 1] == 0 &&                             //111
                            image[i][j] == 1 && image[i - 1][j - 1] == 1 && image[i - 1][j] == 1 && image[i - 1][j + 1] == 1) {     //?1?
                        temp[i][j] = 1;                                                                                             //000
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }
                    if (image[i][j - 1] == 0 && image[i + 1][j - 1] == 0 && image[i + 1][j] == 0 &&                                 //?1?
                            image[i - 1][j] == 1 && image[i][j] == 1 && image[i][j + 1] == 1) {                                     //011
                        temp[i][j] = 1;                                                                                             //00?
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }

                    //Rotate original 270 degrees...
                    if (image[i - 1][j - 1] == 0 && image[i][j - 1] == 0 && image[i + 1][j - 1] == 0 &&                             //0?1
                            image[i][j] == 1 && image[i - 1][j + 1] == 1 && image[i][j + 1] == 1 && image[i + 1][j + 1] == 1) {     //011
                        temp[i][j] = 1;                                                                                             //0?1
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }
                    if (image[i - 1][j] == 0 && image[i - 1][j - 1] == 0 && image[i][j - 1] == 0 &&                                 //00?
                            image[i + 1][j] == 1 && image[i][j] == 1 && image[i][j + 1] == 1) {                                     //011
                        image = setDifference(image, temp);
                        temp[i][j] = 0;
                    }
                }
            }
        }
        return image;
    }

    /**
     * Determine if the two given arrays are equivalent or not
     *
     * @param f - the first array for the equivalency check
     * @param g - the second array for the equivalency check
     * @return - false if either array is null and the other isn't, or if the two arrays are not equal. Otherwise
     * true is returned
     */
    private static boolean equal(int[][] f, int[][] g) {
        if ((f == null && g != null) || (f != null && g == null)) {
            return false;
        }
        return Arrays.equals(f, g);
    }

    /**
     * Dilate with a 4-neighbor definition
     *
     * @param f - the array to dilate
     */
    private static int[][] dilationFour(int[][] f) {
        // This function performs dilation operation
        int i, j;
        int[][] g = new int[SIZE][SIZE];
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                g[i][j] = f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] > 0 ? 1 : 0;
            }
        }
        return g;
    }

    /**
     * Erode with a 4-neighbor definition
     *
     * @param f - the array to erode
     */
    private static int[][] erosionFour(int[][] f) {
        // This function performs erosion operation
        int i, j;
        int[][] g = new int[SIZE][SIZE];
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                if (f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] == 5) {
                    g[i][j] = 1;
                } else {
                    g[i][j] = 0;
                }
            }
        }
        return g;
    }

    /**
     * Attempt to get rid of stray pixels in the given array. Performs a closing
     * followed by an opening
     *
     * @param f - the array to clean up
     */
    private static int[][] cleanImage(int[][] f) {
        return dilationFour(erosionFour(erosionFour(dilationFour(f))));
    }

    /**
     * Reads a raw image file and stores it in a given array
     *
     * @param filename - the name of the raw data file
     */
    private static int[][] read(String filename) {
        int[][] f = new int[SIZE][SIZE];
        try (InputStream in = RecognizeDigit.class.getClassLoader().getResourceAsStream(filename)) {
            byte[] b = new byte[1];
            int i, j;
            for (i = 0; i < SIZE; i++) {
                for (j = 0; j < SIZE; j++) {
                    if (in.read(b) == -1) {
                        throw new IOException("Error reading input");
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

        return f;
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
     * Computes the bounded version of the image in a 7x7 array. It uses the upper and lower bounds for the
     * x and y values to iterate over the actual image in the 140x140 array. It then converts a portion of the
     * image into a single pixel for the bounded array.
     *
     * @param f - the image being converted into a 7x7 image
     * @return - the bounded image
     */
    private static int[][] boundImage(int[][] f) {
        BoundingBox bounds = getBoundingBox(f);
        int[][] boundedImage = new int[7][7];

        int x = (bounds.upperX - bounds.lowerX) / 7;
        int y = (bounds.upperY - bounds.lowerY) / 7;
        for (int i = bounds.lowerX; i < bounds.upperX; i += x) {
            for (int j = bounds.lowerY; j < bounds.upperY; j += y) {
                int row = (i - bounds.lowerX) / x;
                int col = (j - bounds.lowerY) / y;
                if (isZero(i, i + x, j, j + y, f)) {
                    boundedImage[row][col] = 0;
                } else {
                    boundedImage[row][col] = 1;
                }
            }
        }
        return boundedImage;
    }

    /**
     * Check to see if the array is all zeros or not
     *
     * @param lowX - the lowest x bound of the current box
     * @param highX - the highest x bound of the current box
     * @param lowY - the lowest y bound of the current box
     * @param highY - the highest y bound of the current box
     * @param f - the array to check
     * @return - true if it is all zero, false otherwise
     */
    private static boolean isZero(int lowX, int highX, int lowY, int highY, int[][] f) {
        for (int i = lowX; i <= highX; i++) {
            for (int j = lowY; j <= highY; j++) {
                if (f[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Find the bounding box for the entire image, that is the maximum and minimum values for
     * row and column in which a pixel is located.
     *
     * @param f - the image that is being bounded
     * @return - a bounding box of the new image
     */
    private static BoundingBox getBoundingBox(int[][] f) {
        int lowerX = -1, upperX = -1, lowerY = -1, upperY = -1;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (f[row][col] == 1) {
                    if (lowerX == -1 || row < lowerX) {
                        lowerX = row;
                    }
                    if (row > upperX) {
                        upperX = row;
                    }
                    if (lowerY == -1 || col < lowerY) {
                        lowerY = col;
                    }
                    if (col > upperY) {
                        upperY = col;
                    }
                }
            }
        }
        if (lowerX > 0 && lowerY > 0 && upperX > 0 && upperY > 0) {
            return new BoundingBox(lowerX, upperX, lowerY, upperY);
        } else {
            System.err.println("Error computing the bounding box.");
            return null;
        }
    }
}
