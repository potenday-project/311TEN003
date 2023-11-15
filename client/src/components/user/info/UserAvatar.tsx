import { Avatar, AvatarProps } from "@mui/material";
import { ReactNode } from "react";

interface UserAvatarProps extends AvatarProps {
  fallback: ReactNode;
}

const UserAvatar = ({ src, fallback,sx,...others }: UserAvatarProps) => {
  return (
    <Avatar
      sx={{...sx, bgcolor: "secondary.main" }}
      src={src}
      data-testid="avatar"
      {...others}
    >
      {typeof fallback === "string" ? fallback[0].toUpperCase() : fallback}
    </Avatar>
  );
};

export default UserAvatar;
