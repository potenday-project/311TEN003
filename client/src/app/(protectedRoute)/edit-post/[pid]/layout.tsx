import CustomAppbar from "@/components/layout/CustomAppbar";
import { ReactNode, Suspense } from "react";
import CustomContainer from "@/components/layout/CustomContainer";

type Props = {
  children: ReactNode;
};

const layout = ({ children }: Props) => {
  return (
    <Suspense
      fallback={
        <>
          <CustomAppbar />
          <CustomContainer />
        </>
      }
    >
      {children}
    </Suspense>
  );
};

export default layout;
