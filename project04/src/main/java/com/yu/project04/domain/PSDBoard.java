package com.yu.project04.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "files")
@Entity
@EqualsAndHashCode(of="pid")
public class PSDBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String pname;
    private String pwriter;

    @OneToMany(cascade = CascadeType.ALL) // 영속성 전이를 모두 같게
    @JoinColumn(name="psdno") // pdsno라는 이름의 외래키가 File에 생김
    private List<PSDFile> files;
}
