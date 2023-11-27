'use client'
import CustomAppbar from "@/components/CustomAppbar";
import SearchIcon from "@/assets/icons/SearchIcon.svg";

const WikiAppbar = () => {
  return (
    <CustomAppbar
      title="술백과"
      buttonComponent={<SearchIcon />}
      onClickButton={() => console.log("눌림")}
    />
  );
};

export default WikiAppbar;
