"use server";
import { cookies } from "next/headers";

interface SetCookieInterface {
  key: string;
  value: string;
  httpOnly?: boolean;
}
export async function setCookie({ key, value, httpOnly }: SetCookieInterface) {
  cookies().set({
    name: key,
    value: value,
    httpOnly: httpOnly ?? true,
  });
}
