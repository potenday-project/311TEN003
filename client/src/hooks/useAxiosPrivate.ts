import axios from "@/libs/axios";
import getTokenFromLocalStorage from "@/utils/getTokenFromLocalStorage";

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
