import React, { useState } from "react";
import { MoreVertOutlined } from "@mui/icons-material";
import { ButtonBase, Menu, MenuItem } from "@mui/material";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";
import useDeleteAttachMutation from "@/queries/attach/useDeleteAttachMutation";
import { useRouter } from "next/navigation";
import HOME from "@/const/clientPath";

type PostCardOptionDropdownProps = {
  postId: number;
  filePk?: string;
};

const PostCardOptionDropdown = ({
  postId,
  filePk,
}: PostCardOptionDropdownProps) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const router = useRouter();

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const { mutateAsync: deletePost } = useDeletePostMutation();
  const { mutateAsync: deleteFile } = useDeleteAttachMutation();

  const deleteHandler = async () => {
    if (confirm("정말 삭제하시겠습니까?")) {
      await deletePost(postId);
      filePk && (await deleteFile(filePk));
      router.push(HOME);
    }
  };

  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <>
      <ButtonBase aria-label="settings" sx={{ p: 0 }} onClick={handleClick}>
        <MoreVertOutlined />
      </ButtonBase>
      <Menu open={open} anchorEl={anchorEl} onClose={handleClose}>
        <MenuItem onClick={deleteHandler}>삭제</MenuItem>
        <MenuItem>수정</MenuItem>
      </Menu>
    </>
  );
};

export default PostCardOptionDropdown;
