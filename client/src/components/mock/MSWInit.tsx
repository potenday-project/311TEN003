"use client";
import { useState, type PropsWithChildren, useEffect } from "react";
const isDev = process.env.NEXT_PUBLIC_API_MOCKING==='enable';

interface Props {}

export default function MSWInit({ children }: PropsWithChildren<Props>) {
  const [ready, setReady] = useState(false);

  const init = async () => {
    if (isDev) {
      const initMock = await import("@/mocks/index");
      await initMock.initMSW();
      setReady(() => true);
    }
  };

  useEffect(() => {
    if (ready) return;
    init();
  }, [ready]);
  
  if (!ready) return null;
  return <>{children}</>;
}
