import { create } from "zustand";

interface GlobalSnackbarStore {
  isOpen: boolean;
  message: string;
  variant: SnackbarVariant;
  fireToast: (message: string, variant?: SnackbarVariant) => void;
  closeToast:()=>void
}
export type SnackbarVariant = "neutral" | "success" | "danger" | "warning";

export const useGlobalSnackbarStore = create<GlobalSnackbarStore>((set) => ({
  isOpen: false,
  message: "",
  variant: "neutral",
  fireToast: (message, variant = "neutral") =>
    set({ isOpen: true, message, variant }),
  closeToast: () => set((prev) => ({ ...prev, message: "", isOpen: false })),
}));