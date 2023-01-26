package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // 특정 영화 리뷰 목록 출력
    @Override
    public List<ReviewDTO> getListOfMovie(Long mno){

        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    // 리뷰 등록
    @Override
    public Long register(ReviewDTO movieReviewDTO) {

        Review movieReview = dtoToEntity(movieReviewDTO);       // dtoToEntity 메소드를 호출해 movieReview 변수에 담아줌

        // 데이터베이스 review 테이블에서 movieReview 데이터를 저장시키는 시점에 reviewNum을 자동 생성을 해줌
        // save(저장) 메소드에 reviewNum을 자동 생성해줌
        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }

    // 리뷰 수정
    @Override
    public void modify(ReviewDTO movieReviewDTO) {

        Optional<Review> result =
                reviewRepository.findById(movieReviewDTO.getReviewnum());

        if(result.isPresent()){

            Review movieReview = result.get();
            movieReview.changeGrade(movieReviewDTO.getGrade());
            movieReview.changeText(movieReviewDTO.getText());

            reviewRepository.save(movieReview);
        }

    }

    // 리뷰 삭제
   @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}

