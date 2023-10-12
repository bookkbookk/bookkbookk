import "@emotion/react";
import { designSystem } from "./designSystem";

declare module "@emotion/react" {
  export interface Theme {
    color: typeof designSystem.light.color;
    filter: typeof designSystem.light.filter;
    radius: typeof designSystem.light.radius;
    font: typeof designSystem.light.font;
    opacity: typeof designSystem.light.opacity;
    palette: typeof designSystem.light.palette;
  }
}
