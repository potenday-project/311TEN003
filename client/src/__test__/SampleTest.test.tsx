import "@testing-library/jest-dom";
import { render, screen } from "@testing-library/react";
import { Button } from "@mui/material";

it("App Router: Works with Client Components", () => {
  render(<Button>테스트</Button>);
  expect(screen.getByRole("button")).toHaveTextContent("테트");
});
