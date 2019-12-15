package project.websocket.controller;

import java.io.Serializable;

import project.websocket.model.Metadata;

public class Message implements Serializable {
    
    public class LatLng implements Serializable{
        private static final long serialVersionUID = 1L;
        double latitude;
        double longitude;

        public LatLng(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    private static final long serialVersionUID = 1L;
    private String username;
    private String msg;
    private LatLng location;


    private String date;

    public Message(Metadata metadata) {
        this.username = metadata.getUsername();
        this.msg = metadata.getMessage();
        this.location = new LatLng(metadata.getLatitude(), metadata.getLongitude());
        this.date = metadata.getMsgTime();
    }

    public Message() {

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}