package computer.vision;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MorphTransform {
    // This program implements the basic morphological operations
    static final int SIZE = 140;
    static byte[][] input = new byte[SIZE][SIZE];
    static byte[][] output = new byte[SIZE][SIZE];
    static byte[][] tempImage = new byte[SIZE][SIZE];
    static ArrayList<byte[][]> skeletonSets = new ArrayList<byte[][]>();

    static FileInputStream in;
    static String directoryPath = "C:\\Users\\chris\\Desktop\\CVMorph\\";

    public static void main(String[] args) {
        read(input);
        int max = findMaxDisc();
        for (int i = 0; i <= max; i++) {
            skeletonSets.add(opening(i));
        }
        byte[][] skeletons = union(skeletonSets);
        display(skeletons, "skeletonSet");
        output = skeletonSets.get(max);
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
    public static void clear(byte[][] f) {
        int i, j;
        for (i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                f[i][j] = 0;
            }
        }
    }

    //Union a byte[][] with 1
    public static byte[][] union(ArrayList<byte[][]> skeletons) {
        byte[][] result = new byte[SIZE][SIZE];
        for (byte[][] b : skeletons) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (b[i][j] == 1) {
                        result[i][j] = 1;
                    }
                }
            }
        }
        return result;
    }

    //Union two byte[][]s
    public static byte[][] union(byte[][] f, byte[][] g) {
        byte[][] result = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (f[i][j] == 1 || g[i][j] == 1) {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }

    //Find the maximum disc
    public static int findMaxDisc() {
        int result = 0;
        while (!isNotZero(input)) {
            read(input);
            for (int i = 0; i <= result; i++) {
                erosion(input, tempImage);
                input = tempImage;
                tempImage = new byte[SIZE][SIZE];
            }
            result++;
        }
        return result;
    }

    //Opening operation
    public static byte[][] opening(int index) {
        byte[][] result = new byte[SIZE][SIZE];
        read(input);
        byte[][] erodedSubset;
        byte[][] dilatedSubset = null;

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
    public static boolean isNotZero(byte[][] f) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (f[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    //Perform set difference on two byte[][]s
    public static byte[][] setDifference(byte[][] f, byte[][] g) {
        byte[][] result = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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
    public static void read(byte[][] f) {
        try {
            in = new FileInputStream(directoryPath + "p1.raw");
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nInput file not found.");
        }
        byte[] b = new byte[1];
        int i, j;
        for (i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                try {
                    in.read(b);
                } catch (IOException e) {
                    System.out.println("IOException in read method: " + e);
                }
                if (b[0] != 0) {
                    f[i][j] = 0;
                } else {
                    f[i][j] = 1;
                }
            }
        }
    }

    // Write to a raw image file
    public static void display(byte[][] f, String filename) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(directoryPath + filename + ".raw");
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nOutput file not found.");
        }
        int i, j;
        for (i = 0; i < SIZE; i++) {
            for (j = 0; j < SIZE; j++) {
                try {
                    if (f[i][j] != 0) {
                        out.write(0);
                    } else {
                        out.write(255);
                    }
                } catch (IOException e) {
                    System.out.println("IOException in write method: " + e);
                }
            }
        }
    }

    public static void dilation(byte[][] f, byte[][] g) {
        // This function performs dilation operation
        int i, j;
        clear(g);
        for (i = 1; i < SIZE - 1; i++) {
            for (j = 1; j < SIZE - 1; j++) {
                g[i][j] = f[i][j] + f[i - 1][j] + f[i + 1][j] + f[i][j - 1] + f[i][j + 1] > 0 ? (byte) 1 : (byte) 0;
            }
        }
    }

    public static void erosion(byte[][] f, byte[][] g) {
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
