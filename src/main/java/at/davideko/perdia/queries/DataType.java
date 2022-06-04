package at.davideko.perdia.queries;

/**
 * Enum containing all possible datatypes an entry in the database can have.
 * This enum contains Long and Double instead of Integer and Float since Perdia-DB uses Rusts 64-bit Integers and
 * Floats, and Javas Integer and Float types are only 32-bit.
 */
public enum DataType {
    STRING,
    INTEGER,
    FLOAT,
    UNDEFINED,
}
