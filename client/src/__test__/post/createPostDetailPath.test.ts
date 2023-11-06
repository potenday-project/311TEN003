import {POST_DETAIL} from "@/const/clientPath";

describe("createPostDetailPath 함수 테스트", () => {
  it("postId=1 userId=thisUser 가 입력되었을 때 ", () => {
    expect(POST_DETAIL("thisUser", "1")).toEqual("/post/@thisUser/1");
  });
  it("공백을 자동으로 Trim 하는지 여부", () => {
    expect(POST_DETAIL(" thisUser", "1 ")).toEqual("/post/@thisUser/1");
  });
});
