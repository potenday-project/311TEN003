"use client";

import {
  Box,
  Chip,
  CircularProgress,
  List,
  ListItemButton,
  TextField,
  Typography,
} from "@mui/material";
import { Dispatch, SetStateAction, useEffect, useMemo, useState } from "react";
import AlcholeSearchIcon from "@/assets/icons/AlcholeSearchIcon.svg";
import InputSearchIcon from "@/assets/icons/InputSearchIcon.svg";
import useGetAlcoholListQuery from "@/queries/alcohol/useGetAlcoholListQuery";
import { AlcoholDetailInterface } from "@/types/alcohol/AlcoholInterface";
import AlcoleNameTag from "./../post/AlcoleNameTag";
import useDebounce from "@/hooks/useDebounce";
import { NewPostRequest_AlCohol } from "@/types/newPost/NewPostInterface";
import React from "react";

interface SearchAlcoholInputInterface {
  setAlcoholInfo: Dispatch<SetStateAction<NewPostRequest_AlCohol | undefined>>;
}
const SearchAlcoholInput = ({
  setAlcoholInfo,
}: SearchAlcoholInputInterface) => {
  const [searchKeyword, setSearchKeyword] = useState("");
  const debouncedValue = useDebounce(searchKeyword, 300);

  const [selectedAlcohol, setSelectedAlcohol] =
    useState<AlcoholDetailInterface>();

  const { data, isLoading, isSuccess } = useGetAlcoholListQuery(debouncedValue);
  const [isSearchingAlcohol, setIsSearchingAlCohol] = useState(false);

  const parsedDTO = useMemo<NewPostRequest_AlCohol | undefined>(() => {
    if (!selectedAlcohol) {
      return;
    }
    const { alcoholNo, alcoholName, alcoholType, ...others } = selectedAlcohol;
    return {
      alcoholNo,
      alcoholName,
      alcoholType,
    };
  }, [selectedAlcohol]);

  useEffect(() => {
    setSearchKeyword(selectedAlcohol?.alcoholName ?? "");
    setAlcoholInfo(parsedDTO);
  }, [selectedAlcohol]);

  return (
    <>
      <TextField
        placeholder="지금 어떤 술을 마시고 있나요?"
        name="positionInfo"
        size="small"
        InputProps={{
          startAdornment: <AlcholeSearchIcon />,
          endAdornment: <InputSearchIcon />,
        }}
        onChange={({ target }) => setSearchKeyword(target.value)}
        value={searchKeyword}
        onFocus={() => setIsSearchingAlCohol(true)}
        onBlur={() => setIsSearchingAlCohol(false)}
        sx={{ px: 0 }}
        autoComplete="off"
      />
      {isSearchingAlcohol && (
        <Box sx={WrapperStyle}>
          <List sx={ListStyle}>
            {isSuccess &&
              data?.list.map((alcoholData) => (
                <ListItemButton
                  key={alcoholData.alcoholNo}
                  disableRipple
                  onMouseDown={(e) => e.preventDefault()}
                  onClick={() => {
                    setSelectedAlcohol(alcoholData);
                    setSearchKeyword(alcoholData.alcoholName);
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
          </List>
        </Box>
      )}
      {selectedAlcohol && (
        <AlcoleNameTag
          alcoholName={selectedAlcohol.alcoholName}
          alcoholType={selectedAlcohol.alcoholType}
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

export default React.memo(SearchAlcoholInput);
