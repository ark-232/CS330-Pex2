import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Vector330Class - provides a 2D vector with associated operations and support
 *
 * @author Steven.Hadfield
 * @version 1.1
 *
 */
public class Vector330 {

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Attributes

    private double x;   // x component of vector
    private double y;   // y component of vector

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Constants

    private static final double EPS = 1.0E-09;  // tolerance for floating point equality checking

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Constructors

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * zero argument constructor
     */
    public Vector330() {
        x = 0.0;
        y = 0.0;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vector330Class - constructor initializing both x and y components with doubles
     *
     * @param x new value for x component of the vector
     * @param y new value for y component of the vector
     */
    public Vector330(double x, double y) {
        this.x = x;
        this.y = y;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vector330Class - constructor initializing both x and y components with ints
     *
     * @param x new value for x component of the vector to be typecast to double
     * @param y new value for y component of the vector to be typecast to double
     */
    public Vector330(int x, int y) {
        this.x = x;
        this.y = y;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Vector330Class - constructor initializing both x and y components with longs
     *
     * @param x new long value for x component of the vector to be typecast to double
     * @param y new long value for y component of the vector to be typecast to double
     */
    public Vector330(long x, long y) {
        this.x = (double) x;
        this.y = (double) y;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Getter (Accessor) methods

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getX() - returns x component of vector as a double
     *
     * @return value of x component as a double
     */
    public double getX() {
        return this.x;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getY() - return the y component of vector as a double
     *
     * @return value of y component as a double
     */
    public double getY() {
        return this.y;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Setter (Mutator) methods

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * setX( double ) - sets the x component of vector using an input of type double
     *
     * @param x new value for x component as a double
     */
    public void setX(double x) {
        this.x = x;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * setY( double ) - sets the y component of vector using an input of type double
     *
     * @param y new value of y component as a double
     */
    public void setY(double y) {
        this.y = y;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Vector Arithematic Methods

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * equals() - checks for vector equality if this and the other (v) vector have components within epsilon of each other
     *
     * @param v other vector passed in
     * @return boolean true if this and other vectors are close enough, else false
     */
    public boolean equals(Vector330 v) {
        return (Math.abs(this.x - v.x) < EPS) && (Math.abs(this.y - v.y) < EPS);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * add() - does vector addition of this vector with the one passed in
     * Vector330Class b = a.add(c);
     * @param v other vector passed in
     * @return  vector sum of this and the other vector
     */
    public Vector330 add(Vector330 v) {
        return new Vector330(this.x + v.x, this.y + v.y);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * subtract() - subtracts passed in vector from this vector
     *
     * @param v other vector passed in
     * @return vector difference of this vector minus the other vector passed in
     */
    public Vector330 subtract(Vector330 v) {
        return new Vector330(this.x - v.x, this.y - v.y);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * dotProduct() - computes the dot product of this vector and the other vector
     *
     * @param v other vector to compute the dot product with
     * @return the scalar (double) dot product of this vector and the other vector
     */
    public double dotProduct(Vector330 v) {
        return ((this.x * v.x) + (this.y * v.y));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * scale() - does a scalar-vector multiplication of this vector with double value passed in
     *
     * @param s value to scale the vector by
     * @return the scaled vector
     */
    public Vector330 scale(double s) {
        return new Vector330((s * this.x), (s * this.y));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * magnitude() - computes the magnitude (2-norm or length) of this vector
     *
     * @return magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * direction() - computes and returns the direction (orientation) of the vector in radians
     *
     * @return angle of the vector in radians
     */
    public double direction() {
        return Math.atan2(this.y, this.x);
    }

    /**
     * distanceTo() - computes the distance between this vector and another vector
     *
     * @param other The other vector
     * @return The distance between this vector and the other vector
     */
    public double distanceTo(Vector330 other) {
        return this.subtract(other).magnitude();
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * normalize() - creates a normalized (of length one) vector in same direction as this vector
     *
     * @return the normalized vector or the zero vector is original vector is close to zero in magnitude
     */
    public Vector330 normalize() {
        double mag = this.magnitude();
        if (mag > EPS) {            // protect against divide by zero
            return this.scale(1.0 / this.magnitude());
        } else {
            return new Vector330(0.0, 0.0);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * toString() - overrides the default toString() method producing an angle-bracket version of this vector
     *
     * @return string representation of the vector in the form "&lt; 3.0, 4.0 &gt;"
     */
    public String toString() {
        return "< " + this.x + ", " + this.y + " >";
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * parseVector() - inputs a Scanner object from which it reads and parses a string representing the vector with
     *          and expected form of "&lt; 3.0, 4.0 &gt;" - note spaces are needed after '&lt;', after the comma,
     *          and before the '&gt;'.
     *
     * @param s Scanner object from which to read the String representation of the vector
     * @return a new Vector330Class object based upon the provided input
     * @throws Exception object with the message set to a description of the parsing error encountered
     */
    public static Vector330 parseVector(Scanner s) throws Exception {

        double xCoord;
        double yCoord;

        Pattern originalPattern = s.delimiter();  // retain original delimiters

        s.useDelimiter("[" + originalPattern + ",]");  // add comma to delimiters

        if (s.hasNext("<")) {

            s.next("<");  // gobble up the '<'

            if (s.hasNextDouble()) {

                xCoord = s.nextDouble();  // capture the x coordinate

                s.useDelimiter(originalPattern); // restore original delimiters

                if (s.hasNext(",")) {

                    s.next(",");  // gobble up the comma

                    if (s.hasNextDouble()) {

                        yCoord = s.nextDouble();  // capture the y coordinate

                        if (s.hasNext(">")) {

                            s.next(">"); // gobble up the '>'

                            return new Vector330(xCoord, yCoord);  // Success!

                        } else {
                            throw new Exception("PARSE ERROR: Missing '>' to end vector");
                        }
                    } else {
                        throw new Exception("PARSE ERROR:  Missing Y coordinate for vector");
                    }
                } else {
                    throw new Exception("PARSE ERROR: Missing comma between X and Y coordinates");
                }
            } else {
                throw new Exception("PARSE ERROR: Missing X coordinate for vector");
            }
        } else {
            throw new Exception("PARSE ERROR: Missing '<' to start vector");
        }
    }

}  // end Vector330Class class