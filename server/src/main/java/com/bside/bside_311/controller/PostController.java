package com.bside.bside_311.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public void addPost() {
    log.info(">>> PostController.addPost");
  }

  @Operation(summary = "게시글 수정", description = "게시글 수정 API")
  @PatchMapping
  public void editPost() {
    log.info(">>> PostController.editPost");
  }

  @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
  @DeleteMapping
  public void deletePost() {
    log.info(">>> PostController.deletePost");
  }

  @Operation(summary = "게시글 목록 조회", description = "게시글 조회 API")
  @GetMapping
  public void getPost() {
    log.info(">>> PostController.getPost");
  }

  @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회 API")
  @GetMapping("/{post_no}")
  public void getPostDetail() {
    log.info(">>> PostController.getPostDetail");
  }


  @Operation(summary = "인용 등록 ", description = "인용 등록 API")
  @PostMapping("/quotes")
  public void addQuote() {
    log.info(">>> PostController.addQuote");
  }

  @Operation(summary = "인용 삭제", description = "인용 삭제 API")
  @DeleteMapping("/quotes/{quote_no}")
  public void deleteQuote() {
    log.info(">>> PostController.deleteQuote");
  }

  @Operation(summary = "인용 단건 조회", description = "인용 단건 조회 API")
  @GetMapping("/quotes/{quote_no}")
  public void getQuoteDetail() {
    log.info(">>> PostController.getQuoteDetail");
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
