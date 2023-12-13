import formatTime from "@/utils/formatTime";

describe("인스타그램 스타일 시간 포매팅 함수", () => {
  const MOCK_NOW = "Thu Dec 14 2023 00:50:25 GMT+0900 (한국 표준시)";
  const THREE_DAYS_AGO = "Thu Dec 11 2023 00:50:25 GMT+0900 (한국 표준시)";
  const EIGHT_DAYS_AGO = "Thu Dec 6 2023 00:50:25 GMT+0900 (한국 표준시)";
  const ONE_MONTH_AGO = "Thu Nov 11 2023 00:50:25 GMT+0900 (한국 표준시)";
  const THREE_MONTH_AGO = "Thu Sep 11 2023 00:50:25 GMT+0900 (한국 표준시)";
  const ONE_YEAR_AGO = "Thu Dec 14 2022 00:50:25 GMT+0900 (한국 표준시)";

  it("3일 전이 입력되었을때 '3일 전' 이 출력되는지", () => {
    expect(formatTime(THREE_DAYS_AGO, MOCK_NOW)).toEqual("3일 전");
  });
  it("8일 전이 입력되었을때 '1주 전' 이 출력되는지", () => {
    expect(formatTime(EIGHT_DAYS_AGO, MOCK_NOW)).toEqual("1주 전");
  });
  it("1달전 이 입력되었을때 '1달 전'이 출력되는지 Trim 하는지 여부", () => {
    expect(formatTime(ONE_MONTH_AGO, MOCK_NOW)).toEqual("1달 전");
  });
  it("3달전 이 입력되었을때 '3달 전'이 출력되는지 Trim 하는지 여부", () => {
    expect(formatTime(THREE_MONTH_AGO, MOCK_NOW)).toEqual("3달 전");
  });
  it("1년전 이 입력되었을때 '1년 전'이 출력되는지 Trim 하는지 여부", () => {
    expect(formatTime(ONE_YEAR_AGO, MOCK_NOW)).toEqual("1년 전");
  });
});
