import React, { useState } from "react";
import { MoreVertOutlined } from "@mui/icons-material";
import { ButtonBase, Menu, MenuItem } from "@mui/material";
import { useDeletePostMutation } from "@/queries/post/useDeletePostMutation";

type PostCardOptionDropdownProps = {
  postId:number
};

const PostCardOptionDropdown = ({postId}: PostCardOptionDropdownProps) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
const {mutate:deletePost}=useDeletePostMutation()

  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <>
      <ButtonBase aria-label="settings" sx={{ p: 0 }} onClick={handleClick}>
        <MoreVertOutlined />
      </ButtonBase>
      <Menu open={open} anchorEl={anchorEl} onClose={handleClose}>
        <MenuItem onClick={()=>{
          if(confirm('정말 삭제하시겠습니까?')){
            deletePost(postId)
          }
          }}>삭제</MenuItem>
        <MenuItem>수정</MenuItem>
      </Menu>
    </>
  );
};

export default PostCardOptionDropdown;
