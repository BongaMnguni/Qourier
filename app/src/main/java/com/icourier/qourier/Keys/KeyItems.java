package com.icourier.qourier.Keys;

/**
 * Created by Payghost on 2/8/2018.
 */

public class KeyItems {

    private String keys;
    private String name;
    private String username;
    private String time;
    private String id;

    public KeyItems(String keys, String name, String username, String time,String id) {
        this.keys = keys;
        this.name = name;
        this.username = username;
        this.time = time;
        this.id = id;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
