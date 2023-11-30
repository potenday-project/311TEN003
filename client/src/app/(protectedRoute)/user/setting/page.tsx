"use client";

import UserInfoCard from "@/components/user/info/UserInfoCard";
import UserInfoCardSkeleton from "@/components/user/info/UserInfoCardSkeleton";
import { useMyInfoQuery } from "@/queries/auth/useMyInfoQuery";
import {
  Button,
  ButtonBase,
  Paper,
  Stack,
  Typography,
  styled,
} from "@mui/material";
import PostSeeMoreIcon from "@/assets/icons/PostSeeMoreIcon.svg";
import useLogoutMutation from "@/queries/auth/useLogoutMutation";

const SettingPage = () => {
  const { data: myInfo } = useMyInfoQuery();
  const { mutate: logoutHandler } = useLogoutMutation();

  return (
    <>
      <PaddingPaper>
        {myInfo ? (
          <UserInfoCard userId={myInfo.userNo} />
        ) : (
          <UserInfoCardSkeleton />
        )}
      </PaddingPaper>

      <PaddingPaper>
        <Typography variant="subtitle2">정보</Typography>
        <ButtonBase sx={{ px: 3, py: 1.5, width: "100%" }}>
          <RowStack>
            <Typography>개인정보</Typography>
            <PostSeeMoreIcon />
          </RowStack>
        </ButtonBase>
      </PaddingPaper>
      <PaddingPaper>
        <Typography variant="subtitle2">고객센터</Typography>
        <ButtonBase sx={{ px: 3, py: 1.5, width: "100%" }}>
          <RowStack>
            <Typography>고객센터</Typography>
            <PostSeeMoreIcon />
          </RowStack>
        </ButtonBase>
        <ButtonBase sx={{ px: 3, py: 1.5, width: "100%" }}>
          <RowStack>
            <Typography>Q&A</Typography>
            <PostSeeMoreIcon />
          </RowStack>
        </ButtonBase>
        <ButtonBase sx={{ px: 3, py: 1.5, width: "100%" }}>
          <RowStack>
            <Typography>사용자 의견 남기기</Typography>
            <PostSeeMoreIcon />
          </RowStack>
        </ButtonBase>
      </PaddingPaper>
      <PaddingPaper>
        <Typography variant="subtitle2">투파이아</Typography>
        <RowStack>
          <Typography>버전</Typography>
          <Typography>1.0.0</Typography>
        </RowStack>
      </PaddingPaper>
      <PaddingPaper>
        <Typography variant="subtitle2">계정</Typography>
        <Button color="secondary" onClick={() => logoutHandler()}>
          로그아웃
        </Button>
      </PaddingPaper>
    </>
  );
};
const PaddingPaper = styled(Paper)(() => ({
  padding: 16,
  display: "flex",
  flexDirection: "column",
  gap: "16px",
}));

const RowStack = styled(Stack)(() => ({
  width: "100%",
  flexDirection: "row",
  justifyContent: "space-between",
  alignItems: "center",
}));

export default SettingPage;
