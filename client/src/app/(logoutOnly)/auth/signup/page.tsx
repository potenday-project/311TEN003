"use client";

import { ChangeEvent, useContext, useCallback, useState } from "react";
import useSignupMutation from "@/queries/auth/useSignupMutation";
import SignupPageContext from "@/store/auth/SignupPageContext";
import useMultistepForm from "@/hooks/useMultistepForm";
import SignupStep from "@/components/auth/signup/SignupStep";
import { TextField, LinearProgress } from "@mui/material";
import CustomAppbar from "@/components/CustomAppbar";
import { useRouter } from "next/navigation";
import HOME from "@/const/clientPath";
import { HomeOutlined } from "@mui/icons-material";
import { SignupRequirement } from "@/types/auth/signupRequirement";
import FixedBottomCTA from "@/components/FixedBottomCTA";

export default function SignUpPage() {
  const { formData, setFormData, disableBtn } = useContext(SignupPageContext);
  const router = useRouter();

  const changeHandler = useCallback(
    ({ target }: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setFormData((prev) => ({ ...prev, [target.name]: target.value }));
    },
    []
  );
  const [doubleCheckPassword, setDoubleCheckPassword] = useState<string>("");

  const { mutateAsync: signupHandler } = useSignupMutation();
  const submitHandler = useCallback(async (data: SignupRequirement) => {
    try {
      await signupHandler(data);
    } catch (err) {
      goTo(0);
    }
  }, []);

  const {
    next,
    MultistepForm,
    isFirstStep,
    isLastStep,
    goTo,
    totalPageNum,
    currentIndex,
  } = useMultistepForm(
    [
      <SignupStep
        title={`원활한 환경을 위해\n이메일을 입력해 주세요😃`}
        error={
          formData.email
            ? !new RegExp(
                /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/
              ).test(formData.email)
            : undefined
        }
      >
        <TextField
          name="email"
          label="이메일"
          type="email"
          value={formData.email}
          helperText="이메일을 입력해 주세요."
          onChange={changeHandler}
        />
      </SignupStep>,

      <SignupStep
        title={`🔐\n비밀번호를 입력해 주세요`}
        error={
          formData.password
            ? !new RegExp(
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/
              ).test(formData.password)
            : undefined
        }
      >
        <TextField
          name="password"
          autoComplete="new-password"
          label="비밀번호"
          type="password"
          value={formData.password}
          placeholder="비밀번호를 입력해주세요"
          helperText="8~20자 대소문자, 숫자, 특수기호가 들어가야해요"
          onChange={changeHandler}
        />
      </SignupStep>,

      <SignupStep
        title={`🔒🔒\n한번 더 입력해 주세요`}
        error={
          doubleCheckPassword
            ? !new RegExp(
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/
              ).test(doubleCheckPassword) ||
              doubleCheckPassword !== formData.password
            : undefined
        }
      >
        <TextField
          name="passwordcheck"
          autoComplete="new-password"
          label="비밀번호"
          type="password"
          value={doubleCheckPassword}
          placeholder="비밀번호를 입력해주세요"
          helperText="8~20자 대소문자, 숫자, 특수기호가 들어가야해요"
          onChange={({ target }) => setDoubleCheckPassword(target.value)}
        />
      </SignupStep>,

      <SignupStep
        title={`거의 다 끝나가요!\n아이디와 닉네임을 설정해주세요🤓`}
        error={
          formData.id ? !!formData.id && !(formData.id.length > 2) : undefined
        }
      >
        <TextField
          name="id"
          autoComplete="username"
          label="아이디"
          type="id"
          placeholder="아이디를 입력해주세요"
          helperText="이메일 노출없이 아이디로 대체해요"
          value={formData.id}
          onChange={changeHandler}
        />
      </SignupStep>,

      <SignupStep
        title={`거의 다 끝나가요!\n아이디와 닉네임을 설정해주세요🤓`}
        error={
          formData.nickname
            ? !!formData.nickname && !(formData.nickname.length > 2)
            : undefined
        }
      >
        <TextField
          name="nickname"
          label="닉네임"
          placeholder="닉네임을 입력해주세요"
          value={formData.nickname}
          autoComplete="off"
          helperText="사용할 닉네임을 입력해 주세요."
          onChange={changeHandler}
        />
      </SignupStep>,
    ],
    0
  );

  return (
    <>
      <CustomAppbar
        appendButton={"취소"}
        prependButton={isFirstStep ? <HomeOutlined sx={{ p: 0 }} /> : undefined}
        onClickPrepend={() => (isFirstStep ? router.push(HOME) : router.back())}
        onClickAppend={() => router.push(HOME)}
      />
      <LinearProgress
        variant="determinate"
        value={(currentIndex / (totalPageNum - 1)) * 100}
      />
      {MultistepForm}
      <FixedBottomCTA
        onClick={() => {
          !isLastStep ? next() : submitHandler(formData);
        }}
        size="large"
        disabled={disableBtn}
      >
        {!isLastStep ? "다음" : "투파이아 시작하기"}
      </FixedBottomCTA>
    </>
  );
}
