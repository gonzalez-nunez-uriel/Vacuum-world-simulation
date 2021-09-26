package com.Yuri;

import java.util.Random;

public class Environment {
    /*

    This class is intended to model a square room with tiles. Each tile can be clean or dirty.
    There can be obstacles in the room. The environment keeps track of where a vacuum cleaner is at and in what
    direction it can move.

     */

    public static enum tileType {
        // main component of the percept - it tell the agent what moves its allowed to do at each tile
        F, // free -- you can go in any direction
        X, // unreachable -- used to prevent dirt from being placed in areas where the agent cannot reach it
        O, // obstacle
        ON, OS, OW, OE, // obstacle north
        ONS, ONW, ONE, OSW, OSE, OWE, // obstacle north and south
        ONSW, ONSE, ONWE, OSWE; // obstacle north, south, and west
    }

    public static enum moveType {
        N,S,W,E,
        X; // exit prompt
    }

    public static Random r =  new Random(); // to generate random numbers

    private int[][] steppedArray; // stores the number of times a tile has been passed by
    private boolean[][] dirtArray; // indicates whether a tile is clean(false) or dirty(true)
    private tileType[][] tileArray; // stores the type of tile the vacuum is at -- it determines which moves it can make
    private int size; // stores the size of the arrays -- not the same as the size given by user
    private int count; // to keep track of how many steps the agent has taken
    //Player coordinates
    public int x;
    public int y;
    public int stepsTaken;
    public int numberOfDirtyTiles;
    public int numberOfObstacles;

    public Environment(int N, double dirtPercent, double obstaclePercent) {
        if(dirtPercent + obstaclePercent > 1) {
            System.out.println("Percentages are not consistent");
        }

        // Allocating memory
        // Extra memory will be used, but its to keep things simple
        // Memory wasted is 2(N+2) + 2N
        this.size = N+2;
        this.steppedArray = new int[N+2][N+2]; // uses extra memory
        this.dirtArray = new boolean[N+2][N+2]; // uses extra memory
        this.tileArray = new tileType[N+2][N+2];
        stepsTaken = 0;

        // Order of initialization does not matter
        initializeSteppedArray();
        initializeDirtArray();
        initializeTileArray();

        // Order of assignment matters -- dirt is not supposed to be assigned on a tile of type X
        assignRandomObstacles(obstaclePercent);
        assignRandomDirt(dirtPercent);
        adjustTileValues(); // after the obstacles and dirt are assigned, the other type of tiles can be assigned


        //Assigns the player a random location in the board
        initializeAgentLocation();
        //Its hard to code a no-move. The vacuum cleans as soon as it spawns
        if(dirtArray[y][x] == true){
            this.dirtArray[y][x] = false;
            stepsTaken++;
        }
        this.steppedArray[y][x]++; // this happens no matter what upon spawn

    }//END CONSTRUCTOR -- The remaining functions are listed in alphabetical order

    public void adjustTileValues() {
        // store the values around a tile to assign tile type
        tileType valN;
        tileType valS;
        tileType valW;
        tileType valE;
        // We only need to look at the interior tiles, not at the ones in the border
        for(int i = 1; i < size-1; i++) {
            for(int j = 1; j < size-1; j++) {
                if(tileArray[i][j] == tileType.O) {
                    // ignore
                } else {
                    // Get values at all directions. Upon call the only values are O and F
                    valN = tileArray[i-1][j];
                    valS = tileArray[i+1][j];
                    valW = tileArray[i][j-1];
                    valE = tileArray[i][j+1];

                    if(valN == tileType.O &&  !(valS == tileType.O) && !(valW == tileType.O) && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.ON;
                    }
                    if( !(valN == tileType.O) &&  valS == tileType.O && !(valW == tileType.O) && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.OS;
                    }
                    if( !(valN == tileType.O) && !(valS == tileType.O) && valW == tileType.O && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.OW;
                    }
                    if( !(valN == tileType.O) && !(valS == tileType.O) && !(valW == tileType.O) && valE == tileType.O) {
                        tileArray[i][j] = tileType.OE;
                    }
                    if(valN == tileType.O && valS == tileType.O && !(valW == tileType.O) && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.ONS;
                    }
                    if(valN == tileType.O && !(valS == tileType.O) && valW == tileType.O && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.ONW;
                    }
                    if(valN == tileType.O && !(valS == tileType.O) && !(valW == tileType.O) && valE == tileType.O) {
                        tileArray[i][j] = tileType.ONE;
                    }
                    if( !(valN == tileType.O) && valS == tileType.O && valW == tileType.O && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.OSW;
                    }
                    if( !(valN == tileType.O) && valS == tileType.O && !(valW == tileType.O) && valE == tileType.O) {
                        tileArray[i][j] = tileType.OSE;
                    }
                    if( !(valN == tileType.O) && !(valS == tileType.O) && valW == tileType.O && valE == tileType.O) {
                        tileArray[i][j] = tileType.OWE;
                    }
                    if(valN == tileType.O && valS == tileType.O && valW == tileType.O && !(valE == tileType.O) ) {
                        tileArray[i][j] = tileType.ONSW;
                    }
                    if(valN == tileType.O && valS == tileType.O && !(valW == tileType.O) && valE == tileType.O) {
                        tileArray[i][j] = tileType.ONSE;
                    }
                    if(valN == tileType.O && !(valS == tileType.O) && valW == tileType.O && valE == tileType.O) {
                        tileArray[i][j] = tileType.ONWE;
                    }
                    if( !(valN == tileType.O) && valS == tileType.O && valW == tileType.O && valE == tileType.O) {
                        tileArray[i][j] = tileType.OSWE;
                    }
                    if( valN == tileType.O && valS == tileType.O && valW == tileType.O && valE == tileType.O) {
                        tileArray[i][j] = tileType.X; // the tile is unreachable
                    }
                }
            }
        }
    }//END F adjustTileValues

