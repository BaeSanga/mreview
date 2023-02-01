package org.zerock.mreview.service;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewService {

    //영화의 모든 영화리뷰를 가져옴
    List<ReviewDTO> getListOfMovie(Long mno);

    //영화 리뷰를 추가
    Long register(ReviewDTO movieReviewDTO);            // register 메소드는 인터페이스니까 구현을 해줘야됨

    //특정한 영화리뷰 수정
    void modify(ReviewDTO movieReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewNum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO){           // 화면에 담아줌

        Review movieReview = Review.builder()                       // reviewnum, movie, member, grade, text를 get해서 movieReview를 생성해준다.
                .reviewnum(movieReviewDTO.getReviewnum())
                .movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
                .member(Member.builder().mid(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return movieReview;
    }

    default ReviewDTO entityToDto(Review movieReview){

        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }
}
