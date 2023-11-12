import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
import axios from "axios";

axios.defaults.xsrfCookieName = "csrftoken";
axios.defaults.xsrfHeaderName = "x-CSRFToken";

/**
 * 쿠키를 싣고가는 요청
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
