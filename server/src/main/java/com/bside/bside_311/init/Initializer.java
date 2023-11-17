package com.bside.bside_311.init;

import com.bside.bside_311.controller.AlcoholController;
import com.bside.bside_311.controller.PostController;
import com.bside.bside_311.controller.UserController;
import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.PostType;
import com.bside.bside_311.repository.AlcoholTypeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Initializer {
  private final ObjectMapper objectMapper;
  private final AlcoholTypeRepository alcoholTypeRepository;
  private final UserController userController;
  private final AlcoholController alcoholController;

  private final PostController postController;

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
    Authentication authentication = new UsernamePasswordAuthenticationToken(1L, "",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    for (int i = 0; i < 5; i++) {
      userController.signup(UserSignupRequestDto.builder()
                                                .email(String.format("test%d@example.com", i))
                                                .password("1a2s3d4f1!")
                                                .id(String.format("test%d", i))
                                                .nickname(String.format("bside%d", i))
                                                .build());
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
    }

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
