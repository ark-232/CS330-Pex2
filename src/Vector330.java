import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a 2D vector with components x and y.
 *
 * @author Enrique Oti
 * @version 1.0
 * DOCUMENTATION: Used ChatGPT to help with the regex expressions, some parsing info, and JavaDoc comments
 */
public class Vector330 {

    /** Small constant for floating-point comparisons. */
    private static final double EPS = 1.0e-9;
    private double x;
    private double y;

    /**
     * Default constructor, initializes the vector to (0,0).
     */
    public Vector330() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * Constructs a vector with specified x and y values.
     *
     * @param x The x component.
     * @param y The y component.
     */
    public Vector330(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x component.
     *
     * @return x component.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x component.
     *
     * @param x The x value to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y component.
     *
     * @return y component.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y component.
     *
     * @param y The y value to set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Checks for equality between this vector and another object.
     * Two vectors are considered equal if their x and y components are close enough (based on EPS).
     *
     * @param obj The object to compare with.
     * @return true if the objects are the same or have nearly identical x and y values, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector330 vector330 = (Vector330) obj;
        return Math.abs(x - vector330.x) < EPS && Math.abs(y - vector330.y) < EPS;
    }

    /**
     * Generates a hash code for this vector.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Adds this vector with another vector.
     *
     * @param v The vector to add with.
     * @return A new vector resulting from the addition.
     */
    public Vector330 add(Vector330 v) {
        return new Vector330(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v The vector to subtract.
     * @return A new vector resulting from the subtraction.
     */
    public Vector330 subtract(Vector330 v) {
        return new Vector330(this.x - v.x, this.y - v.y);
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param v The other vector.
     * @return The dot product value.
     */
    public double dotProduct(Vector330 v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Computes the magnitude of this vector.
     *
     * @return The magnitude.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Computes the direction (angle) of this vector.
     *
     * @return The direction in radians.
     */
    public double direction() {
        return Math.atan2(y, x);
    }

    /**
     * Normalizes this vector.
     *
     * @return A new vector with magnitude of 1 in the direction of this vector.
     */
    public Vector330 normalize() {
        double mag = magnitude();
        if (mag < EPS) return new Vector330(0, 0);
        return new Vector330(x / mag, y / mag);
    }

    /**
     * Scales this vector by a scalar value.
     *
     * @param s The scalar value.
     * @return A new scaled vector.
     */
    public Vector330 scale(double s) {
        return new Vector330(this.x * s, this.y * s);
    }

    /**
     * Parses a vector from the given input scanner.
     * The vector is expected to be in the format: &lt; x, y &gt;.
     *
     * @param s The scanner from which the vector string is read.
     * @return The parsed vector.
     * @throws Exception If there is a parsing error.
     *
     * <p>Regex Explanation:</p>
     * <pre>
     * &lt;\\s*             : Matches a '&lt;' followed by zero or more spaces.
     * -?\\d+(\\.\\d+)?    : Matches an optional '-' for negative numbers, followed by one or more digits, and an optional fraction.
     * ,\\s*               : Matches a ',' followed by zero or more spaces.
     * &gt;                : Matches the closing '&gt;'.
     * </pre>
     */
    public static Vector330 parseVector(java.util.Scanner s) throws Exception {
        String str = s.findInLine("<\\s*(-?\\d+(\\.\\d+)?)\\s*,\\s*(-?\\d+(\\.\\d+)?)\\s*>");
        if (str == null || str.isEmpty()) throw new Exception("Parsing error: Invalid vector format");

        Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher m = p.matcher(str);
        m.find();
        double x = Double.parseDouble(m.group());
        m.find();
        double y = Double.parseDouble(m.group());

        return new Vector330(x, y);
    }

    /**
     * @return A string representation of this vector in the format: &lt; x, y &gt;.
     */
    @Override
    public String toString() {
        return String.format("< %.1f, %.1f >", x, y);
    }
}