    public void applyMove(moveType move) {
        // A move is 'processed' by updating the x and y variables stored in this class
        switch(move) {
            case N:
                y--;
                dirtArray[y][x] = false;
                steppedArray[y][x]++;
                stepsTaken++;
                break;
            case S:
                y++;
                dirtArray[y][x] = false;
                steppedArray[y][x]++;
                stepsTaken++;
                break;
            case E:
                x++;
                dirtArray[y][x] = false;
                steppedArray[y][x]++;
                stepsTaken++;
                break;
            case W:
                x--;
                dirtArray[y][x] = false;
                steppedArray[y][x]++;
                stepsTaken++;
        }
    }//END F applyMove

    public void assignRandomDirt(double percent) {
        System.out.println("Assiging dirt");
        int i,j;
        int K = (int) Math.ceil( percent * Math.pow(size-2,2) ); // the size needs to be corrected to account for the borders
        numberOfDirtyTiles = K;

        int count = 0; // it might be the case that there are too many obstacles and there is no more room for the dirt in the desired proportion

        while(K > 0) {
            if(count > 10*size*size) { // time-out condition -- arbitrary, maybe something better can be used instead
                // The environment still has dirt and the vacuum can still clean, it just wont be in the desired proportion
                System.out.println("Max number of iterations allowed. Dirt assignment halted");
                break;
            }

            // generates random numbers in [1,N-1]
            i = 1 + r.nextInt(size-1);
            j = 1 + r.nextInt(size-1);
            boolean val1 = dirtArray[i][j];
            tileType val2 = tileArray[i][j];
            if(!val1) {//if val is false --> if tile is clean
                // CAREFUL -- For high percentages of obstacles, it is possible for there to be no room for dirt -- an infinite loop
                // I am adding a STOP count, just in case
                if(val1 != true && val2 != tileType.O && val2 != tileType.X) { // if no dirt has been assigned already and there is no obstacle and the area is reachable
                    dirtArray[i][j] = true;
                    K--;
                }
            }
            count++;
        }

        System.out.println("Finished assigning dirt");

    }//END F assignRandomDirt

    public void assignRandomObstacles(double percent) {
        // In case there are no obstacles
        if(percent == 0) {
            return;
        }

        System.out.println("Assiging obstacles");
        int i,j;
        int K = (int) Math.ceil( percent * Math.pow(size-2,2) ); // the size needs to be corrected to account for the borders
        numberOfObstacles = K;

        int count = 0; // just in case the user assigns too many tiles. In theory you should not be able to assign
        // more objects than there are tiles, but one should always be careful

        while(K > 0) {
            if(count > 10*size*size) { // time-out condition -- arbitrary, maybe something better can be used instead
                // The environment still has dirt and the vacuum can still clean, it just wont be in the desired proportion
                System.out.println("Max number of iterations allowed. Dirt assignment halted");
                break;
            }

            // generates random numbers in [1,N-1]
            i = 1 + r.nextInt(size-1);
            j = 1 + r.nextInt(size-1);
            tileType val = tileArray[i][j];
            if(!(val == tileType.O)) { // if there is no obstacle at (i,j) -- to avoid assigning an obstacle to the same tile more than once
                tileArray[i][j] = tileType.O;
                K--;
            }
            count++;
        }

        System.out.println("Finished assigning obstacales");
    }//END F assignRandomObstacles

    //for debugging
    public int countObstacles() {
        int count = 0;
        for(int i=0; i<size; i++) {
            for(int j = 0; j<size; j++) {
                if(tileArray[i][j] == tileType.O) {
                    count++;
                }
            }
        }
        count = count - 4*size+4;
        System.out.println("number of obstacles: " + count);
        return count;
    }//END F countObstacles

    //for debugging
    public int countDirtyTiles() {
        int count = 0;
        for(int i=0; i<size; i++) {
            for(int j = 0; j<size; j++) {
                if(dirtArray[i][j]) {
                    count++;
                }
            }
        }
        System.out.println("number of dirty tiles left: " + count);
        return count;
    }//END F countTrues

    public Percept getPercept() {
        boolean isCurrentTileDirty = dirtArray[y][x];
        tileType currentTileStatus = tileArray[y][x];
        return new Percept(isCurrentTileDirty, currentTileStatus);
    }//END F getPercept

