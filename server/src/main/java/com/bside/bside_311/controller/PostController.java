package com.bside.bside_311.controller;

import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.AddPostResponseDto;
import com.bside.bside_311.dto.EditPostRequestDto;
import com.bside.bside_311.dto.PostDetailResponseDto;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.dto.QuoteInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시글", description = "게시글 API")
public class PostController {

  @Operation(summary = "게시글 등록 ", description = "게시글 등록 API")
  @PostMapping
  public AddPostResponseDto addPost(@ModelAttribute @Valid AddPostRequestDto addPostRequestDto) {
    log.info(">>> PostController.addPost");
    return null;
  }

  @Operation(summary = "게시글 수정", description = "게시글 수정 API")
  @PatchMapping
  public void editPost(@RequestBody @Valid EditPostRequestDto editPostRequestDto) {
    log.info(">>> PostController.editPost");
    return ;
  }

  @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
  @DeleteMapping
  public void deletePost() {
    log.info(">>> PostController.deletePost");
  }

  @Operation(summary = "게시글 목록 조회", description = "게시글 조회 API")
  @GetMapping
  public Page<PostResponseDto> getPost(Pageable pageable, @RequestParam(required = false) String keyWord) {
    log.info(">>> PostController.getPost");
    return null;
  }

  @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회 API")
  @GetMapping("/{post_no}")
  public PostResponseDto getPostDetail() {
    log.info(">>> PostController.getPostDetail");
    return null;
  }


  @Operation(summary = "인용 등록 ", description = "인용 등록 API")
  @PostMapping("/{postNo}/quotes/{quotedPostNo}")
  @ResponseStatus(HttpStatus.CREATED)
  public void addQuote(@PathVariable Long postNo, @PathVariable Long quotedPostNo) {
    log.info(">>> PostController.addQuote");
  }

  @Operation(summary = "인용 삭제", description = "인용 삭제 API")
  @DeleteMapping("/quotes/{quote_no}")
  public void deleteQuote() {
    log.info(">>> PostController.deleteQuote");
  }

  @Operation(summary = "인용 단건 조회", description = "인용 단건 조회 API")
  @GetMapping("/quotes/{quote_no}")
  public QuoteInfoDto getQuoteDetail() {
    log.info(">>> PostController.getQuoteDetail");
    return null;
  }

  @Operation(summary = "게시글 좋아요", description = "게시글 좋아요 API")
  @PostMapping("/like/{post_no}")
  public void likePost() {
    log.info(">>> PostController.likePost");
  }

  @Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요 취소 API")
  @PostMapping("/like-cancel/{post_no}")
  public void likeCancelPost() {
    log.info(">>> PostController.likeCancelPost");
  }

  @Operation(summary = "게시글 사진 첨부", description = "게시글 사진 첨부 API")
  @PostMapping("/{post_no}/attach")
  public void postAttachPicture() {
    log.info(">>> PostController.postAttachPicture");
  }

  @Operation(summary = "게시글 사진 삭제", description = "게시글 사진 삭제 API")
  @PostMapping("/{post_no}/attach/{attach_no}")
  public void postDeletePicture() {
    log.info(">>> PostController.postDeletePicture");
  }


}
