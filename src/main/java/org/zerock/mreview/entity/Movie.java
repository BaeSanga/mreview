package org.zerock.mreview.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// 영화 제목만을 가지는 구조
// M:N 관계를 처리할 때는 반드시 맵핑 테이블의 설계는 마지막 단계에서 처리하고 '명사'에 해당하는 클래스를 먼저 설계
//        -> 영화(Movie)와 회원(Member)의 존재가 명사에 해당이므로 먼저 설계
@Entity
// 삭제 로직이 수행되면 delete 쿼리 대신, update 쿼리를 통해 필드 값만 true로 변경시켜줘야함 이때 @SQLDelete를 사용
// @SQLDelete : 엔티티 삭제가 발생했을 때 delete 쿼리 대신 실행시켜줄 커스텀 sql 구문
@SQLDelete(sql = "UPDATE movie SET delYn = true WHERE mno = ?")
@Where(clause = "delYn = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    private boolean delYn = Boolean.FALSE;      // 삭제 여부 기본값 false

}