    public void initializeDirtArray(){
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                dirtArray[i][j] = false; // initially the room is clean
            }
        }
        System.out.println("Dirt array initialized");
    }//END F initializeDirtArray

    public void initializeAgentLocation() {
        int i, j;
        // used to control execution -- it stays true until a tile where the agent can spawn has been found
        boolean flag = true;

        while(flag) {
            i = r.nextInt(size);
            j = r.nextInt(size);
            // For debugging
            //System.out.printf(">> %d %d", i,j);
            //System.out.println(tileArray[i][j]);
            if( !(tileArray[i][j] == tileType.O || tileArray[i][j] == tileType.X) ) { // if the tile is not inaccessible or it does not have an obstacle
                y = i;
                x = j;
                flag = false;
            }
        }
    }// END F initializeAgentLocation

    public void initializeTileArray() {
        // initialize top and bottom rows
        for(int i = 0; i < size; i++) {
            tileArray[0][i] = tileType.O;
            tileArray[size-1][i] = tileType.O;
        }
        // initialize first and last columns
        for(int i = 1; i < size - 1; i++) {
            tileArray[i][0] = tileType.O;
            tileArray[i][size-1] = tileType.O;
        }
        // initialize interior tiles
        for(int i = 1; i < size-1; i++) {
            for(int j = 1; j < size-1; j++) {
                tileArray[i][j] = tileType.F; // intilially all tiles are free

            }
        }
        System.out.println("Tile array initialized");
    } //END F initializeTileArray

    public void initializeSteppedArray(){
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                steppedArray[i][j] = 0; // all counts start with zero
            }
        }
    }//END F initializeSteppedArray

    // used to terminate the game (simulation)
    // for debugging
    public boolean isThereStillDirt() {
        for(int i=0;i<size;i++) {
            for(int j=0; j<size;j++) {
                if(dirtArray[i][j] == true)
                    return true;
            }
        }
        return false;
    }//END F isThereStillDirt

    public void printDirtArray() {
        String msg;
        for(int i = 0; i < size; i++) {
            System.out.print("| ");
            for(int j = 0; j < size; j++) {
                msg = String.format("%8s", dirtArray[i][j]);
                System.out.print(msg);
            }
            System.out.println(" |");
        }
    }//END F printDirtArray

    public void printEnvironment() {
        String msg;
        boolean val1;
        tileType val2;
        System.out.println("Step: " + stepsTaken);
        for(int i = 0; i < size; i++) {
            System.out.print("|");
            for(int j = 0; j < size; j++) {

                if(i == this.y && j == this.x) {
                    msg = String.format("%2s", "@");
                    System.out.print(msg);
                } else {
                    val1 = dirtArray[i][j];
                    val2 = tileArray[i][j];
                    if(val2 == tileType.O)
                        msg = String.format("%2s", "O");
                    else {
                        if(val1)
                            msg = String.format("%2s", "D");
                        else {
                            if(steppedArray[i][j] == 1)
                                msg = String.format("%2s", "X");
                            else if(steppedArray[i][j] > 1 && steppedArray[i][j] < 10 )
                                msg = String.format("%2d", steppedArray[i][j]);
                            else if(steppedArray[i][j] >= 10)
                                msg = String.format("%2s", "*");
                            else
                                msg = String.format("%2s", " ");
                        }

                    }
                    System.out.print(msg);
                }
            }
            System.out.println("|");
        }
    }//END F printEnvironment

    public void printEnvironmentSP() {
        String msg;
        boolean val1;
        tileType val2;
        System.out.println("Step: " + stepsTaken);
        for(int i = 0; i < size; i++) {
            System.out.print("|");
            for(int j = 0; j < size; j++) {

                if(i == this.y && j == this.x) {
                    msg = String.format("%2s", "\u0040");
                    System.out.print(msg);
                } else {
                    val1 = dirtArray[i][j];
                    val2 = tileArray[i][j];
                    if(val2 == tileType.O)
                        msg = String.format("%2s", "\u25A0"); //  \u25A0 \u2b1b
                    else {
                        if(val1)
                            msg = String.format("%2s", "X");
                        else {
                            if(steppedArray[i][j] == 1)
                                msg = String.format("%2s", "*"); // \u255AA
                            else if(steppedArray[i][j] > 1 && steppedArray[i][j] < 10 )
                                msg = String.format("%2d", steppedArray[i][j]);
                            else if(steppedArray[i][j] >= 10)
                                msg = String.format("%2s", "\u0025");
                            else
                                msg = String.format("%2s", "  "); // \u0020
                        }

                    }
                    System.out.print(msg);
                }
            }
            System.out.println("|");
        }
    }//END F printEnvironment

    public void printPlayerLocation() {
        System.out.printf("Player location is x=%d y=%d\n",x,y);
    }//END F printPLayerLocation

    //Prints the tile array. It is for debugging.
    public void printTileArray() {
        String msg;
        for(int i = 0; i < size; i++) {
            System.out.print("| ");
            for(int j = 0; j < size; j++) {
                msg = String.format("%6s", tileArray[i][j]);
                System.out.print(msg);
            }
            System.out.println(" |");
        }
    }//END F printTileArray

}
