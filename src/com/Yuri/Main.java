package com.Yuri;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Starting simulation\n");
        System.out.println("In this simulation a @ is used to indicate the location of the vacuum robot,");
        System.out.println("A * is used to denote a tile that the vacuum has already stepped at.");
        System.out.println("After the tile has been stepped once, a number will appear indicating how many times");
        System.out.println("the vacuum has stepped over the tile. If the tile has been stepped over more than 9 times");
        System.out.println("a % is going to be displayed instead.");
        System.out.println("An X indicates that a tile is dirty and \u25A0 is used to denote an obstacle.");
        System.out.println("The entire game board is surrounded by obstacles to simulate the walls of the room");
        System.out.println("\nType 'next' to acknowledge message");
        scanner.next();

        dirtOnlySimulation();
        dirtAndObstaclesSimulation();
        System.out.println("Type 'next' to continue");
        scanner.next();
        System.out.println("End of all simulations. Program execution complete");

    }

    public static void dirtOnlySimulation(){
        SimulationResults results;
        System.out.println("Simulation for the first agent dispersing 25% of dirt");
        results = simulationFirstAgent(8,0.25,0);
        results.printSimulationResults("Simulation for the first agent dispersing 25% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the first agent dispersing 50% of dirt");
        results = simulationFirstAgent(8,0.5,0);
        results.printSimulationResults("Simulation for the first agent dispersing 50% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the first agent dispersing 75% of dirt");
        results = simulationFirstAgent(8,0.75,0);
        results.printSimulationResults("Simulation for the first agent dispersing 75% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 25% of dirt");
        results = simulationSecondAgent(8,0.25,0);
        results.printSimulationResults("Simulation for the second agent dispersing 25% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 50% of dirt");
        results = simulationSecondAgent(8,0.5,0);
        results.printSimulationResults("Simulation for the second agent dispersing 50% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 75% of dirt");
        results = simulationSecondAgent(8,0.75,0);
        results.printSimulationResults("Simulation for the second agent dispersing 75% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("End of dirt only simulations \n__________________________________________\n\n");

    }//END F dirtOnlySimulation

    public static void dirtAndObstaclesSimulation(){
        SimulationResults results;
        System.out.println("Simulation for the first agent dispersing 25% of dirt and 25% of obstacles");
        results = simulationFirstAgent(8,0.25,0.25);
        results.printSimulationResults("Simulation for the first agent dispersing 25% of dirt and 25% of obstacles");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the first agent dispersing 25% of dirt and 25% of obstacles");
        results = simulationFirstAgent(8,0.25,0.5);
        results.printSimulationResults("Simulation for the first agent dispersing 25% of dirt and 25% of obstacles");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the first agent dispersing 20% of dirt and 75% of obstacles");
        results = simulationFirstAgent(8,0.2,0.75);
        results.printSimulationResults("Simulation for the first agent dispersing 20% of dirt and 75% of obstacles");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 25% of dirt");
        results = simulationSecondAgent(8,0.25,0.25);
        results.printSimulationResults("Simulation for the second agent dispersing 25% of dirt");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 25% of dirt and 50% of obstacles");
        results = simulationSecondAgent(8,0.25,0.5);
        results.printSimulationResults("Simulation for the second agent dispersing 25% of dirt and 50% of obstacles");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("Simulation for the second agent dispersing 20% of dirt and 75% of obstacles");
        results = simulationSecondAgent(8,0.2,0.75);
        results.printSimulationResults("Simulation for the second agent dispersing 20% of dirt and 75% of obstacles");
        System.out.println("Simulation complete. Type 'next' for the next experiment");
        scanner.next();

        System.out.println("End of dirt and obstacle simulations \n__________________________________________\n\n");

    }//END F dirtOnlySimulation

    public static SimulationResults simulationFirstAgent(int N, double dirtPercent, double obstaclePercent){
        Agent1 player = new Agent1();
        Environment room = new Environment(N, dirtPercent, obstaclePercent);
        Percept currentPercept; // to store the percept given from the environment to the agent
        Environment.moveType playerMove; // to store the decision made by the agent
        int count = 0; // to keep track of the loop and force its termination if necessary

        System.out.println("Game at start");
        room.printTileArray();
        room.printEnvironment();
        System.out.println("Starting game");
        while(room.isThereStillDirt()){
            currentPercept = room.getPercept();
            playerMove = player.decideNextMove(currentPercept.isDirty, currentPercept.tileStatus);
            System.out.printf("Move is: %s | ", playerMove);
            room.applyMove(playerMove);
            System.out.println("Room after move:");
            room.printEnvironmentSP();
            System.out.println("---------------------------------------");

            count++;
            //just in case
            if(count > 10*N*N) {
                System.out.println(count + " steps --- TERMINATING PROGRAM");
                break;
            }
        }
        player.resetAgent();
        SimulationResults results = new SimulationResults();
        results.numberOfDirtyTiles = room.numberOfDirtyTiles;
        results.numberOfObstacles = room.numberOfObstacles;
        results.numberOfSteps = room.stepsTaken;
        int dirtyTilesLeft = room.countDirtyTiles();
        results.numberOfDirtyTilesLeft = dirtyTilesLeft;
        return results;
    }//END F simulationFirstAgent

    public static SimulationResults simulationSecondAgent(int N, double dirtPercent, double obstaclePercent){
        Agent2 player = new Agent2();
        Environment room = new Environment(N, dirtPercent, obstaclePercent);
        Percept currentPercept; // to store the percept given from the environment to the agent
        Environment.moveType playerMove; // to store the decision made by the agent
        int count = 0; // to keep track of the loop and force its termination if necessary

        System.out.println("Game at start");
        room.printTileArray();
        room.printEnvironment();
        System.out.println("Starting game");
        while(room.isThereStillDirt()){
            currentPercept = room.getPercept();
            playerMove = player.decideNextMove(currentPercept.isDirty, currentPercept.tileStatus);
            System.out.printf("Move is: %s | ", playerMove);
            room.applyMove(playerMove);
            System.out.println("Room after move:");
            room.printEnvironmentSP();
            System.out.println("---------------------------------------");

            count++;
            //just in case
            if(count > 15*N*N) {
                System.out.println( count + " steps --- TERMINATING PROGRAM");
                break;
            }
        }
        SimulationResults results = new SimulationResults();
        results.numberOfDirtyTiles = room.numberOfDirtyTiles;
        results.numberOfObstacles = room.numberOfObstacles;
        results.numberOfSteps = room.stepsTaken;
        int dirtyTilesLeft = room.countDirtyTiles();
        results.numberOfDirtyTilesLeft = dirtyTilesLeft;
        return results;
    }//END F simulationSecondAgent
}
