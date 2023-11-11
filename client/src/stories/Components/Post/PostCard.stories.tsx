// import PostCard from "@/components/post/PostCard";
// import { Container } from "@mui/material";
// import { Meta, StoryObj } from "@storybook/react";

// const mockData = {
//   nickname: "테스트아이디입니다",
//   id: "jungujungu",
//   profileImgUrls: [],
//   updateDt: "2023-11-12T03:48:49.44238",
//   edited: true,
//   postNo: 34,
//   postContent: "포스트 글입니다",
//   commentCount: 0,
//   positionInfo: "울릉도 동남쪽 뱃길따라 200리",
//   alcoholNo: 1,
//   alcoholType: '와인',
//   alcoholName: 'Lorem Ipsum.14',
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

// const meta = {
//   title: "Components/Post/PostCard",
//   component: PostCard,
//   tags: ["autodocs"],
//   decorators: [
//     (Story) => {
//       return (
//         <Container maxWidth="md" sx={{ mx: "auto" }}>
//           <Story />
//         </Container>
//       );
//     },
//   ],
// } satisfies Meta<typeof PostCard>;

// export default meta;

// type Story = StoryObj<typeof meta>;
// export const Default: Story = {
//   args: {
//     ...mockData,
//   },
// };
// export const withoutImage: Story = {
//   args: {
//     ...mockData,
//     postAttachUrls: [],
//   },
// };
// export const withoutUserImage: Story = {
//   args: {
//     ...mockData,
//     profileImgUrls: [],
//   },
// };
// export const withoutTags: Story = {
//   args: {
//     ...mockData,
//     tagList: [],
//   },
// };
