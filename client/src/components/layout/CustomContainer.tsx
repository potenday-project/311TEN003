import { appbarHeight, navbarHeight } from "@/const/uiSizes";
import { Container, ContainerProps, Paper } from "@mui/material";

interface CustomContainerInterface extends ContainerProps {
  disableMt?: boolean;
}

const CustomContainer = ({
  sx,
  disableMt,
  children,
}: CustomContainerInterface) => {
  return (
    <Container
      sx={{ ...sx, px: { xs: 0, sm: 4 }, mt: disableMt ? 0 : 8 }}
      maxWidth={"lg"}
    >
      <Paper
        sx={{
          display: "flex",
          position: "relative",
          flexDirection: "column",
          gap: 2,
          p: 2,
          minHeight: `calc(100vh - ${appbarHeight} - ${navbarHeight})`,
        }}
      >
        {children}
      </Paper>
    </Container>
  );
};

export default CustomContainer;
