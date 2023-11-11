import { Box, Chip, IconButton, Typography } from "@mui/material";
import PostSeeMoreIcon from "@/assets/icons/PostSeeMoreIcon.svg";
import { PostInterface } from "@/types/post/PostInterface";

type Props = {
  alcoholName: PostInterface["alcoholName"];
  alcoholType: PostInterface["alcoholType"];
};

const AlcoleNameTag = ({ alcoholName, alcoholType }: Props) => {
  return (
    <Box sx={WrapperStyle}>
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
          variant="outlined"
          sx={ChipStyle}
        />
        <Typography variant="label" color="primary.main">
          {alcoholName}
        </Typography>
      </Box>
      <IconButton sx={{ p: 0 }}>
        <PostSeeMoreIcon style={{ margin: "3px 0" }} />
      </IconButton>
    </Box>
  );
};

const WrapperStyle = {
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

export default AlcoleNameTag;
