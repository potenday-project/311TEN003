// import PostCard from "@/components/post/PostCard";
// import { Container } from "@mui/material";
// import { Meta, StoryObj } from "@storybook/react";

// const mockData = {
//   nickname: "testNick",
//   id: "userID",
//   updateDt: "2023-11-08T13:05:09.531Z",
//   createdAt: "2023-11-08T13:05:09.531Z",
//   edited: true,
//   postNo: 135,
//   postContent:
//     "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
//   positionInfo: "울릉도 동남쪽 뱃길따라 200리",
//   alcoholName: "Lorem Ipsum4",
//   alcoholType: "와인",
//   postAttachUrls: ["https://source.unsplash.com/random?wallpapers"],
//   tagList: ["tag1", "tag2"],
//   quoteInfo: [],
//   likeCount: 6,
//   commentCount: 3,
//   quoteCount: 4,
//   followedByMe: true,
//   likedByme: false,
//   profileImgUrls: ["https://source.unsplash.com/random?wallpapers"],
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
