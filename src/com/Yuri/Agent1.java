package com.Yuri;

public class Agent1 {

    private static Environment.moveType desiredMove = Environment.moveType.E; // not supposed to be set to South
    private boolean changeFlag = true; // adds a change in behavior that prevents premature cycling

    public Environment.moveType decideNextMove(boolean isDirty, Environment.tileType tileStatus) {

        // Special cases
        if(tileStatus == Environment.tileType.ONSW){
            desiredMove = Environment.moveType.E;
            return Environment.moveType.E;
        }
        if(tileStatus == Environment.tileType.ONSE){
            desiredMove = Environment.moveType.W;
            return Environment.moveType.W;
        }
        if(tileStatus == Environment.tileType.ONWE){
            desiredMove = Environment.moveType.E;
            return Environment.moveType.S;
        }
        if(tileStatus == Environment.tileType.OSWE){
            desiredMove = Environment.moveType.N;
            return Environment.moveType.N;
        }

        // Normal cases
        if(desiredMove ==  Environment.moveType.E){
            if(tileStatus == Environment.tileType.OE ||
                    tileStatus == Environment.tileType.ONE){
                desiredMove = Environment.moveType.W; // changing direction to West
                return Environment.moveType.S;
            } else if(tileStatus == Environment.tileType.OSE ){
                if(changeFlag){
                    changeFlag = false;
                    desiredMove = Environment.moveType.W; // changing direction to West
                    return Environment.moveType.W;
                } else{
                    changeFlag = true;
                    desiredMove = Environment.moveType.N; // changing direction to North
                    return Environment.moveType.N;
                }

            }  else if(tileStatus == Environment.tileType.OWE ){
                desiredMove = Environment.moveType.N; // changing direction to North
                return Environment.moveType.N;
            }

            else{
                return desiredMove;
            }
       }

       if(desiredMove == Environment.moveType.W){
           if(tileStatus == Environment.tileType.OW ||
                   tileStatus == Environment.tileType.ONW){
               desiredMove = Environment.moveType.E; // changing direction toEast
               return Environment.moveType.S;
           } else if(tileStatus == Environment.tileType.OSW ){
               if(changeFlag){
                   changeFlag =false;
                   desiredMove = Environment.moveType.E; // changing direction to East
                   return Environment.moveType.E;
               } else{
                   changeFlag=true;
                   desiredMove = Environment.moveType.N; // changing direction to North
                   return Environment.moveType.N;
               }

           } else if(tileStatus == Environment.tileType.OWE ){
               desiredMove = Environment.moveType.N; // changing direction to North
               return Environment.moveType.N;
           }

           else{
               return desiredMove;
           }
       }

       if(desiredMove == Environment.moveType.N){

           if(tileStatus == Environment.tileType.F ||
                   tileStatus == Environment.tileType.OW ||
                   tileStatus == Environment.tileType.OE ||
                   tileStatus == Environment.tileType.OWE ||
                   tileStatus == Environment.tileType.OSW ||
                   tileStatus == Environment.tileType.OSE ){
               return desiredMove;
           }
           if(tileStatus == Environment.tileType.ON ||
                   tileStatus == Environment.tileType.ONW){
               desiredMove = Environment.moveType.E; // changing direction to East
               return desiredMove;
           } else if(tileStatus == Environment.tileType.ONE){
               desiredMove = Environment.moveType.W; // changing direction to West
               return desiredMove;
           }


       }
        return null;
    }// END F decideNextMove

    public void resetAgent(){
        desiredMove =  Environment.moveType.E;
        changeFlag = true;
    }




}
