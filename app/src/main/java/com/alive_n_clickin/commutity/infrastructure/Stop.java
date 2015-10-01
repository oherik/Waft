package com.alive_n_clickin.commutity.infrastructure;

/**
 * Created by OscarEvertsson on 01/10/15.
 */
public class Stop {
    private String name;
    private long id;
    private double lat;
    private double lon;
    private char track;

    /**
     * @returns null if no track are specified or otherwise a char in capital letter.
     */
    public char getTrack(){
        return this.track;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLon(){
        return this.lon;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", track=" + track +
                '}';
    }
}
