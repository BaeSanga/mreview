package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 결과데이터 : ReviewDTO 리스트, 해당영화의 모든 리뷰 반환
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("--------------list---------------");
        log.info("MNO: " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);      // ResponseEntity : HTTP 요청 또는 응답에 해당하는 Header, Body를 포함하는 클래스
    }

    // 결과데이터 : 생성된 리뷰 번호 , 새로운 리뷰등록
    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO movieReviewDTO){       // 회원 ID, 리뷰 점수, text를 받아옵니다

        log.info("--------------add MovieReview---------------");                       // 코드리뷰할 때는 log 설명 굳이 안해도된다
        log.info("reviewDTO: " + movieReviewDTO);

        // 카멜케이스(중간 글자들은 대문자로 시작하지만 첫 글자가 소문자) 무조건 지키기 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Long reviewNum = reviewService.register(movieReviewDTO);        // reviewService에 있는 register 메소드를 호출해서 reviewNum에 담아준다
        // reviewNum는 고유번호, 식별자니까 수정, 삭제 시 reviewNum을 파라미터로 받아온다.
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);          // "객체"의 내용이 HTTP message body의 JSON 형태로 담겨지고, HTTP 상태코드를 전달하도록 한다.
    }

    // 결과데이터 : 리뷰의 수정 성공 여부, 리뷰수정
    @PutMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewNum,
                                             @RequestBody ReviewDTO movieReviewDTO){
        log.info("---------------modify MovieReview--------------" + reviewNum);
        log.info("reviewDTO: " + movieReviewDTO);

        reviewService.modify(movieReviewDTO);

        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewNum){
        log.info("---------------modify removeReview--------------");
        log.info("reviewNum: " + reviewNum);

        reviewService.remove(reviewNum);

        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

}

