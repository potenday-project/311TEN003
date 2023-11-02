import type { Preview } from "@storybook/react";
import React from "react";
import ThemeRegistry from "./../src/components/theme/ThemeRegistry";
import { GlobalStyles } from "@mui/material";
import OverrideCSS from "./../src/const/overrideCSS";

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: "^on[A-Z].*" },
    expanded: true,
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
  decorators: [
    (Story) => {
      return (
        <>
          <ThemeRegistry options={{ key: "mui" }}>
            <GlobalStyles styles={OverrideCSS} />
            <Story />
          </ThemeRegistry>
        </>
      );
    },
  ],
};

export default preview;
