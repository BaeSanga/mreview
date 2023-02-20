package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(
            "select m, mi, avg(coalesce(r.grade,0)), count(r) from Movie m "
                    + "left outer join MovieImage mi on mi.movie = m "
                    + "left outer join Review r on r.movie = m "
                    + "where m.delYn = 'N' "
                    + "group by m "

    )
    Page<Object[]> getListPage(Pageable pageable);      // 페이지 처리

    @Query("select m, mi ,avg(coalesce(r.grade,0)),  count(r)" +
            " from Movie m left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review  r on r.movie = m "+
            " where m.mno = :mno group by mi"
    )
    List<Object[]> getMovieWithAll(Long mno);           // 특정 영화 조회

    // 삭제 N -> Y
    @Modifying
    @Transactional
    @Query("update Movie set delYn = 'Y' where mno = :mno")
    void deleteMovie(long mno);

    @Query ("update Movie set title = '?' where mno = :mno")
    void modifyMovie(long mno);

}