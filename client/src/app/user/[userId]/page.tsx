const page = ({ params }: { params: { userId: string } }) => {
  return <div>{params.userId}님의 page 입니다</div>;
};

export default page;
