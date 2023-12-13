import { SEARCH_BY_KEYWORD } from "@/const/clientPath";
import { PostInterface } from "@/types/post/PostInterface";
import { Stack, StackProps, Typography } from "@mui/material";
import Link from "next/link";

interface TagListInterface extends StackProps {
  tags: PostInterface["tagList"];
}
const PostHashTagList = ({ tags, ...others }: TagListInterface) => {
  const uniqueSet = Array.from(new Set(tags));
  return (
    <>
      {uniqueSet?.length > 0 && (
        <Stack
          data-testid="tags"
          pt={2}
          flexDirection={"row"}
          columnGap={1}
          flexWrap={"wrap"}
          {...others}
        >
          {uniqueSet.map((tag, i) => (
            <Link href={SEARCH_BY_KEYWORD(tag)} key={i}>
              <Typography
                component={"span"}
                variant={"label"}
                color="text.secondary"
              >
                {`#${tag}`}
              </Typography>
            </Link>
          ))}
        </Stack>
      )}
    </>
  );
};

export default PostHashTagList;
