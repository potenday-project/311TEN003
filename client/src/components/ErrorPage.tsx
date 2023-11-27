import { Button, Paper } from "@mui/material";

import hasErrorPage from "@/assets/images/hasError.png";
import Image from "next/image";
import { useEffect } from "react";
import { useErrorHandler } from "@/utils/errorHandler";

const ErrorPage = ({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) => {
  const errorHandler = useErrorHandler();

  useEffect(() => {
    errorHandler(error);
  }, [error]);

  return (
    <Paper
      sx={{
        display: "flex",
        justifyContent: "center",
        flexDirection: "column",
        alignItems: "center",
        height: "calc(100vh - 56px)",
        gap: 2,
      }}
    >
      <Image priority src={hasErrorPage} alt="에러임을 알림" />
      <Button onClick={reset}>다시 시도</Button>
    </Paper>
  );
};

export default ErrorPage;
