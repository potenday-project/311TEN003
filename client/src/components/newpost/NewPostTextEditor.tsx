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
}

const NewPostTextEditor = ({ onContentChange }: NewPostTextEditorInterface) => {
  const [mentioningValue, setMentioningValue] = useState("");

  const [tagList, setTagList] = useState<string[]>([]);

  const [content, setContent] = useState("");
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
        style={{ height: 300 }}
        modules={modules}
        placeholder="입력해주세요"
        onChange={(content, _d, _s, editor) => {
          const parsedTags = editor
            .getContents()
            .filter((op) => op.insert?.mention?.value)
            .reduce(
              (acc: string[], op) => [...acc, op.insert?.mention?.value],
              []
            );
          setTagList(parsedTags);
          setContent(content);
          setTextLength(editor.getLength() - 1);
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
        </Typography>{" "}
        / 200자
      </Typography>
    </>
  );
};

export default memo(NewPostTextEditor);
