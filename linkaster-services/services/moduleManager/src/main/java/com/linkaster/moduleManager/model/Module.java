package com.linkaster.moduleManager.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "modules")
public class Module {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "module_owner_id", unique = true, nullable = false)
    private long moduleOwnerId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "module_code", unique = true, nullable = false)
    private String moduleCode;    

    @Column(name = "studentList")
    private List<Long> studentList;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EventModel> events;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Announcement> announcements;

    @Column(name = "type")
    private String type;


    // Add groupchat's RSA key here
}
