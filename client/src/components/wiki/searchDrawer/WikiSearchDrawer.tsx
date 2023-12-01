import { Stack } from "@mui/material";
import { useContext } from "react";
import WikiPageContext from "@/store/wiki/WikiPageContext";
import WikiSerachArea from "@/components/wiki/searchDrawer/WikiSerachArea";
import CustomSwipeableDrawer from "@/components/CustomSwipeableDrawer";

const WikiSearchDrawer = () => {
  const { isSearching, setIsSearching } = useContext(WikiPageContext);

  return (
    <CustomSwipeableDrawer
      open={isSearching}
      onOpen={() => setIsSearching(true)}
      onClose={() => setIsSearching(false)}
    >
      <Stack gap={2}>
        <WikiSerachArea />
      </Stack>
    </CustomSwipeableDrawer>
  );
};

export default WikiSearchDrawer;
