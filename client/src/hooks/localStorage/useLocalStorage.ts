'use client'
import { useCallback, useEffect, useState } from "react";

const useLocalStorage = <T>(storageKey: string) => {
  /**
   * 로컬스토리지에 아이템을 stringify해 저장하는 함수
   */
  const setItem = useCallback(
    (keyword: T) => {
      localStorage.setItem(storageKey, JSON.stringify(keyword));
    },
    [storageKey]
  );
  /**
   * 로컬 스토리지 아이템을 파싱해서 리턴하는 함수
   */
  const getItems = useCallback((): T | null => {
    if(typeof window ==='undefined'){
      return null
    }
    return JSON.parse(localStorage.getItem(storageKey) || "null");
  }, [storageKey]);

  const [storageValue, setStorageValue] = useState<T | null>(getItems());
  
  // 새로운 값이 저장될 경우, 로컬스토리지에도 같이 저장
  useEffect(() => {
    if (!storageValue) {
      return;
    }
    setItem(storageValue);
  }, [storageValue]);

  return [storageValue, setStorageValue] as const;
};

export default useLocalStorage;
