import { SigninRequirement } from "@/types/auth/signin"

/**
 * 로그인 관련 로직들이 모여있는 Hook
 * @returns login Handler
 */
export default function useLogin () {
  const loginHandler = (props:SigninRequirement)=>{
    const {email,password} = props
    console.log(`email : ${email}, password : ${password}`)
  }
  return loginHandler
}
