package model;

import config.MazeConfig;
import geometry.RealCoordinates;

public class zhonya {
    public RealCoordinates pos = new RealCoordinates(7,8);
    public boolean exites = false; 
    public  boolean actif = false;
    public  long creationTime = System.nanoTime();

    public zhonya(RealCoordinates pos) {
        this.pos = pos;
    }



    public  boolean exites(){
        return exites;
    }

    public void setExites(boolean exites) {
        this.exites = exites;
    }

    public  RealCoordinates getPos() {
        return pos;
    }

    public  void setactif(boolean actif) {
        this.actif = actif;
    }

    public  boolean getactif() {
        return actif;
    }

    public  void setTime() {
        creationTime = System.nanoTime();
    }

    public  boolean fin(){
        long elapsedSeconds = (System.nanoTime() - creationTime) / 1_000_000_000;
        return elapsedSeconds >= 2;
    }




}
