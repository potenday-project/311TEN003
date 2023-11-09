import useLogoutOnlyProtector from "@/hooks/useLogoutOnlyProtector";
import { ReactNode } from "react";

const LogoutOnlyLayout = async ({ children }: { children: ReactNode }) => {
  useLogoutOnlyProtector();
  return <>{children}</>;
};

export default LogoutOnlyLayout;
