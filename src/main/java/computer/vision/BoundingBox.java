package computer.vision;

/**
 * Create a bounding box for the image being operated on. Part of {@link NeuralNet}.
 */
public class BoundingBox {
    // The upper and lower x and y values
    public int lowerX, upperX, lowerY, upperY;

    /**
     * Creates a bounding box for the current image
     *
     * @param lowerX - the lowest x value in the image
     * @param upperX - the highest x value in the image
     * @param lowerY - the lowest y value in the image
     * @param upperY - the highest y value in the image
     */
    BoundingBox(int lowerX, int upperX, int lowerY, int upperY) {
        int count = 0;
        while ((upperX - lowerX) % 7 != 0) {
            if (count % 2 == 0) {
                upperX++;
                count++;
            } else {
                lowerX--;
                count++;
            }
        }
        count = 0;
        while ((upperY - lowerY) % 7 != 0) {
            if (count % 2 == 0) {
                upperY++;
                count++;
            } else {
                lowerY--;
                count++;
            }
        }
        this.lowerX = lowerX;
        this.upperX = upperX;
        this.lowerY = lowerY;
        this.upperY = upperY;
    }
}
