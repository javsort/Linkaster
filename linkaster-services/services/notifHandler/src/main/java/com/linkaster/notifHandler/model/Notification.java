package com.linkaster.notifHandler.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id                                         //notification id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "receiver")                  //receiver id
    private long receiver;

    @Column(name = "sender")                    //name of sender
    private String sender;

    @Column(name = "timesent")                  //timestamp, must be set when notif is created
    private LocalDateTime timesent;

    @Column(name = "contents")                  //actual message
    private String contents;

    @Column(name = "location")                  //location of the chat (or tab) that sent the notif. for easy access to origin
    private String location;
}