package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // @EntityGraph : 엔티티의 특정한 속성을 같이 로딩하도록 표시하는 어노테이션

    // 예를 들어, 기본적으로 JPA는 FATCH 속성값을 LAZY로 지정하는 것이 일반적이다. 이런 상황에서 특정 기능을 수행할 때만 EAGER로딩을 하도록 지정할 수 있다.
    // @EntityGraph는 attributePaths 속성과 type 속성을 가진다.
    // attributePaths : 로딩 설정을 변경하고 싶은 속성의 이름을 배열로 명시
    // type : @EntityGraph를 어떤 방식으로 적용할 것인지 설정 , FATCH 속성값은 Paths에 명시한 속성을 EAGER 처리하고, 나머지는 LAZY처리 / LOAD 속성값은 Paths에 명시한 속성을 EAGER 처리하고, 나머지는 엔티티 클래스에 명시되거나 기본 방식으로 처리

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.movie = :member")
    void deleteByMember(Member member);
}
