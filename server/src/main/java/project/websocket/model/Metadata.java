package project.websocket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import project.websocket.controller.Message;
import project.websocket.dto.MetadataDTO;

@Entity
@Table(name = "METADATA")

public class Metadata implements MetadataDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSG_SEQ_NAME")
    @Column(name = "ID")
    private long id;


    @NotNull(message="{metadata.time}")
    @Column(name = "MSG_TIME")
    private String msgTime;

    private double latitude;
    private double longitude;

    private String username;
    private String message;


    public Metadata(Message msg) {
        this.username = msg.getUsername();
        this.message = msg.getMsg();
        this.latitude = msg.getLocation().getLatitude();
        this.longitude = msg.getLocation().getLongitude();
        this.msgTime = msg.getDate();
    }

    //required by JPA, should not used
    protected Metadata(){

    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getMsgTime() {
        return msgTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }













}
