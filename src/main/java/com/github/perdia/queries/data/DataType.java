package com.github.perdia.queries.data;

/**
 * Enum containing all possible datatypes an entry in the database can have.
 * This enum contains Long and Double instead of Integer and Float since Perdia-DB uses Rusts 64-bit Integers and
 * Floats, and Javas Integer and Float types are only 32-bit.
 */
public enum DataType {
    /**
     * DataType for storing Strings
     */
    STRING,
    /**
     * DataType for storing Longs (64-bit Integers)
     */
    INTEGER,
    /**
     * DataType for storing Doubles (64-bit Floats)
     */
    FLOAT,
    /**
     * DataType for entries that have no value yet
     */
    UNDEFINED,
}
