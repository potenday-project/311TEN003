import useFollowMutation from "@/queries/user/useFollowMutation";
import useUnFollowMutation from "@/queries/user/useUnFollowMutation";
import { Button, ButtonProps } from "@mui/material";

interface FollowBtnProps extends ButtonProps {
  isFollowing?: boolean;
  userId: string;
}

const FollowUserBtn = ({
  isFollowing,
  userId,
  ...btnProps
}: FollowBtnProps) => {
  const { mutate: follow } = useFollowMutation();
  const { mutate: unfollow } = useUnFollowMutation();
  return (
    <>
      {isFollowing ? (
        <Button
          onClick={() => unfollow(userId)}
          variant="outlined"
          {...btnProps}
        >
          언팔로우
        </Button>
      ) : (
        <Button onClick={() => follow(userId)} {...btnProps}>
          팔로우
        </Button>
      )}
    </>
  );
};

export default FollowUserBtn;
