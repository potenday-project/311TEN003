package com.bside.bside_311.controller;

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
import com.bside.bside_311.service.PostService;
import com.bside.bside_311.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시글", description = "게시글 API")
public class PostController {
  private final PostService postService;

  @Operation(summary = "[o]게시글 등록 ", description = "게시글 등록 API")
  @PostMapping
  public AddPostResponseDto addPost(@RequestBody @Valid AddPostRequestDto addPostRequestDto) {
    log.info(">>> PostController.addPost");
    return postService.addPost(Post.of(addPostRequestDto), addPostRequestDto.getAlcoholNo(),
        addPostRequestDto.getAlcoholFeature(), addPostRequestDto.getTagList());
  }

  @Operation(summary = "[o]게시글 수정", description = "게시글 수정 API")
  @PatchMapping("/{postNo}")
  public void editPost(@PathVariable("postNo") Long postNo,
                       @RequestBody @Valid EditPostRequestDto editPostRequestDto) {
    log.info(">>> PostController.editPost");

    postService.editPost(postNo, editPostRequestDto);
    return;
  }

  @Operation(summary = "[o]게시글 삭제", description = "게시글 삭제 API")
  @DeleteMapping("/{postNo}")
  public void deletePost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.deletePost");
    postService.deletePost(postNo);
  }

  @Operation(summary = "[~]게시글 목록 조회", description = "게시글 조회 API")
  @GetMapping
  public GetPostResponseDto getPosts(@RequestParam(name = "page", defaultValue = "0")
                                     @Schema(description = "페이지번호(0부터), 기본값 0.", example = "0")
                                     Long page,
                                     @RequestParam(name = "size", defaultValue = "10")
                                     @Schema(description = "사이즈, 기본값 10.", example = "10")
                                     Long size,
                                     @RequestParam(required = false, name = "orderColumn")
                                     @Schema(description = "정렬 컬럼", example = "alcohol_no")
                                     String orderColumn,
                                     @RequestParam(required = false, name = "orderType")
                                     @Schema(description = "정렬 타입", example = "DESC")
                                     String orderType,
                                     @RequestParam(required = false, name = "searchKeyword")
                                     @Schema(description = "키워드", example = "키워드")
                                     String searchKeyword) {
    log.info(">>> PostController.getPost");
    return postService.getPosts(page, size, orderColumn, orderType, searchKeyword);
  }

  @Operation(summary = "[~]게시글 상세 조회", description = "게시글 상세 조회 API")
  @GetMapping("/{postNo}")
  public PostResponseDto getPostDetail(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.getPostDetail");
    return postService.getPostDetail(postNo);
  }

  @Operation(summary = "[o]게시글 댓글 등록", description = "게시글 댓글 등록 API")
  @PostMapping("/{postNo}/comments")
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
  public void editComment(@PathVariable("postNo") Long postNo,
                          @PathVariable("commentNo") Long commentNo,
                          @Valid @RequestBody
                          EditCommentRequestDto editCommentRequestDto) {
    log.info(">>> PostController.editComment");
    postService.editComment(postNo, commentNo, editCommentRequestDto);
  }

  @Operation(summary = "[o]게시글 댓글 삭제", description = "게시글 댓글 삭제 API")
  @DeleteMapping("/{postNo}/comments/{commentNo}")
  public void deleteComment(@PathVariable("postNo") Long postNo,
                            @PathVariable("commentNo") Long commentNo) {
    log.info(">>> PostController.deleteComment");
    postService.deleteComment(postNo, commentNo);
  }

  @Operation(summary = "[o]인용 등록 ", description = "인용 등록 API")
  @PostMapping("/{postNo}/quotes/{quotedPostNo}")
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
  public void likePost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.likePost");
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    postService.likePost(userNo, postNo);
  }

  @Operation(summary = "[o]게시글 좋아요 취소", description = "게시글 좋아요 취소 API")
  @PostMapping("/like-cancel/{postNo}")
  public void likeCancelPost(@PathVariable("postNo") Long postNo) {
    log.info(">>> PostController.likeCancelPost");
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    postService.likeCancelPost(userNo, postNo);
  }

  @Operation(summary = "게시글 사진 첨부", description = "게시글 사진 첨부 API")
  @PostMapping(value = "/{post_no}/attach", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void postAttachPicture(@ModelAttribute @Valid MultipartFile image) {
    log.info(">>> PostController.postAttachPicture");
  }

  @Operation(summary = "게시글 사진 삭제", description = "게시글 사진 삭제 API")
  @PostMapping("/{post_no}/attach/{attach_no}")
  public void postDeletePicture() {
    log.info(">>> PostController.postDeletePicture");
  }


}
