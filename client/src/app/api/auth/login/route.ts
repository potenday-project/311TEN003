import { LOGIN_API_PATH } from "@/const/serverPath";
import { setCookie } from "@/hooks/useSetCookie";
import axios from "@/libs/axios";
import { SigninResponseInterface } from "@/types/auth/signinResponse";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  const { id, password } = await request.json();
  try {
    const { data } = await axios.post<SigninResponseInterface>(LOGIN_API_PATH, {
      id,
      password,
    });
    setCookie({ key: "accessToken", value: data.token, httpOnly: true });
    return NextResponse.json({ ...data });
  } catch {
    return NextResponse.json({ message: "로그인 실패" }, { status: 400 });
  }
}
