import PostCard from "@/components/post/PostCard";
import { Container, Paper } from "@mui/material";
import { Meta, StoryObj } from "@storybook/react";

const mockData = {
  id: "123458",
  createdAt: "Mon Nov 06 2023 00:13:07",
  nickname: "testNick",
  userId: "userID",
  userImage: "",
  content:
    "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Eos ullam aut minus aliquam quis officia, non dolore omnis, magnam totam tenetur ad harum? Mollitia omnis odit atque blanditiis exercitationem! Voluptatum.",
  image: ["https://source.unsplash.com/random?wallpapers"],
};

const meta = {
  title: "Components/Post/PostCard",
  component: PostCard,
  tags: ["autodocs"],
  decorators: [
    (Story) => {
      return (
        <Container maxWidth="md" sx={{ mx: "auto" }}>
          <Story />
        </Container>
      );
    },
  ],
} satisfies Meta<typeof PostCard>;

export default meta;

type Story = StoryObj<typeof meta>;
export const Default: Story = {
  args: {
    ...mockData,
  },
};
export const withoutImage: Story = {
  args: {
    ...mockData,
    image: [],
  },
};
export const withoutUserImage: Story = {
  args: {
    ...mockData,
    userImage: undefined,
  },
};
