package com.bside.bside_311.controller;

import com.bside.bside_311.config.security.UserRequired;
import com.bside.bside_311.dto.AddCommentRequestDto;
import com.bside.bside_311.dto.AddCommentResponseDto;
import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.AddPostResponseDto;
import com.bside.bside_311.dto.AddQuoteResponseDto;
import com.bside.bside_311.dto.EditCommentRequestDto;
import com.bside.bside_311.dto.EditPostRequestDto;
import com.bside.bside_311.dto.GetPostCommentsResponseDto;
import com.bside.bside_311.dto.GetPostResponseDto;
import com.bside.bside_311.dto.GetQuotesByPostResponseDto;
import com.bside.bside_311.dto.PostResponseDto;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.service.AttachService;
import com.bside.bside_311.service.PostService;
import com.bside.bside_311.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시글", description = "게시글 API")
public class PostController {
  private final PostService postService;
  private final AttachService attachService;

  @Operation(summary = "[o]게시글 등록 ", description = "게시글 등록 API")
  @UserRequired
  @PostMapping
  public AddPostResponseDto addPost(@RequestBody @Valid AddPostRequestDto addPostRequestDto) {
    log.info(">>> PostController.addPost");
    return postService.addPost(Post.of(addPostRequestDto), addPostRequestDto.getAlcoholNo(),
        addPostRequestDto.getAlcoholFeature(), addPostRequestDto.getTagList());
  }

  @Operation(summary = "[o]게시글 수정", description = "게시글 수정 API")
  @UserRequired
  @PatchMapping("/{postNo}")
  public void editPost(@PathVariable("postNo") Long postNo,
                       @RequestBody @Valid EditPostRequestDto editPostRequestDto) {
    log.info(">>> PostController.editPost");

    postService.editPost(postNo, editPostRequestDto);
    return;
  }

