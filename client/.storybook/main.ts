import type { StorybookConfig } from "@storybook/nextjs";
import path from "path";

const config: StorybookConfig = {
  stories: ["../src/**/*.mdx", "../src/**/*.stories.@(js|jsx|mjs|ts|tsx)"],
  addons: [
    "@storybook/addon-links",
    "@storybook/addon-essentials",
    "@storybook/addon-onboarding",
    "@storybook/addon-interactions",
  ],
  parameters: {
    nextjs: {
      appDirectory: true,
    },
  },
  framework: {
    name: "@storybook/nextjs",
    options: {},
  },
  docs: {
    autodocs: "tag",
  },
  staticDirs: [
    {
      from: '../src/assets/font',
      to: '/src/assets/font'
    }
  ],
  webpackFinal: async (config, { configType }) => {
    config.resolve!.alias = {
      ...config.resolve!.alias,
      "@": path.resolve(__dirname, "../src"),
      "~": path.resolve(__dirname, "../src"),
    };
    return config;
  },
};
export default config;