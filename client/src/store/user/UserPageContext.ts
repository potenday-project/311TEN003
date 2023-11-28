import { Dispatch, SetStateAction, createContext } from "react";

interface UserPageContextInterface {
  isEditing: boolean;
  setIsEditing: Dispatch<SetStateAction<boolean>>;
}

const UserPageContext = createContext<UserPageContextInterface>({
  isEditing: true,
  setIsEditing: () => {},
});

export default UserPageContext;
