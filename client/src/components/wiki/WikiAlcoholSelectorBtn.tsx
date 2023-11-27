import { Stack, Typography, ButtonBase, ButtonBaseProps } from "@mui/material";
import { ReactNode, memo } from "react";

interface WiciAlcoholSelectorBtnProps extends Omit<ButtonBaseProps, "onClick"> {
  isSelected?: boolean;
  title: string;
  iconComponent: ReactNode;
  onClick: () => void;
}

const WikiAlcoholSelectorBtn = ({
  isSelected,
  title,
  iconComponent,
  onClick,
  ...buttonBaseProps
}: WiciAlcoholSelectorBtnProps) => {
  return (
    <ButtonBase onClick={onClick} {...buttonBaseProps}>
      <Stack alignItems="center">
        <Stack
          justifyContent="center"
          alignItems="center"
          sx={{
            borderRadius: "50%",
            width: 56,
            height: 56,
            backgroundColor: isSelected ? "primary.main" : "#F6EAFB",
            transitionDuration: 200,
          }}
        >
          {iconComponent}
        </Stack>
        <Typography sx={{ p: 1 }}>{title}</Typography>
      </Stack>
    </ButtonBase>
  );
};

export default memo(WikiAlcoholSelectorBtn);
