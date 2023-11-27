import { LOGIN_API_PATH } from "@/const/serverPath";
import { setCookie } from "@/hooks/useSetCookie";
import axios from "@/libs/axios";
import { SigninResponseInterface } from "@/types/auth/signinResponse";
import { isAxiosError } from "axios";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  const { id, password } = await request.json();
  try {
    const { data } = await axios.post<SigninResponseInterface>(LOGIN_API_PATH, {
      id,
      password,
    });
    setCookie({ key: "accessToken", value: data.token, httpOnly: true });
    return NextResponse.json(
      { ...data },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
          "Access-Control-Allow-Headers": "Content-Type, Authorization",
          "Access-Control-Allow-Credentials": "true",
        },
      }
    );
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
        {
          status: httpStatus,
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
            "Access-Control-Allow-Headers": "Content-Type, Authorization",
            "Access-Control-Allow-Credentials": "true",
          },
        }
      );
    }
  }
}
