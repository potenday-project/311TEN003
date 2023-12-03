"use client";

import { NEW_POST, SIGNIN } from "@/const/clientPath";
import { usePathname } from "next/navigation";

export default function Layout({ children }: any) {
  const pathname = usePathname();
  const allowedPath = [NEW_POST, SIGNIN];

  return allowedPath.some((path) => pathname.startsWith(path))
    ? children
    : null;
}
