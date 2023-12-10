//@ts-ignore
import LogoLarge from "@/assets/icons/LogoLarge.svg?url";
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
      bgcolor={src ? "background.paper" : "gray.primary"}
      p={2}
      sx={{
        backgroundImage: `url(${src ?? LogoLarge.src})`,
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
