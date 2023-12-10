import CustomSwipeableDrawer from "@/components/layout/CustomSwipeableDrawer";
import UserPageContext from "@/store/user/UserPageContext";
import { useContext } from "react";
import UserInfoEditingForm from "./UserInfoEditingForm";

const UserInfoEditingDrawer = () => {
  const { isEditing, setIsEditing } = useContext(UserPageContext);

  return (
    <CustomSwipeableDrawer
      open={isEditing}
      onClose={() => setIsEditing(false)}
      onOpen={() => setIsEditing(true)}
    >
      <UserInfoEditingForm />
    </CustomSwipeableDrawer>
  );
};

export default UserInfoEditingDrawer;
