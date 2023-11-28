import { SwipeableDrawer, Stack, styled, Box } from "@mui/material";
import { useContext } from "react";
import WikiPageContext from "@/store/wiki/WikiPageContext";
import WikiSerachArea from "@/components/wiki/searchDrawer/WikiSerachArea";

const WikiSearchDrawer = () => {
  const { isSearching, setIsSearching } = useContext(WikiPageContext);

  const pullerBleed = 24;
  return (
    <SwipeableDrawer
      open={isSearching}
      onOpen={() => setIsSearching(true)}
      onClose={() => setIsSearching(false)}
      anchor="bottom"
      disableSwipeToOpen
      PaperProps={{
        sx: {
          p: 2,
          borderTopLeftRadius: pullerBleed,
          borderTopRightRadius: pullerBleed,
          overFlow: "hidden",
        },
      }}
      ModalProps={{
        keepMounted: false,
      }}
    >
      <Puller />
      <Stack gap={2} pt={`${pullerBleed + 8}px`}>
        <WikiSerachArea />
      </Stack>
    </SwipeableDrawer>
  );
};

export default WikiSearchDrawer;

const Puller = styled(Box)(() => ({
  width: 56,
  height: 4,
  backgroundColor: "#F6EAFB",
  borderRadius: 3,
  position: "absolute",
  top: 8,
  left: "calc(50% - 28px)",
}));
