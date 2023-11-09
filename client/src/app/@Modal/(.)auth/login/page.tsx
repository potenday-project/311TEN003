import LogoutOnlyLayout from "@/app/(logoutOnly)/layout";
import ModalWrapper from "@/components/ModalWrapper";
import LoginPage from "@/app/(logoutOnly)/auth/login/page";

const LoginModalPage = () => {
  return (
    <LogoutOnlyLayout>
      <ModalWrapper>
        <LoginPage.SigninForm />
        <LoginPage.CTA />
      </ModalWrapper>
    </LogoutOnlyLayout>
  );
};

export default LoginModalPage;
