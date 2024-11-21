package com.linkaster.moduleManager.model;

import java.sql.Date;

import org.yaml.snakeyaml.events.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "modules")
public class Module {

    @Id
    @Column(name = "id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true,nullable = false)
    private String name;

    @Column(name = "code" , unique = true,nullable = false)
    private String code;    

    @Column(name = "studentId")
    private long[] studentId;

    @Column(name = "event")
    private EventModel[] event;

    @Column(name = "announcement")
    private String[] announcement;

    @Column(name = "type")
    private String type;
    
}