  @Operation(summary = "[o]게시글 삭제", description = "게시글 삭제 API")
  @UserRequired
  @DeleteMapping("/{postNo}")
  public void deletePost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.deletePost");
    postService.deletePost(postNo);
  }

  @Operation(summary = "[o]게시글 목록 조회(v1)", description = "게시글 조회 API")
  @GetMapping
  public GetPostResponseDto getPosts(@RequestParam(name = "page", defaultValue = "0")
                                     @Schema(description = "페이지번호(0부터), 기본값 0.", example = "0")
                                     Long page,
                                     @RequestParam(name = "size", defaultValue = "10")
                                     @Schema(description = "사이즈, 기본값 10.", example = "10")
                                     Long size,
                                     @RequestParam(required = false, name = "orderColumn")
                                     @Schema(description = "정렬 컬럼", example = "post_no")
                                     String orderColumn,
                                     @RequestParam(required = false, name = "orderType")
                                     @Schema(description = "정렬 타입", example = "DESC")
                                     String orderType,
                                     @RequestParam(required = false, name = "searchKeyword")
                                     @Schema(description = "키워드", example = "키워드")
                                     String searchKeyword,
                                     @RequestParam(required = false, name = "searchUserNos")
                                     @Schema(description = "검색 유저 번호들.", example = "1,2,4")
                                     String searchUserNos

  ) {
    log.info(">>> PostController.getPost");
    List<Long> searchUserNoList = new ArrayList<>();
    try {
      searchUserNoList =
          Arrays.stream(searchUserNos.split(",")).map(Long::parseLong).toList();
    } catch (NumberFormatException e) {
      log.error(">>> PostController.getPost searchUserNos 파싱 에러 NumberFormatException", e);
    } catch (Exception e) {
      log.error(">>> PostController.getPost searchUserNos 파싱 에러 Exception", e);
    }
    return postService.getPosts(page, size, orderColumn, orderType, searchKeyword,
        searchUserNoList);
  }

  @Operation(summary = "[o]게시글 목록 조회(v2)", description = "게시글 조회 API Page, size 사용법. <br> ex1) /posts/v2?page=0&size=10&sort=id,desc <br> ex2) /posts/v2?page=0&size=10&sort=id,desc&sort=content,asc&searchKeyword=키워드&searchUserNos=1,2,4")
  @GetMapping("/v2")
  public Page<PostResponseDto> getPostsV2(
//                                        @Schema(description = "페이지 번호와 사이즈.정렬 까지.(0부터) ex)[1]page=0&size=5&sort=id,desc [2]page=1&size=15&sort=id,desc&sort=content,asc", example = "0")
      Pageable pageable,
      @RequestParam(required = false, name = "searchKeyword")
      @Schema(description = "키워드", example = "키워드")
      String searchKeyword,
      @RequestParam(required = false, name = "searchUserNos")
      @Schema(description = "검색 유저 번호들.", example = "1,2,4")
      String searchUserNos,
      @RequestParam(required = false, name = "isLikedByMe")
      @Schema(description = "나에 의해서 좋아하는 게시글 필터 여부(true or false).", example = "false")
      Boolean isLikedByMe,
      @RequestParam(required = false, name = "isCommentedByMe")
      @Schema(description = "내가 댓글을 단 게시글 필터 여부.(true or false)", example = "false")
      Boolean isCommentedByMe,
      @RequestParam(required = false, name = "searchAlcoholNos")
      @Schema(description = "검색 술 번호들.", example = "1,2,4")
      String searchAlcoholNos

  ) {
    log.info(">>> PostController.getPost");
    List<Long> searchUserNoList = new ArrayList<>();
    if (StringUtils.hasText(searchUserNos)) {
      try {
        searchUserNoList =
            Arrays.stream(searchUserNos.split(",")).map(Long::parseLong).toList();
      } catch (NumberFormatException e) {
        log.error(">>> PostController.getPost searchUserNos 파싱 에러 NumberFormatException", e);
        throw new IllegalArgumentException("searchUserNos 파싱 에러 NumberFormatException", e);
      } catch (Exception e) {
        log.error(">>> PostController.getPost searchUserNos 파싱 에러 Exception", e);
        throw new IllegalArgumentException("searchUserNos 파싱 에러 Exception", e);
      }
    }

    List<Long> searchAlcoholNoList = new ArrayList<>();
    if (StringUtils.hasText(searchAlcoholNos)) {
      try {
        searchAlcoholNoList =
            Arrays.stream(searchAlcoholNos.split(",")).map(Long::parseLong).toList();
      } catch (NumberFormatException e) {
        log.error(">>> PostController.getPost searchAlcoholNos 파싱 에러 NumberFormatException", e);
        throw new IllegalArgumentException("searchAlcoholNos 파싱 에러 NumberFormatException", e);
      } catch (Exception e) {
        log.error(">>> PostController.getPost searchAlcoholNos 파싱 에러 Exception", e);
        throw new IllegalArgumentException("searchAlcoholNos 파싱 에러 Exception", e);
      }
    }

    return postService.getPostsV2(pageable, searchKeyword, searchUserNoList, isLikedByMe,
        isCommentedByMe, searchAlcoholNoList);
  }

  @Operation(summary = "[o]게시글 상세 조회", description = "게시글 상세 조회 API")
  @GetMapping("/{postNo}")
  public PostResponseDto getPostDetail(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.getPostDetail");
    return postService.getPostDetail(postNo);
  }

  @Operation(summary = "[o]게시글 댓글 등록", description = "게시글 댓글 등록 API")
  @PostMapping("/{postNo}/comments")
  @UserRequired
  @ResponseStatus(HttpStatus.CREATED)
  public AddCommentResponseDto addComment(@PathVariable("postNo") Long postNo, @Valid @RequestBody
  AddCommentRequestDto addCommentRequestDto) {
    log.info(">>> PostController.addComment");
    return postService.addComment(postNo, addCommentRequestDto);
  }

  @Operation(summary = "[o]게시글 댓글 조회", description = "게시글 댓글 조회 API")
  @GetMapping("/{postNo}/comments")
  @ResponseStatus(HttpStatus.CREATED)
  public GetPostCommentsResponseDto getPostComments(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.getPostComments");
    return postService.getPostComments(postNo);
  }

  @Operation(summary = "[o]게시글 댓글 수정", description = "게시글 댓글 수정 API")
  @PatchMapping("/{postNo}/comments/{commentNo}")
  @UserRequired
  public void editComment(@PathVariable("postNo") Long postNo,
                          @PathVariable("commentNo") Long commentNo,
                          @Valid @RequestBody
                          EditCommentRequestDto editCommentRequestDto) {
    log.info(">>> PostController.editComment");
    postService.editComment(postNo, commentNo, editCommentRequestDto);
  }

  @Operation(summary = "[o]게시글 댓글 삭제", description = "게시글 댓글 삭제 API")
  @DeleteMapping("/{postNo}/comments/{commentNo}")
  @UserRequired
  public void deleteComment(@PathVariable("postNo") Long postNo,
                            @PathVariable("commentNo") Long commentNo) {
    log.info(">>> PostController.deleteComment");
    postService.deleteComment(postNo, commentNo);
  }

  @Operation(summary = "[o]인용 등록 ", description = "인용 등록 API")
  @PostMapping("/{postNo}/quotes/{quotedPostNo}")
  @UserRequired
  @ResponseStatus(HttpStatus.CREATED)
  public AddQuoteResponseDto addQuote(@PathVariable("postNo")
                                      @Schema(example = "1", description = "포스트 번호") Long postNo

      , @PathVariable("quotedPostNo") @Schema(example = "2", description = "인용하는 포스트 번호")
                                      Long quotedPostNo) {
    log.info(">>> PostController.addQuote");
    return AddQuoteResponseDto.of(postService.addQuote(postNo, quotedPostNo));
  }

  @Operation(summary = "[o]인용 삭제", description = "인용 삭제 API")
  @DeleteMapping("/quotes/{quoteNo}")
  @UserRequired
  public void deleteQuote(@PathVariable("quoteNo") Long quoteNo) {
    log.info(">>> PostController.deleteQuote");
    postService.deleteQuote(quoteNo);
  }

  @Operation(summary = "[o]인용 복수 조회", description = "인용 복수 조회 API")
  @GetMapping("/post-quotes/{postNo}")
  public GetQuotesByPostResponseDto getQuotesByPost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.getQuoteDetail");
    return postService.getQuotesByPost(postNo);
  }

  @Operation(summary = "[o]게시글 좋아요", description = "게시글 좋아요 API")
  @PostMapping("/like/{postNo}")
  @UserRequired
  public void likePost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.likePost");
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    postService.likePost(userNo, postNo);
  }

  @Operation(summary = "[o]게시글 좋아요 취소", description = "게시글 좋아요 취소 API")
  @PostMapping("/like-cancel/{postNo}")
  @UserRequired
  public void likeCancelPost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.likeCancelPost");
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    postService.likeCancelPost(userNo, postNo);
  }

}
