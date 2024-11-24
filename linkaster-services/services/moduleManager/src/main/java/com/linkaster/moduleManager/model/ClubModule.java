package com.linkaster.moduleManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Table(name = "club_modules")
public class ClubModule extends Module {
    
    @Column(name = "clubLeaderStudentId")
    private String clubLeaderStudentId;

    @Column(name = "clubLeader")
    private String clubLeader;
}
