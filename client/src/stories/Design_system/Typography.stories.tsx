import { Typography } from "@mui/material";
import { Meta, StoryObj } from "@storybook/react";
const meta = {
  title: "Design system/Typography",
  component: Typography,
  tags: ["autodocs"],
  argTypes: {
    variant: {
      options: [
        "h1",
        "h2",
        "subtitle1",
        "subtitle2",
        "body",
        "button",
        "label",
        "caption1",
        "caption2",
      ],
      default: "body",
      control: { type: "select" },
    },
  },
  decorators: [
    (Story, ctx) => {
      return (
        <div style={{ display: "flex", flexDirection: "column" }}>
          <Typography variant={ctx.args.variant} sx={{ fontWeight: "bold" }}>
            {ctx.args.children}
          </Typography>
          <Story />
        </div>
      );
    },
  ],
} satisfies Meta<typeof Typography>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Heading1: Story = {
  args: {
    variant: "h1",
    children: "오늘은 이거다 주酒, D-SHOT, DA(alcohol)ily",
  },
};

export const Heading2: Story = {
  args: {
    ...Heading1.args,
    variant: "h2",
  },
};

export const Subtitle1: Story = {
  args: {
    ...Heading1.args,
    variant: "subtitle1",
  },
};
export const Subtitle2: Story = {
  args: {
    ...Heading1.args,
    variant: "subtitle1",
  },
};
export const Body1: Story = {
  args: {
    ...Heading1.args,
    variant: "body1",
  },
};
export const Button: Story = {
  args: {
    ...Heading1.args,
    variant: "button",
  },
};
export const Label: Story = {
  args: {
    ...Heading1.args,
    variant: "label",
  },
};
export const Caption1: Story = {
  args: {
    ...Heading1.args,
    variant: "caption1",
  },
};
export const Caption2: Story = {
  args: {
    ...Heading1.args,
    variant: "caption2",
  },
};
