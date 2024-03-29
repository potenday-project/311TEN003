"use client";

import {
  Box,
  Chip,
  CircularProgress,
  List,
  ListItemButton,
  TextField,
  Typography,
  InputAdornment,
} from "@mui/material";
import { memo, useEffect, useState } from "react";
import AlcholeSearchIcon from "@/assets/icons/AlcholeSearchIcon.svg";
import InputSearchIcon from "@/assets/icons/InputSearchIcon.svg";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import AlcoholNameTag from "@/components/wiki/AlcoholNameTag";
import useDebounce from "@/hooks/useDebounce";
import { NewPostRequestAlCohol } from "@/types/newPost/NewPostInterface";

interface SearchAlcoholInputInterface {
  setAlcoholNo: (alcoholNo: NewPostRequestAlCohol["alcoholNo"]) => void;
  initialValue?: Pick<
    AlcoholDetailInterface,
    "alcoholName" | "alcoholNo" | "alcoholType"
  >;
}
const SearchAlcoholInput = ({
  setAlcoholNo,
  initialValue,
}: SearchAlcoholInputInterface) => {
  // 유저가 검색한 키워드
  const [searchKeyword, setSearchKeyword] = useState<string>("");
  // 검색한 키워드의 Debounced 값
  const debouncedValue = useDebounce(searchKeyword, 300);
  const [isSearchingAlcohol, setIsSearchingAlCohol] = useState(false);

  // 검색결과
  const { data, isLoading, isSuccess } = useGetAlcoholListQuery({
    searchKeyword: debouncedValue,
  });
  // 유저가 검색후 최종적으로 선택한 값
  const [selectedAlcohol, setSelectedAlcohol] = useState<
    | Pick<AlcoholDetailInterface, "alcoholName" | "alcoholNo" | "alcoholType">
    | undefined
  >(initialValue);

  useEffect(() => {
    setSearchKeyword(selectedAlcohol?.alcoholName ?? "");
    setAlcoholNo(selectedAlcohol?.alcoholNo);
  }, [selectedAlcohol]);

  return (
    <>
      <TextField
        placeholder="지금 어떤 술을 마시고 있나요?"
        name="positionInfo"
        size="small"
        InputProps={{
          sx: {
            borderRadius: 12,
          },
          startAdornment: (
            <InputAdornment position="start">
              <AlcholeSearchIcon />
            </InputAdornment>
          ),
          endAdornment: (
            <InputAdornment position="end">
              <InputSearchIcon />
            </InputAdornment>
          ),
        }}
        onChange={({ target }) => setSearchKeyword(target.value)}
        value={searchKeyword}
        onFocus={() => setIsSearchingAlCohol(true)}
        onBlur={() => setIsSearchingAlCohol(false)}
        autoComplete="off"
      />
      {/* FIXME List 컴포넌트로 분리 */}
      {isSearchingAlcohol && data && (
        <Box sx={WrapperStyle}>
          <List sx={ListStyle}>
            {isSuccess &&
              data?.content.map((alcoholData) => (
                <ListItemButton
                  key={alcoholData.alcoholNo}
                  disableRipple
                  onMouseDown={(e) => e.preventDefault()}
                  onClick={() => {
                    setSelectedAlcohol(alcoholData);
                    setIsSearchingAlCohol(false);
                  }}
                  sx={ListItemButtonStyle}
                >
                  <Box sx={FlexboxStyle}>
                    <Chip label={alcoholData.alcoholType} variant="outlined" />
                    <Typography color="primary.main">
                      {alcoholData.alcoholName}
                    </Typography>
                  </Box>
                  <AlcholeSearchIcon />
                </ListItemButton>
              ))}
            {isLoading && <CircularProgress sx={{ margin: "0 auto" }} />}
            {data?.content.length === 0 && <>검색결과가 없어요</>}
          </List>
        </Box>
      )}
      {selectedAlcohol && (
        <AlcoholNameTag
          alcoholName={selectedAlcohol.alcoholName}
          alcoholType={selectedAlcohol.alcoholType}
          alcoholNo={selectedAlcohol.alcoholNo}
          onClickRemove={() => setSelectedAlcohol(undefined)}
          removable
        />
      )}
    </>
  );
};

const WrapperStyle = {
  width: "calc(100% - 32px)",
  minHeight: "50px",
  maxHeight: "142px",
  overflowY: "auto",
  backgroundColor: "#F5F5F5",
  border: "1px solid #E6E6E6",
  borderRadius: 1.5,
  position: "absolute",
  top: "64px",
  zIndex: 1,
};
const ListStyle = {
  display: "flex",
  flexDirection: "column",
  py: 1,
  px: 2,
  gap: 0.5,
};
const ListItemButtonStyle = {
  p: "1px",
  borderRadius: 12,
  justifyContent: "space-between",
};
const FlexboxStyle = {
  display: "flex",
  flexDirection: "row",
  alignItems: "center",
  gap: 1,
};

export default memo(SearchAlcoholInput);
