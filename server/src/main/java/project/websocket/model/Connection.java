package project.websocket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import project.websocket.dto.ConnectionDTO;


@Entity
@Table(name = "CONNECTION")

public class Connection implements ConnectionDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONN_SEQ_NAME")
    @Column(name = "ID")
    private long id;


    @NotNull(message="{connection.time}")
    @Column(name = "CONN_TIME")
    private String conn_time;

    public Connection(String time) {
        this.conn_time = time;
    }

    //required by JPA, should not used
    protected Connection(){

    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getConn_time() {
        return conn_time;
    }

    public void setConn_time(String conn_time) {
        this.conn_time = conn_time;
    }
}
