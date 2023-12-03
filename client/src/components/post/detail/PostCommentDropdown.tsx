import { ButtonBase, Menu, MenuItem } from "@mui/material";
import { MoreVertOutlined } from "@mui/icons-material";
import { useState } from "react";

const PostCommentDropdown = () => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
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
        <MenuItem
          onClick={() => {
            if (confirm("정말 삭제하시겠습니까?")) {
              console.log("눌림");
            }
          }}
        >
          삭제
        </MenuItem>
        <MenuItem>수정</MenuItem>
      </Menu>
    </>
  );
};

export default PostCommentDropdown;
