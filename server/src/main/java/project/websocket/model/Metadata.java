package project.websocket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import project.websocket.dto.MetadataDTO;

import java.sql.Time;


@Entity
@Table(name = "METADATA")

public class Metadata implements MetadataDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSG_SEQ_NAME")
    @Column(name = "ID")
    private long id;


    @NotNull(message="{metadata.time}")
    @Column(name = "MSG_TIME")
    private Time msg_time;

    public Metadata(Time time) {
        this.msg_time = time;
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
    
    public Time getMsg_time() {
        return msg_time;
    }

    public void setConn_time(Time msg_time) {
        this.msg_time = msg_time;
    }











}
