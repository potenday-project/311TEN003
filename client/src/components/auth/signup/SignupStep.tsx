import { TextFieldProps, Typography } from "@mui/material";
import {
  JSXElementConstructor,
  ReactElement,
  cloneElement,
  useContext,
  useEffect,
} from "react";
import { AnimatePresence, motion } from "framer-motion";
import SignupPageContext from "@/store/auth/SignupPageContext";

interface SignupStepProps extends Omit<TextFieldProps, "onChange"> {
  title: string;
  children?: ReactElement<any, string | JSXElementConstructor<any>>;
  error?: boolean;
}

const SignupStep = ({ title, children, error }: SignupStepProps) => {
  const { setDisableBtn } = useContext(SignupPageContext);

  useEffect(() => setDisableBtn(error ?? true), [error]);

  return (
    <AnimatePresence mode="popLayout">
      <motion.div
        initial={{ translateY: "100%",opacity:0 }}
        animate={{ translateY: 0,opacity:1 }}
        exit={{ translateY: "-100%",opacity:0 }}
        style={{
          display: "flex",
          flexDirection: "column",
          gap: "32px",
          padding: "16px",
        }}
      >
        <Typography
          variant="h2"
          color="primary.main"
          fontWeight="bold"
          whiteSpace="pre-line"
        >
          {title}
        </Typography>
        {children && cloneElement(children, { error })}
      </motion.div>
    </AnimatePresence>
  );
};

export default SignupStep;
