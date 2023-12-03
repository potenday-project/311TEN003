import { PostInterface } from "@/types/post/PostInterface";
import { createContext } from "react";

interface PostDetailPageContextInterface {
  data?: PostInterface;
}

const PostDetailPageContext = createContext<PostDetailPageContextInterface>({
  data: undefined,
});

export default PostDetailPageContext;
