const SearchPage = ({
  searchParams,
}: {
  searchParams?: { [key: string]: string | string[] | undefined };
}) => {
  return <div>{searchParams?.keyword}</div>;
};

export default SearchPage;
