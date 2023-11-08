import { HttpResponse, http } from "msw";
import { randomNumber, randomSelect } from "./utils/random";
import { PostInterface } from "@/types/post/PostInterface";

export const handlers = [
  http.get(`${process.env.NEXT_PUBLIC_BASE_URL}/posts`, () => {
    return HttpResponse.json({
      content: Array.from(new Array(5)).map((_data, i):PostInterface => {
        return {
          nickname: "testNick",
          id: "userID",
          updateDt: "2023-11-08T13:05:09.531Z",
          createdAt: "2023-11-08T13:05:09.531Z",
          edited: randomSelect<boolean>(true, false),
          postNo: i,
          postContent:
            "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
          positionInfo: "울릉도 동남쪽 뱃길따라 200리",
          alcoholName: "string",
          postAttachUrl: ["https://source.unsplash.com/random?wallpapers"],
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
          followedByMe: randomSelect(true, false),
        };
      }),
    });
  }),
];
