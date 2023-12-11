"use client";

import { SIGNIN } from "@/const/clientPath";
import { usePathname } from "next/navigation";

export default function Layout({ children }: any) {
  const pathname = usePathname();
  const allowedPath = [SIGNIN];

  return allowedPath.some((path) => pathname.startsWith(path))
    ? children
    : null;
}
