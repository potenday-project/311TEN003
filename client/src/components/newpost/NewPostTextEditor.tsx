"use client";
import React, { useCallback, useState, memo, useEffect } from "react";
import ReactQuill from "react-quill";
import "./quill.core.css";
import "quill-mention";
import "./quill.mention.css";
import { Typography } from "@mui/material";
import { sanitize } from "isomorphic-dompurify";

interface NewPostTextEditorInterface {
  onContentChange: (props: { content: string; tagList: string[] }) => void;
  initialValue?: string;
  maxLength?: number;
}

const NewPostTextEditor = ({
  onContentChange,
  maxLength = 200,
  initialValue,
}: NewPostTextEditorInterface) => {
  const [_mentioningValue, setMentioningValue] = useState("");

  const [tagList, setTagList] = useState<string[]>([]);

  const [content, setContent] = useState(initialValue ?? "");
  const [textLength, setTextLength] = useState(0);

  useEffect(() => {
    onContentChange({ content: sanitize(content), tagList });
  }, [content]);

  const modules = {
    toolbar: false,
    mention: {
      allowedChars: /^[A-Za-z가-힣\sÅÄÖåäö]*$/,
      mentionDenotationChars: ["#"],
      source: useCallback(
        async (
          searchTerm: string,
          renderItem: (
            arg0: { id: number | string; value: string }[] | undefined,
            arg1: any
          ) => void,
          mentionChar: string
        ) => {
          if (mentionChar === "#") {
            // 검색중인 태그를 설정
            setMentioningValue(searchTerm);
          }
          if (searchTerm.length === 0 || searchTerm.includes(" ")) {
            renderItem([], searchTerm);
          } else {
            renderItem([{ id: searchTerm, value: searchTerm }], searchTerm);
          }
        },
        []
      ),
    },
  };

  return (
    <>
      <ReactQuill
        style={{ height: 100 }}
        modules={modules}
        placeholder="입력해주세요"
        onChange={(content, _d, _s, editor) => {
          const textLength = editor.getLength() - 1;
          const parsedTags = editor
            .getContents()
            .filter((op) => op.insert?.mention?.value)
            .reduce(
              (acc: string[], op) => [...acc, op.insert?.mention?.value],
              []
            );
          setTagList(parsedTags);
          setTextLength((prev) => {
            if (textLength < maxLength) {
              return textLength;
            }
            return prev;
          });
          setContent((prev) => {
            if (textLength < maxLength) {
              return content;
            }
            return prev;
          });
        }}
        value={content}
      />
      <Typography
        variant="label"
        color={"text.secondary"}
        sx={{ textAlign: "right" }}
      >
        <Typography
          variant="label"
          color="primary.main"
          fontWeight={"bold"}
          component="span"
        >
          {textLength}
        </Typography>
        / {maxLength}자
      </Typography>
    </>
  );
};

export default memo(NewPostTextEditor);
