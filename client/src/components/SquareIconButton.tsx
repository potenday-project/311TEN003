import { ButtonBase, ButtonBaseProps } from "@mui/material";
import { ReactNode, Ref, forwardRef } from "react";

interface SquareIconButtonProps extends Omit<ButtonBaseProps, "children"> {
  iconComponent: ReactNode;
  children?: ReactNode;
}

const SquareIconButton = ({
  children,
  iconComponent,
  ...ButtonbaseProps
}: SquareIconButtonProps,ref:Ref<HTMLButtonElement>) => {
  return (
    <ButtonBase ref={ref} {...ButtonbaseProps} sx={ButtonBaseStyle}>
      {iconComponent}
      {children}
    </ButtonBase>
  );
};

const ButtonBaseStyle = {
  bgcolor: "#F5F5F5",
  border: "1px solid",
  borderColor: "#E6E6E6",
  width: 72,
  height: 72,
  borderRadius: 1.5,
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  ":hover": {
    bgcolor: "#F0F0F0",
  },
};

export default forwardRef(SquareIconButton);
