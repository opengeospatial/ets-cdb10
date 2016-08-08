package org.opengis.cite.cdb10;

/**
 * An enumerated type defining all recognized test run arguments.
 */
public enum TestRunArg {

    /**
     * An absolute URI that refers to a representation of the test subject or
     * metadata about it.
     */
    IUT, ICS, DIRECTORIES,LATLONG,MINMAXLOD;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
