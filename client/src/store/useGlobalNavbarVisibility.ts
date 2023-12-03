import { create } from "zustand";

interface GlobalNavbarVisibility {
  isVisible: boolean;
  setIsVisible: (val: boolean) => void;
}
/**
 * 네비게이션바 (바텀네비게이션)을 표시할지 여부
 */
export const useGlobalNavbarVisibility = create<GlobalNavbarVisibility>(
  (set) => ({
    isVisible: true,
    setIsVisible: (val) => set(() => ({ isVisible: val })),
  })
);
