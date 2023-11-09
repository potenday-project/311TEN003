import useAuthProtector from "@/hooks/useAuthProtector";
import { ReactNode } from "react";

const AuthProtectorlayout = async ({ children }: { children: ReactNode }) => {
  useAuthProtector();
  return <>{children}</>;
};

export default AuthProtectorlayout;
