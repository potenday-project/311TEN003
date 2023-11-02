import { Button, ButtonProps as MuiButtonProps } from "@mui/material";
import { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "Design system/Button",
  component: Button,
  tags: ["autodocs"],
  argTypes: {
    variant: {
      options: ["contained", "outlined", "text"],
      default: "contained",
      control: { type: "radio" },
    },
    size: {
      options: ["small", "medium", "large"],
      control: { type: "radio" },
    },
    fullWidth: { control: "boolean" },
  },
} satisfies Meta<MuiButtonProps>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Contained: Story = {
  args: {
    variant: "contained",
    children: "버튼",
    size: "medium",
    fullWidth: false,
  },
};

export const Outlined: Story = {
  args: {
    ...Contained.args,
    variant: "outlined",
  },
};

export const Text: Story = {
  args: {
    ...Contained.args,
    variant: "text",
  },
};
