import { PostInterface } from "@/types/post/PostInterface";
import { HttpResponse, http } from "msw";
import { randomBoolean, randomNumber, randomSelect } from "../utils/random";
/**
 * 포스트 상세보기 정보를 받아오는 핸들러
 */
export default  http.get(`${process.env.NEXT_PUBLIC_BASE_URL}/posts/1`, () => {
    return HttpResponse.json<PostInterface>({
      nickname: "testNick",
      id: "userID",
      updateDt: "2023-11-08T13:05:09.531Z",
      createdAt: "2023-11-08T13:05:09.531Z",
      edited: randomBoolean(),
      postNo: 135,
      postContent:
        "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
      positionInfo: "울릉도 동남쪽 뱃길따라 200리",
      alcoholName: "string",
      postAttachUrl: randomSelect(
        ["https://source.unsplash.com/random?wallpapers"],
        []
      ),
      tagList: randomSelect(["tag1", "tag2"], []),
      quoteInfo: randomSelect(
        [
          {
            quoteNo: 1,
            quoteContent: "1",
          },
        ],
        []
      ),
      likeCount: randomNumber(),
      quoteCount: randomNumber(),
      followedByMe: randomBoolean(),
      likedByme: randomBoolean(),
      profileImgUrls: randomSelect(
        "https://source.unsplash.com/random?wallpapers",
        ""
      ),
    });
  });
