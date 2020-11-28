package com.yu.project04.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of="uid")
@Entity
public class Member {
    @Id
    private String uid;
    private String upw;
    private String uname;
}
