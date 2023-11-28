import { Box, BoxProps, Chip, IconButton, Typography } from "@mui/material";
import PostSeeMoreIcon from "@/assets/icons/PostSeeMoreIcon.svg";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import XIcon from "@/assets/icons/XIcon.svg";
import { useRouter } from "next/navigation";
import { WIKI_DETAIL } from "@/const/clientPath";

interface AlcoholNameTagInterface extends BoxProps {
  alcoholName: AlcoholDetailInterface["alcoholName"];
  alcoholType: AlcoholDetailInterface["alcoholType"];
  alcoholNo: AlcoholDetailInterface["alcoholNo"];
  removable?: boolean;
  onClickRemove?: () => void;
}

const AlcoholNameTag = ({
  alcoholName,
  alcoholType,
  removable = false,
  alcoholNo,
  onClickRemove,
  ...others
}: AlcoholNameTagInterface) => {
  const router = useRouter();
  return (
    <Box sx={WrapperStyle} {...others}>
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          gap: 1,
          alignItems: "center",
        }}
      >
        <Chip
          label={alcoholType}
          color={"primary"}
          size="small"
          variant="outlined"
          sx={ChipStyle}
        />
        <Typography variant="label" color="primary.main">
          {alcoholName}
        </Typography>
      </Box>
      {removable ? (
        <IconButton
          sx={{ p: 0 }}
          onClick={() => onClickRemove && onClickRemove()}
        >
          <XIcon />
        </IconButton>
      ) : (
        <IconButton
          sx={{ p: 0 }}
          onClick={() => router.push(WIKI_DETAIL(String(alcoholNo)))}
        >
          <PostSeeMoreIcon style={{ margin: "3px 0" }} />
        </IconButton>
      )}
    </Box>
  );
};

const WrapperStyle = {
  width: "100%",
  border: "1px solid",
  borderColor: "gray.secondary",
  backgroundColor: "gray.primary",
  display: "flex",
  flexDirection: "row",
  alignItems: "center",
  justifyContent: "space-between",
  px: 1,
  py: 0.5,
  borderRadius: 4,
};

const ChipStyle = {
  fontWeight: "bold",
  borderColor: "gray.secondary",
  backgroundColor: "background.paper",
  color: "#4A148C",
};

export default AlcoholNameTag;
