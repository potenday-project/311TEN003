import { SEARCH_BY_KEYWORD } from "@/const/clientPath";
import { PostInterface } from "@/types/post/PostInterface";
import { Box, BoxProps, Typography } from "@mui/material";
import Link from "next/link";

interface TagListInterface extends BoxProps {
  tags: PostInterface["tags"];
}
const PostHashTagList = ({ tags, ...others }: TagListInterface) => {
  return (
    <>
      {tags?.length > 0 && (
        <Box
          data-testid="tags"
          sx={{
            pt: 2,
            display: "flex",
            flexDirection: "row",
            gap: "8px",
          }}
          {...others}
        >
          {tags.map((tag) => (
            <Link href={SEARCH_BY_KEYWORD(tag)}>
              <Typography
                component={"span"}
                variant={"label"}
                color="text.secondary"
                key={tag}
              >
                {`#${tag}`}
              </Typography>
            </Link>
          ))}
        </Box>
      )}
    </>
  );
};

export default PostHashTagList;
