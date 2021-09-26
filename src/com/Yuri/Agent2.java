package com.Yuri;

public class Agent2 {

    public Environment.moveType decideNextMove(boolean isDirty, Environment.tileType tileStatus) {
        int selection;

        //Number of random values matches number of possibilities -> all possibilities are equally likely
        switch(tileStatus) {
            case F: //If there are no obstacles, you can play any move

                selection = Environment.r.nextInt(4);
                switch(selection) {
                    case 0:
                        return Environment.moveType.N;
                    case 1:
                        return Environment.moveType.S;
                    case 2:
                        return Environment.moveType.W;
                    case 3:
                        return Environment.moveType.E;
                }

            case ON: //You cannot go NORTH

                selection = Environment.r.nextInt(3);
                switch(selection) {
                    case 0:
                        return Environment.moveType.S;
                    case 1:
                        return Environment.moveType.W;
                    case 2:
                        return Environment.moveType.E;
                }

            case OW: //You cannot go WEST

                selection = Environment.r.nextInt(3);
                switch(selection) {
                    case 0:
                        return Environment.moveType.N;
                    case 1:
                        return Environment.moveType.S;
                    case 2:
                        return Environment.moveType.E;
                }

            case OE: //You cannot go EAST
                selection = Environment.r.nextInt(3);
                switch(selection) {
                    case 0:
                        return Environment.moveType.N;
                    case 1:
                        return Environment.moveType.S;
                    case 2:
                        return Environment.moveType.W;
                }

            case OS: //You cannot go SOUTH
                selection = Environment.r.nextInt(3);

                switch(selection) {
                case 0:
                    return Environment.moveType.N;
                case 1:
                    return Environment.moveType.W;
                case 2:
                    return Environment.moveType.E;
                }

            case ONS: //You cannot go NORTH or SOUTH
                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.W;
                else
                    return Environment.moveType.E;

            case ONW: //You cannot go NORTH or WEST

                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.S;
                else
                    return Environment.moveType.E;

            case ONE: //You cannot go NORTH or EAST

                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.S;
                else
                    return Environment.moveType.W;

            case OSW: //You cannot go SOUTH or WEST

                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.N;
                else
                    return Environment.moveType.E;

            case OSE: //You cannot go SOUTH and EAST

                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.N;
                else
                    return Environment.moveType.W;

            case OWE: //You cannot go WEST or EAST
                selection = Environment.r.nextInt(2);
                if(selection == 0)
                    return Environment.moveType.N;
                else
                    return Environment.moveType.S;

            case ONSW:
                return Environment.moveType.E;

            case ONSE:
                return Environment.moveType.W;

            case ONWE:
                return Environment.moveType.S;

            case OSWE:
                return Environment.moveType.N;


            default: //The compiler wouldn't stop complaining about needing a default even though I covered all cases
                // if the percept is of type X or O this will be triggered, but this should not be possible
                // decideNextMove should never make a move that ends up in such a situation
                System.out.println("Something whent wrong");
                return null;

        }
    }

}
