package com.linkaster.moduleManager.model;

import java.sql.Date;
import java.util.Map;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;    

    @Column(name = "studentId")
    private long[] studentId;

    @Column(name = "room")
    private String room;

    @Column(name = "scheduleDay")
    private Date[] scheduleDay;

    @Column(name = "beginTime")
    private int[] beginTime;

    @Column(name = "endTime")
    private int[] endTime;

    @Column(name = "announcement")
    private String[] announcement;

    @Column(name = "assignment")
    private Map<Integer, AssignementModel> assignment;

    @Column(name = "type")
    private String type;
    
}
