import SigninForm from "@/components/user/signin/SigninForm";
import { Box, Paper } from "@mui/material";
import { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "Components/Auth/LoginForm",
  component: SigninForm,
  tags: ["autodocs"],
  decorators: [
    (Story) => {
      return (
        <Paper sx={{ mx: "auto", maxWidth: "768px", height: "100%" }}>
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Story />
          </Box>
        </Paper>
      );
    },
  ],
} satisfies Meta<typeof SigninForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const LoginForm: Story = {
  args: {},
};
