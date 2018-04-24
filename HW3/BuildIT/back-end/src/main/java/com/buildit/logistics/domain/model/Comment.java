package com.buildit.logistics.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "comment_id")
    Long id;

    String comment;

    @ManyToOne
    @JoinColumn(name = "request_id")
    PlantHireRequest request;
}
