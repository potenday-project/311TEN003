import React, { useState } from "react";
import { MoreVertOutlined } from "@mui/icons-material";
import { ButtonBase, Menu, MenuItem } from "@mui/material";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";
import useDeleteAttachMutation from "@/queries/attach/useDeleteAttachMutation";
import { useRouter } from "next/navigation";
import HOME from "@/const/clientPath";
import DeleteEditDropdown from "../DeleteEditDropdown";

type PostCardOptionDropdownProps = {
  postId: number;
  filePk?: string;
};

const PostCardOptionDropdown = ({
  postId,
  filePk,
}: PostCardOptionDropdownProps) => {
  const router = useRouter();
  const { mutateAsync: deletePost } = useDeletePostMutation();
  const { mutateAsync: deleteFile } = useDeleteAttachMutation();

  const deleteHandler = async () => {
    if (confirm("정말 삭제하시겠습니까?")) {
      await deletePost(postId);
      filePk && (await deleteFile(filePk));
      router.push(HOME);
    }
  };

  return <DeleteEditDropdown onDelete={deleteHandler} />;
};

export default PostCardOptionDropdown;
