import { Button, ButtonProps, Paper } from "@mui/material";

const FixedBottomCTA = ({ ...props }: ButtonProps) => {
  return (
    <>
      <Button
        sx={{
          position: "fixed",
          bottom: 0,
          left: 0,
          right: 0,
          height: 56,
          borderRadius: 0,
          fontSize: "1rem",
          zIndex: 2,
        }}
        {...props}
      />
      <Paper
        sx={{
          position: "fixed",
          bottom: 0,
          left: 0,
          right: 0,
          height: 56,
          zIndex: 1,
        }}
      ></Paper>
    </>
  );
};

export default FixedBottomCTA;
