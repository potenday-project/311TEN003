/**
 * 선택할 아이템을 2가지 받아 둘중 하나를 랜덤으로 선택해 리턴
 * @param first 선택할 아이템
 * @param second 선택할 아이템
 * @returns 
 */
export const randomSelect = <T>(first: T, second: T) => {
  return randomBoolean() ? first : second;
};
/**
 * 0~10사이의 랜덤 숫자를 리턴
 * @returns 
 */
export const randomNumber = ()=>{
  const randomNum = parseInt(String(Math.random() * 10));
  return Number(randomNum)
}
/**
 * true / false 를 랜덤하게 리턴
 * @returns 
 */
export const randomBoolean = ()=>{
  const randomNum = randomNumber();
  return randomNum % 2 === 0
}