package com.buildit.logistics.domain.model;

import com.buildit.logistics.domain.enums.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Creator {
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    Roles position;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "surname")
    String surname;
}
