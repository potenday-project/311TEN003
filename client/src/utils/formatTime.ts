import dayjs from "dayjs";
import "dayjs/locale/ko";
import relativeTime from "dayjs/plugin/relativeTime";

// 상대적인 시간을 표시하기 위해 플러그인 추가
dayjs.extend(relativeTime);
dayjs.locale("ko"); // 로캘을 한국어로 설정

/**
 * Instagram 스타일의 작성 시간 포맷 함수
 */
const formatTime = (timestamp: string, now?: string) => {
  const postTime = dayjs(timestamp);
  const currentTime = dayjs(now || new Date());

  if (
    currentTime.diff(postTime, "day") < 7 &&
    currentTime.diff(postTime, "day") >= 1
  ) {
    return `${currentTime.diff(postTime, "day")}일 전`;
  }

  // 1주 ~ 1달 까지는 n주 전 으로 표기
  if (
    currentTime.diff(postTime, "day") >= 7 &&
    currentTime.diff(postTime, "month") < 1
  ) {
    return `${currentTime.diff(postTime, "week")}주 전`;
  }

  // 1년전을 일 년 전 이 아닌 1년 전 으로 표기
  if (currentTime.diff(postTime, "year") === 1) {
    return `${currentTime.diff(postTime, "year")}년 전`;
  }
  // 1년 전을 일 년 전 이 아닌 1년 전 으로 표기
  if (currentTime.diff(postTime, "month") === 1) {
    return `${currentTime.diff(postTime, "month")}달 전`;
  }

  // 그 외의 경우에는 상대적인 시간 표시
  return postTime.fromNow();
};

export default formatTime;
