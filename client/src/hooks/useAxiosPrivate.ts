import axios from "@/libs/axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";
/**
 * 헤더에 로컬스토리지에 들어있는 토큰을 싣는 Intercepter 가 달린 Axios
 * @returns AxiosPrivate
 */
const useAxiosPrivate = () => {
  axios.interceptors.request.use(
    (config) => {
      if (config.headers) {
        config.headers.Authorization = getTokenFromLocalStorage();
      }
      return config;
    },
    (error) => Promise.reject(error)
  );
  return axios;
};

export default useAxiosPrivate;
