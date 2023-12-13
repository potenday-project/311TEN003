import { cloneElement, ReactComponentElement } from "react";

type Props = {
  children: ReactComponentElement<any>;
  count: number;
};

const ComponentRepeater = ({ children, count }: Props) => {
  return (
    <>
      {Array.from(new Array(count)).map((_e, i) =>
        cloneElement(children, { key: i })
      )}
    </>
  );
};

export default ComponentRepeater;
