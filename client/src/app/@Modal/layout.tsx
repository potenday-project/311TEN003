"use client";

import { usePathname } from "next/navigation";

export default function Layout({ children }: any) {
  const pathname = usePathname();
  return pathname.startsWith("/post/") ? children : null;
}
