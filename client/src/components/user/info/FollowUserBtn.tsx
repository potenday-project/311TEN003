import { Button, ButtonProps } from "@mui/material";

interface FollowBtnProps extends ButtonProps {
  isFollowing?: boolean;
}

const FollowUserBtn = ({ isFollowing, ...btnProps }: FollowBtnProps) => {
  return (
    <>
      {isFollowing ? (
        <Button variant="outlined" {...btnProps}>
          언팔로우
        </Button>
      ) : (
        <Button {...btnProps}>팔로우</Button>
      )}
    </>
  );
};

export default FollowUserBtn;
