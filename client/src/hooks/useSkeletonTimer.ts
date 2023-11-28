import { useEffect, useState } from "react";
/**
 * 시간을 인자로 받아 해당 시간이 지난 후 true를 리턴, 시간이 지나지 않았을 경우 false를 리턴
 * @param time ms 단위, 기본값은 200ms
 * @returns 입력받은 시간이 지났는지 여부
 */
const useSkeletonTimer = (time: number = 200) => {
  const [isTimePassed, setTimer] = useState(false);
  
  useEffect(() => {
    const timerId = setTimeout(() => {
      setTimer(true);
    }, time);
    return () => {
      clearTimeout(timerId);
    };
  }, []);
  return isTimePassed;
};

export default useSkeletonTimer;
