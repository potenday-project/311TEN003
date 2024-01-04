/**
 * 브라우저 클립보드에 Text 를 복사시키는 핸들러, HTTPS || Localhost 에서만 동작
 * @param content 복사시킬 내용
 * @param onError 에러시 사용할 함수
 * @param onSuccess 성공시 사용할 함수
 * @param onSettle 성공/실패 여부에 관계없이 완료후 사용할 함수
 */

interface CopyToClipboardOptions {
  onError?: (err: unknown) => void;
  onSuccess?: () => void;
  onSettle?: () => void;
}

const copyToClipboard = async (
  content: string,
  { onError, onSettle, onSuccess }: CopyToClipboardOptions
) => {
  try {
    await navigator.clipboard.writeText(content);
    onSuccess && onSuccess();
  } catch (err) {
    onError && onError(err);
  } finally {
    onSettle && onSettle();
  }
};

export default copyToClipboard;
