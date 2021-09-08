package org.zerock.bimovie.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     *
     * 2021-09-08 추가기능
     * Movie객체가 JPA에서 엔티티 매니저에 의해 관리될 때 변수에 할당 되도록 List<Poster> 변수 초기화
     * cascade를 통해 Movie를 저장하면 Poster도 저장되게 처리
     */
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poster> posterList = new ArrayList<>();

    /**
     * Movie에서 Poster객체의 참조 관계를 같이 처리하도록 하면 엔티티 양방향으로 다룰 때 생기는 불편함을 없앨 수 있음.
     * @param poster
     */
    public void addPoster(Poster poster) {

        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    public void removePoster(Long ino) {
        Optional<Poster> result = posterList.stream().filter(p -> p.getIno() == ino).findFirst();

        result.ifPresent(poster -> {
            poster.setMovie(null);
            posterList.remove(poster);
        });

        // 포스터 번호 변경
        changeIdx();
    }

    private void changeIdx() {
        for (int i = 0; i < posterList.size(); i++) {
            posterList.get(i).setIdx(i);
        }
    }
}