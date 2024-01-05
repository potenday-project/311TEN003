import hasNoAlcoholImage from "@/assets/images/hasNoAlcoholImage.png";
import { Box } from "@mui/material";
import AlcoholDetailLabel from "./AlcoholDetailLabel";

interface AlcoholDetailThumbnailProps {
  title: string;
  type: string;
  src?: string;
}

const AlcoholDetailThumbnail = ({
  title,
  type,
  src,
}: AlcoholDetailThumbnailProps) => {
  return (
    <Box
      height={410}
      bgcolor={"background.paper"}
      p={2}
      sx={{
        backgroundImage: `url(${src ?? hasNoAlcoholImage.src})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "contain",
        backgroundPosition: "center",
      }}
    >
      <AlcoholDetailLabel title={title} type={type} />
    </Box>
  );
};

export default AlcoholDetailThumbnail;
