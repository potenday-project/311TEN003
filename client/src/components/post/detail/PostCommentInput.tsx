"use client";
import { InputAdornment, Paper, TextField } from "@mui/material";
import { useCallback, useContext, useState } from "react";
import SubmitCommentIcon from "@/assets/icons/comment/SubmitCommentIcon.svg";
import PostDetailPageContext from "@/store/post/PostDetailPageContext";
import useNewPostCommentMutation from "@/queries/post/comment/useNewPostCommentMutation";

const PostCommentInput = () => {
  const { data: currentData } = useContext(PostDetailPageContext);
  const [isEditing, setIsEditing] = useState(false);
  const [inputValue, setInputValue] = useState("");

  const { mutateAsync: submitForm } = useNewPostCommentMutation(
    currentData?.postNo ? String(currentData?.postNo) : undefined
  );
  const submitHandler = useCallback(
    (content: string) => {
      submitForm(content).then(() => {
        setInputValue("");
      });
    },
    [submitForm, setInputValue]
  );

  return (
    <Paper
      sx={{
        backgroundColor: "gray.primary",
        border: "1px solid",
        borderColor: "gray.secondary",
        position: "fixed",
        bottom: isEditing ? 0 : 56,
        borderRadius: 1.5,
        left: 0,
        right: 0,
        p: 2,
        zIndex: 2,
      }}
      elevation={0}
    >
      <TextField
        fullWidth
        onFocus={() => {
          setIsEditing(true);
        }}
        onBlur={() => {
          setIsEditing(false);
        }}
        size="small"
        autoComplete="off"
        placeholder="회원님의 생각을 올려보세요"
        multiline
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
        rows={isEditing ? 5 : 1}
        InputProps={{
          endAdornment: (
            <InputAdornment
              position="end"
              onClick={(e) => {
                e.stopPropagation();
                submitHandler(inputValue);
              }}
              sx={{
                color: inputValue.length > 0 ? "primary.main" : "text.disabled",
              }}
            >
              <SubmitCommentIcon />
            </InputAdornment>
          ),
        }}
        sx={{ backgroundColor: "background.paper" }}
      />
    </Paper>
  );
};

export default PostCommentInput;
