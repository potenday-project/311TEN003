import { Dispatch, SetStateAction, createContext } from "react";

interface WikiPageContextInterface {
  isSearching: boolean;
  setIsSearching: Dispatch<SetStateAction<boolean>>;
}

const WikiPageContext = createContext<WikiPageContextInterface>({
  isSearching: true,
  setIsSearching: () => {},
});

export default WikiPageContext;
