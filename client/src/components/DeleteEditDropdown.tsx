import { ButtonBase, Menu, MenuItem } from "@mui/material";
import { MoreVertOutlined } from "@mui/icons-material";
import { useState } from "react";

interface DeleteEditDropdownInterface {
  onDelete?: () => void;
  onEdit?: () => void;
}

const DeleteEditDropdown = ({
  onDelete,
  onEdit,
}: DeleteEditDropdownInterface) => {
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
        {onDelete && <MenuItem onClick={onDelete}>삭제</MenuItem>}
        {onEdit && <MenuItem onClick={onEdit}>수정</MenuItem>}
      </Menu>
    </>
  );
};

export default DeleteEditDropdown;
