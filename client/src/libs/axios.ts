import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import axios from "axios";

axios.defaults.xsrfCookieName = "csrftoken";
axios.defaults.xsrfHeaderName = "x-CSRFToken";

/**
 * 토큰을 싣고가는 요청
 */
export const axiosPrivate = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  headers: {
    "Content-Type": "application/json",
    Authorization: getTokenFromLocalStorage(),
  },
});

/**
 * 쿠키없이 가는 요청
 */
export default axios.create({ baseURL: process.env.NEXT_PUBLIC_BASE_URL });

/**
 * Auth Bff 로 연결되는 요청
 */
export const axiosBff = axios.create({
  baseURL: process.env.NEXT_PUBLIC_CLIENT_BASE_URL,
  withCredentials:true
});