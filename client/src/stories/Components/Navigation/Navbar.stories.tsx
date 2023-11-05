import NavigationBar from "@/components/NavigationBar";

import { Meta, StoryObj } from "@storybook/react";
const meta = {
  title: "Components/Navigation/NavigationBar",
  component: NavigationBar,
  tags: ["autodocs"],
  decorators: [
    (Story) => {
      return (
        <div
          style={{ minHeight: "150px" }}
          onClick={(e) => e.stopPropagation()}
        >
          <Story />
        </div>
      );
    },
  ],
} satisfies Meta<typeof NavigationBar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Navbar: Story = {};
