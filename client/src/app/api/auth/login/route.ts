import { LOGIN_API_PATH } from "@/const/serverPath";
import { setCookie } from "@/hooks/useSetCookie";
import axios from "@/libs/axios";
import { SigninResponseInterface } from "@/types/auth/signinResponse";
import { AxiosResponse, isAxiosError } from "axios";
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
  } catch (error) {
    if (
      isAxiosError<{
        httpStatus: number;
        errorMessage: string;
        detailMessage: string;
      }>(error) &&
      error.response
    ) {
      const { httpStatus, errorMessage, detailMessage } = error.response?.data;
      return NextResponse.json(
        { errorMessage, detailMessage, httpStatus },
        { status: httpStatus }
      );
    }
  }
}
