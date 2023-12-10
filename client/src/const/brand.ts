/**
 * To let change the name of app
 */
export const nameOfApp = "투파이아" as const;
/**
 * One line brand message to change brand description
 */
export const oneLineMessage = "술에 대한 진짜 이야기" as const;
/**
 * title 을 입력받아 Tupaia | title 형태의 html title 을 리턴
 * @param title 제목으로 사용할 문자열
 * @returns Tupaia | title
 */
export const HTML_TITLE =(title:string)=>`${nameOfApp} | ${title}`