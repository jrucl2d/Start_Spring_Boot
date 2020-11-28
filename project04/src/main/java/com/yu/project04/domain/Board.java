package com.yu.project04.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "replies")
@Entity
@EqualsAndHashCode(of="bno")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String writer;
    private String content;

    @CreationTimestamp
    private Timestamp regdate;
    @UpdateTimestamp
    private Timestamp updatdate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL) // 관계의 주체가 아니다. 매여있다. 댓글이 있으면 매여있어서 삭제 불가능하다.
    private List<Reply> replies;
}
