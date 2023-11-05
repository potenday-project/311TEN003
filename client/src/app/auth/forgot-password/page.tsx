type Props = {};
import { nameOfApp } from "@/const/brand";
import { Metadata } from "next";

export const metaData: Metadata = {
  title: `${nameOfApp} | 비밀번호 재설정`,
};

const ForgotPasswordPage = (props: Props) => {
  return <div>비밀번호 재설정 페이지</div>;
};

export default ForgotPasswordPage;
