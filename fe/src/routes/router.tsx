import { reissueAccessToken } from "@api/auth/utils";
import { loader as userLoader } from "@api/member/queries";
import Auth from "@pages/Auth";
import BookClub from "@pages/BookClub";
import Landing from "@pages/Landing";
import Library from "@pages/Library";
import Main from "@pages/Main";
import MyPage from "@pages/MyPage";
import NotFound from "@pages/NotFound";
import SignUp from "@pages/SignUp";
import Layout from "layout/Layout";
import {
  Navigate,
  Outlet,
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import Root from "./Root";
import UserProvider from "./UserProvider";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route
      loader={reissueAccessToken}
      path={ROUTE_PATH.home}
      element={<Root />}>
      <Route
        path={ROUTE_PATH.home}
        loader={userLoader}
        element={<UserProvider />}>
        <Route path={ROUTE_PATH.home} element={<Landing />} />
        <Route
          path={ROUTE_PATH.main}
          element={
            <Layout>
              <Outlet />
            </Layout>
          }>
          <Route index element={<Main />} />
          <Route path={ROUTE_PATH.library} element={<Library />} />
          <Route path={ROUTE_PATH.bookClub} element={<BookClub />} />
          <Route path={ROUTE_PATH.myPage} element={<MyPage />} />
        </Route>
      </Route>
      <Route path={ROUTE_PATH.auth} element={<Auth />} />
      <Route path={ROUTE_PATH.signUp} element={<SignUp />} />
      <Route path={ROUTE_PATH.notFound} element={<NotFound />} />
      <Route path="*" element={<Navigate to={ROUTE_PATH.notFound} replace />} />
    </Route>
  )
);
