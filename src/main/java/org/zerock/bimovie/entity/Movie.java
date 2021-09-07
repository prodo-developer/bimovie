package org.zerock.bimovie.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "posterList")
@Table(name = "tbl_movie")
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    /**
     * @OneToMany와 mappedBy라는 속성을 이용해서
     * 실제 DB에서 자신은 연관관계의 주인이 아니라는 것을 명시합니다.
     * '연관관계의 주인'이라는 표현은 DB관계를 생각해보면 tbl_poster가 FK로 tbl_movie 참조
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Poster> posterList = new ArrayList<>();
}