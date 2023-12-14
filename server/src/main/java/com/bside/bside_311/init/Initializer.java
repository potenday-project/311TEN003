package com.bside.bside_311.init;

import com.bside.bside_311.controller.AlcoholController;
import com.bside.bside_311.controller.AttachController;
import com.bside.bside_311.controller.PostController;
import com.bside.bside_311.controller.UserController;
import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.AddCommentRequestDto;
import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.ImageRequestDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.PostType;
import com.bside.bside_311.repository.AlcoholTypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Initializer {
  private final ObjectMapper objectMapper;
  private final AlcoholTypeRepository alcoholTypeRepository;
  private final UserController userController;
  private final AlcoholController alcoholController;
  private final AttachController attachController;

  private final PostController postController;

  private static UsernamePasswordAuthenticationToken easyUserRoleAuthenticationFactory(
      Long userNo) {
    return new UsernamePasswordAuthenticationToken(userNo, "",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }

  public static File convertInputStreamToFile(InputStream in) throws IOException {

    File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
    tempFile.deleteOnExit();

    copyInputStreamToFile(in, tempFile);

    return tempFile;
  }

  private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

    FileUtils.copyInputStreamToFile(inputStream, file);
  }

  private void setSecurityContextUserNo(Long userNo) {
    SecurityContextHolder.getContext()
                         .setAuthentication(easyUserRoleAuthenticationFactory(userNo));
  }

  @PostConstruct
  public void init() throws JsonProcessingException {
    alcoholTypeRepository.save(AlcoholType.builder().name("소주").description("소주다.").build());
    alcoholTypeRepository.save(AlcoholType.builder().name("맥주").description("맥주다.").build());
    alcoholTypeRepository.save(AlcoholType.builder().name("와인").description("와인이다.").build());
    alcoholTypeRepository.save(AlcoholType.builder().name("막걸리").description("와인이다.").build());

    String signupAdminBody = """
        {
          "email": "admin@example.com",
          "password": "1a2s3d4f1!",
          "id": "admin",
          "nickname": "admin"
        }
        """;
    userController.signupAdmin(objectMapper.readValue(signupAdminBody, UserSignupRequestDto.class));

    String signupBody = """
        {
          "email": "test@example.com",
          "password": "1a2s3d4f1!",
          "id": "apple",
          "nickname": "bside"
        }
        """;
    userController.signup(objectMapper.readValue(signupBody, UserSignupRequestDto.class));
    Authentication authentication = easyUserRoleAuthenticationFactory(1L);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    for (int i = 0; i < 5; i++) {
      userController.signup(UserSignupRequestDto.builder()
                                                .email(String.format("test%d@example.com", i))
                                                .password("1a2s3d4f1!")
                                                .id(String.format("test%d", i))
                                                .nickname(String.format("bside%d", i))
                                                .introduction(
                                                    String.format("testIntroduction%d", i))
                                                .build());
    }
    securityContextHolderClear();
    SecurityContextHolder.getContext().setAuthentication(null);

    for (int i = 0; i < 5; i++) {
      SecurityContextHolder.getContext()
                           .setAuthentication(easyUserRoleAuthenticationFactory((long) (i + 1)));
      alcoholController.
          addAlcohol(AddAlcoholRequestDto.builder()
                                         .alcoholName(String.format("test%d", i))
                                         .alcoholTypeNo((long) (i % 4 + 1))
                                         .nickNames(
                                             List.of(String.format("testNickName%d", i),
                                                 String.format("testNickName23453%d", i)))
                                         .manufacturer(String.format("testNmanufacturer%d", i))
                                         .description(String.format("testDescription%d", i))
                                         .degree((float) (i + 0.5))
                                         .period(20L + i)
                                         .productionYear(1990L + i)
                                         .tagList(List.of(String.format("testTag%d", i),
                                             String.format("testTag23453%d", i)))
                                         .volume(700L + i).build());
      postController.addPost(AddPostRequestDto.builder()
                                              .alcoholNo((long) (i + 1))
                                              .alcoholFeature(String.format("testFeature%d", i))
                                              .postContent(String.format("testContent%d", i))
                                              .postType(PostType.BASIC.name())
                                              .positionInfo(String.format("testPositionInfo%d", i)
                                              ).tagList(List.of(String.format("testTag%d", i),
              String.format("testTag23453%d", i)))
                                              .build());
      SecurityContextHolder.getContext().setAuthentication(null);
    }

    // following
    // 1L -> 3L
    // 1L -> 4L
    // 2L -> 3L
    // 4L -> 2L
    // 5L -> 2L
    // 2L -> 4L
    setSecurityContextUserNo(1L);
    userController.followUser(3L);
    securityContextHolderClear();

    setSecurityContextUserNo(1L);
    userController.followUser(4L);
    securityContextHolderClear();

    setSecurityContextUserNo(2L);
    userController.followUser(3L);
    securityContextHolderClear();

    setSecurityContextUserNo(4L);
    userController.followUser(2L);
    securityContextHolderClear();

    setSecurityContextUserNo(5L);
    userController.followUser(2L);
    securityContextHolderClear();

    setSecurityContextUserNo(2L);
    userController.followUser(4L);
    securityContextHolderClear();

    // post Like
    // 5L -> 2L
    setSecutiryContextDoSomethingAndClear(5L, () -> {
      postController.likePost(2L);
    });
    // 4L -> 2L
    setSecutiryContextDoSomethingAndClear(4L, () -> {
      postController.likePost(2L);
    });
    // 1L -> 3L
    setSecutiryContextDoSomethingAndClear(1L, () -> {
      postController.likePost(3L);
    });

    // comment
    // 1L -> 2L 포스트에. 정말 웃기다 크크.
    // 3L -> 2L 포스트에. 오 저기는 꼭 가봐야겠어.
    // 5L -> 1L 포스트에 와 이건 뭐임.
    // 4L -> 1L 포스트에. 이건 뭐임.
    // 2L -> 2L 포스트에. ㅋㅋ 신기하긴 함.
    setSecutiryContextDoSomethingAndClear(1L, () -> {
      postController.addComment(2L, new AddCommentRequestDto("정말 웃기다 크크."));
    });
    setSecutiryContextDoSomethingAndClear(3L, () -> {
      postController.addComment(2L, new AddCommentRequestDto("오 저기는 꼭 가봐야겠어."));
    });
    setSecutiryContextDoSomethingAndClear(5L, () -> {
      postController.addComment(1L, new AddCommentRequestDto("와 이건 뭐임."));
    });
    setSecutiryContextDoSomethingAndClear(4L, () -> {
      postController.addComment(1L, new AddCommentRequestDto("오 ㅋㅋㅋ"));
    });
    setSecutiryContextDoSomethingAndClear(2L, () -> {
      postController.addComment(2L, new AddCommentRequestDto("ㅋㅋㅋ ㄹㅇ 강추해요."));
    });

    setSecutiryContextDoSomethingAndClear(2L, () -> {
      postController.addComment(2L, new AddCommentRequestDto("ㅋㅋㅋ ㄹㅇ 강추해요."));
    });

    // 인용하기.
    // 2번 게시글이 1번 게시글을 인용.
    // 3번 게시글이 1번 게시글을 인용.
    // 5번 게시글이 3번 게시글을 인용.
    setSecutiryContextDoSomethingAndClear(2L, () -> {
      postController.addQuote(2L, 1L);
    });
    setSecutiryContextDoSomethingAndClear(3L, () -> {
      postController.addQuote(3L, 1L);
    });
    setSecutiryContextDoSomethingAndClear(5L, () -> {
      postController.addQuote(5L, 3L);
    });


    // 1번부터 5번까지 게시글 첨부파일 등록
    // 1번부터 5번까지 내 프로필 등록.
//    attachPhoto();

  }

  private void attachPhoto() {
    for (int i = 0; i < 5; i++) {
      int finalI = i;
      setSecutiryContextDoSomethingAndClear((long) (i + 1), () -> {
        try {
          attachController.userAttachPicture((long) (finalI + 1),
              AttachType.POST.name(),
              new ImageRequestDto(makeMultiPartFileByLocalFile(String.format("%d.png", finalI + 1),
                  "/example/attach/post")));
          attachController.userAttachPicture((long) (finalI + 1),
              AttachType.PROFILE.name(),
              new ImageRequestDto(makeMultiPartFileByLocalFile(String.format("%d.png", finalI + 1),
                  "/example/attach/profile")));

          attachController.userAttachPicture((long) (finalI + 1),
              AttachType.ALCOHOL.name(),
              new ImageRequestDto(makeMultiPartFileByLocalFile(String.format("%d.png", finalI + 1),
                  "/example/attach/alcohol")));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }
  }

  public MultipartFile makeMultiPartFileByLocalFile(String fileName, String classPathFolderPath)
      throws IOException {
//    File file = new File("C:\\temp\\test.xlsx");
//    DiskFileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());    		       InputStream
//                                                                                                                                                                                  input = new FileInputStream(file);
//    OutputStream os = fileItem.getOutputStream();
//    IOUtils.copy(input, os);
//    MultipartFile multipartFile = new MockMultipartFile("1.png", new FileInputStream(new File(filePath)));

    MultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(
        convertInputStreamToFile(
            Initializer.class.getResourceAsStream(classPathFolderPath + "/" + fileName))));
    return multipartFile;
  }

  public void setSecutiryContextDoSomethingAndClear(Long userNo, Runnable runnable) {
    setSecurityContextUserNo(userNo);
    runnable.run();
    securityContextHolderClear();
  }

  private void securityContextHolderClear() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  //  @PostConstruct
  public void init2() {
    WebClient client = WebClient.create("http://localhost:8080");
    for (int i = 0; i < 5; i++) {
      WebClient.ResponseSpec retrieve = client.post()
                                              .uri("/user/signup")
                                              .accept(
                                                  MediaType.APPLICATION_JSON)
                                              .body(BodyInserters.fromValue(
                                                  UserSignupRequestDto.builder()
                                                                      .email(
                                                                          String.format(
                                                                              "test%d@example.com",
                                                                              i))
                                                                      .password(
                                                                          "1a2s3d4f1!")
                                                                      .id(String.format(
                                                                          "test%d",
                                                                          i))
                                                                      .nickname(
                                                                          String.format(
                                                                              "bside%d",
                                                                              i))
                                                                      .build()))
                                              .retrieve();
      Mono<UserSignupResponseDto> userSignupResponseDtoMono =
          retrieve.bodyToMono(UserSignupResponseDto.class);
      UserSignupResponseDto block = userSignupResponseDtoMono.block();
      System.out.println("block = " + block);
    }
  }

}
