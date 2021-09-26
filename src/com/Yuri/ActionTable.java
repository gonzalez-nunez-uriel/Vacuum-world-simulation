package com.Yuri;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class ActionTable {
    Map<Environment.tileType,Environment.moveType> actions = new HashMap<Environment.tileType,Environment.moveType>();

    public void put(Environment.tileType tileStatus, Environment.moveType action) {
        actions.put(tileStatus, action);
    }

    public void remove(Environment.tileType tileStatus) {
        actions.remove(tileStatus);
    }

    public  Environment.moveType get(Environment.tileType tileStatus) {
        return actions.get(tileStatus);
    }

    public boolean containsKey(Environment.tileType tileStatus) {
        return actions.containsKey(tileStatus);
    }

    public Set<Environment.tileType> keySet(){
        return actions.keySet();
    }

    public String toString() {
        Environment.tileType tileStatus;
        Environment.moveType move;
        String msg="__________\n";
        // its my first time using sets and iterators so I dont know what is going on here, but it works
        Set<Environment.tileType> keys = keySet();
        Iterator<Environment.tileType> items = keys.iterator();
        while(items.hasNext()) {
            tileStatus = items.next();
            move = get(tileStatus);
            msg += "| ";
            msg += tileStatus;
            msg += " | ";
            msg += move;
            msg += " |\n";
        }
        msg +="----------";
        return msg;
    }
}
