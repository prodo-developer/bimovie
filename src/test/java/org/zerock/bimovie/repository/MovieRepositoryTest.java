package org.zerock.bimovie.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.bimovie.entity.Movie;
import org.zerock.bimovie.entity.Poster;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("영화 데이터 넣기 테스트")
    public void testInsert() {
        System.out.println("testInsert....................");

        Movie movie = Movie.builder()
                .title("극한직업")
                .build();

        movie.addPoster(Poster.builder().fname("극한직업포스터1.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터2.jpg").build());

        movieRepository.save(movie);

        System.out.println(movie.getMno());
    }

    @Test
    @Transactional
    @Commit
    public void testRemovePoster() {

        Movie movie = movieRepository.getOne(1L);
        movie.removePoster(2L);

        movieRepository.save(movie);
    }
}