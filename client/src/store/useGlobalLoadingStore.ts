import { create } from "zustand";

interface GlobalLoadingStore {
  isLoading: boolean;
  setLoading: (val: boolean) => void;
}

export const useGlobalLoadingStore = create<GlobalLoadingStore>((set) => ({
  isLoading: false,
  setLoading: (val) => set(() => ({ isLoading: val })),
}));
