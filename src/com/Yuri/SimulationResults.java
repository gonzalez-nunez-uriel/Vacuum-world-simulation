package com.Yuri;

public class SimulationResults {

    public int numberOfObstacles;
    public int numberOfDirtyTiles;
    public int numberOfSteps;
    public int numberOfDirtyTilesLeft;

    public void printSimulationResults(String extraMsg){
        System.out.println("\nSimulation results >> " + extraMsg);
        System.out.printf("number of obstacles: %s | number of dirty tiles: %s |\n",numberOfObstacles, numberOfDirtyTiles);
        System.out.printf("number of steps: %s | number of dirty tiles leftover: %s |\n",numberOfSteps,numberOfDirtyTilesLeft);
    }
}
