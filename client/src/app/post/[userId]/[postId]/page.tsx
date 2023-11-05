const PostDetailPage = ({
  params,
}: {
  params: { userId: string; postId: string };
}) => {
  const parsedUserId = params.userId.slice(1, params.userId.length);
  const parsedPostId = params.postId.slice(1, params.postId.length);
  return (
    <div>
      userId:{parsedUserId}
      <br /> postId:{parsedPostId}
    </div>
  );
};

export default PostDetailPage;
