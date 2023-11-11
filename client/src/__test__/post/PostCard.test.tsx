// import "@testing-library/jest-dom";
// import { render, screen } from "@testing-library/react";
// import PostCard from "@/components/post/PostCard";

// const mockData: any = {
//   nickname: "테스트아이디입니다",
//   id: "jungujungu",
//   profileImgUrls: [],
//   updateDt: "2023-11-12T03:48:49.44238",
//   edited: true,
//   postNo: 34,
//   postContent: "",
//   commentCount: 0,
//   positionInfo: "",
//   alcoholNo: null,
//   alcoholType: null,
//   alcoholName: null,
//   postAttachUrls: [
//     {
//       attachNo: 1,
//       attachUrl:
//         "https://res.cloudinary.com/drezugbxz/image/upload/v1699728531/test/0E62M1GGZJEH4.jpg",
//       attachType: "POST",
//     },
//   ],
//   tagList: [],
//   quoteInfo: [],
//   likeCount: 1,
//   quoteCount: 0,
//   likedByMe: false,
//   followedByMe: false,
// };

// jest.mock("next/navigation", () => ({
//   useRouter() {
//     return {
//       prefetch: () => null,
//     };
//   },
// }));

// describe("버튼 컴포넌트 스펙", () => {
//   beforeEach(() => render(<PostCard {...mockData} />));
//   it("@유저아이디 형태의 헤더가 존재하는지 체크", () => {
//     expect(screen.getByTestId("mui-header")).toHaveTextContent(
//       `@${mockData.id}`
//     );
//   });
//   it("유저 닉네임이 존재하는지 여부 체크", () => {
//     expect(screen.getByTestId("mui-header")).toHaveTextContent(
//       mockData.nickname
//     );
//   });
//   it("공유하기 버튼이 존재하는지 여부", () => {
//     expect(screen.getByTestId("shareBtn")).not.toBeNull();
//   });
//   it("좋아요 버튼이 존재하는지 여부", () => {
//     expect(screen.getByTestId("likeBtn")).not.toBeNull();
//   });
// });

// describe("버튼 컴포넌트 조건부렌더링 테스트", () => {
//   it("포스트에 이미지가 없을경우 이미지가 표시되지 않는지 여부", () => {
//     render(<PostCard {...{ ...mockData, postAttachUrls: [] }} />);
//     const imgNode = screen.queryByTestId("postImg");
//     expect(imgNode).not.toBeInTheDocument();
//   });
//   it("유저 이미지가 없을경우 이미지대신 1글자의 더미문자 표시여부", () => {
//     render(<PostCard {...{ ...mockData, userImage: undefined }} />);
//     const imgNode = screen.queryByTestId("avatar");
//     expect(imgNode).toHaveTextContent(/./);
//   });
// });
