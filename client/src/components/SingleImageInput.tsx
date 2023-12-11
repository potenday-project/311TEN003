import React, { InputHTMLAttributes } from "react";

interface SingleImageInputInterface
  extends Omit<
    InputHTMLAttributes<HTMLInputElement>,
    "onChange" | "type" | "capture" | "style" | "name"
  > {
  onChange: (file: File) => void;
}

const SingleImageInput = ({
  onChange,
  ...others
}: SingleImageInputInterface) => {
  return (
    <input
      name="image"
      style={{ display: "none" }}
      type="file"
      accept="image/*"
      onChange={(e) => {
        if (e.target.files) {
          onChange(e.target.files[0]);
        }
      }}
      {...others}
    />
  );
};

export default SingleImageInput;
