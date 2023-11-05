import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import PostCard from "@/components/post/PostCard";

const mockData = {
  id: "123458",
  createdAt: "Mon Nov 06 2023 00:13:07",
  nickname: "testNick",
  userId: "userID",
  userImage: "https://source.unsplash.com/random?wallpapers",
  content:
    "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
  image: ["https://source.unsplash.com/random?wallpapers"],
};

describe("버튼 컴포넌트 스펙", () => {
  beforeEach(() => render(<PostCard {...mockData} />));
  it("@유저아이디 형태의 헤더가 존재하는지 체크", () => {
    expect(screen.getByTestId("mui-header")).toHaveTextContent(
      `@${mockData.userId}`
    );
  });
  it("유저 닉네임이 존재하는지 여부 체크", () => {
    expect(screen.getByTestId("mui-header")).toHaveTextContent(
      mockData.nickname
    );
  });
  it("공유하기 버튼이 존재하는지 여부", () => {
    expect(screen.getByTestId("shareBtn")).not.toBeNull();
  });
  it("좋아요 버튼이 존재하는지 여부", () => {
    expect(screen.getByTestId("likeBtn")).not.toBeNull();
  });
});

describe("버튼 컴포넌트 조건부렌더링 테스트", () => {
  it("포스트에 이미지가 없을경우 이미지가 표시되지 않는지 여부", () => {
    render(<PostCard {...{ ...mockData, image: [] }} />);
    const imgNode = screen.queryByTestId("postImg");
    expect(imgNode).not.toBeInTheDocument();
  });
  it("유저 이미지가 없을경우 이미지대신 1글자의 더미문자 표시여부", () => {
    render(<PostCard {...{ ...mockData, userImage: undefined }} />);
    const imgNode = screen.queryByTestId("avatar");
    expect(imgNode).toHaveTextContent(/./);
  });
});
