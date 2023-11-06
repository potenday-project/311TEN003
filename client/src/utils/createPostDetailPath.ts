/**
 * 유저아이디와 게시글 아이디를 입력받아 /@userId/postId 형태의 String을 리턴
 * @param userId 유저ID
 * @param postId 게시글ID
 * @returns
 */
const createPostUrl = (userId: string, postId: string) => {
  const trimmedUserId = userId.trim();
  const trimmedPostId = postId.trim();
  
  return `/@${trimmedUserId}/${trimmedPostId}`;
};

export default createPostUrl;
