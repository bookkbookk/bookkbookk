import Main from "@pages/Main";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.home} />
      <Route path={ROUTE_PATH.main} element={<Main />} />
    </>
  )
);
