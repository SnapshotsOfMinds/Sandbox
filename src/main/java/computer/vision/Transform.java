package computer.vision;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * THIS PROJECT CANNOT RUN. The required raw image files are missing.
 */
public class Transform {
    // This program implements the basic morphological operations
    private static final int SIZE = 140;
    private static byte[][] input = new byte[SIZE][SIZE];
    private static byte[][] tempImage = new byte[SIZE][SIZE];
    private static ArrayList<byte[][]> skeletonSets = new ArrayList<>();

    private static String directoryPath = "C:\\Users\\bowenc65\\Desktop\\CVMorph\\";

    public static void main(String[] args) {
        read(input);
        int max = findMaxDisc();
        for (int i = 0; i <= max; i++) {
            skeletonSets.add(opening(i));
        }
        byte[][] skeletons = union(skeletonSets);
        display(skeletons, "skeletonSet.raw");
        byte[][] output = skeletonSets.get(max);
        for (int i = max; i >= 0; i--) {
            dilation(output, tempImage);
            tempImage = union(tempImage, skeletonSets.get(i));
            output = tempImage;
            tempImage = new byte[SIZE][SIZE];
            if (i % 3 == 0) {
                display(output, "reconstruct" + i);
            }
        }
        display(output, "reconstructFinal");
    }

    // Set all elements to zero
    private static void clear(byte[][] f) {
        int i, j;
        for (i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                f[i][j] = 0;
            }
        }
    }

    //Union a byte[][] with 1
    private static byte[][] union(ArrayList<byte[][]> skeletons) {
        byte[][] result = new byte[SIZE][SIZE];
        for (byte[][] b : skeletons) {
            for (int i = 0; i < SIZE - 1; i++) {
                for (int j = 0; j < SIZE - 1; j++) {
                    if (b[i][j] == 1) {
                        result[i][j] = 1;
                    }
                }
            }
        }
        return result;
    }

    //Union two byte[][]s
    private static byte[][] union(byte[][] f, byte[][] g) {
        byte[][] result = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (f[i][j] == 1 || g[i][j] == 1) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }

    //Find the maximum disc
    private static int findMaxDisc() {
        int result = 0;
        while (!isZero(input)) {
            read(input);
            for (int i = 0; i < result; i++) {
                erosion(input, tempImage);
                input = tempImage;
                tempImage = new byte[SIZE][SIZE];
            }
            result++;
        }
        return result;
    }

    //Opening operation
    private static byte[][] opening(int index) {
        byte[][] result = new byte[SIZE][SIZE];
        read(input);
        byte[][] erodedSubset;
        byte[][] dilatedSubset;

        for (int i = 0; i <= index; i++) {
            erodedSubset = new byte[SIZE][SIZE];
            dilatedSubset = new byte[SIZE][SIZE];
            erosion(input, erodedSubset);
            dilation(erodedSubset, dilatedSubset);
            result = setDifference(input, dilatedSubset);
            input = erodedSubset;
        }

        return result;
    }

    //Check if the byte[][] is zero
    private static boolean isZero(byte[][] f) {
        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (f[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    //Perform set difference on two byte[][]s
    private static byte[][] setDifference(byte[][] f, byte[][] g) {
        byte[][] result = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                if (f[i][j] == 1 && g[i][j] == 1) {
                    result[i][j] = 0;
                }
                if (f[i][j] == 1 && g[i][j] == 0) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }

    // Read from a raw image file
    private static void read(byte[][] f) {
        try (FileInputStream in = new FileInputStream(directoryPath + "p1.raw")) {
            byte[] b = new byte[1];
            int i, j;
            for (i = 0; i < SIZE - 1; i++) {
                for (j = 0; j < SIZE - 1; j++) {
                    if (in.read(b) == -1) {
                        throw new IOException("Unable to read the bytes for the array.");
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

    // Write to a raw image file
    private static void display(byte[][] f, String filename) {
        try (FileOutputStream out = new FileOutputStream(directoryPath + filename + ".raw")) {
            int i, j;
            for (i = 0; i < SIZE - 1; i++) {
                for (j = 0; j < SIZE - 1; j++) {
                    if (f[i][j] != 0) {
                        out.write(0);
                    } else {
                        out.write(255);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dilation(byte[][] f, byte[][] g) {
        // This function performs dilation operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                g[i][j] = f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] > 0 ? (byte) 1 : (byte) 0;
            }
        }
    }

    private static void erosion(byte[][] f, byte[][] g) {
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
}
