import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import Auth from "@pages/Auth";
import BookClub from "@pages/BookClub";
import Landing from "@pages/Landing";
import Library from "@pages/Library";
import Main from "@pages/Main";
import NotFound from "@pages/NotFound";
import SignUp from "@pages/SignUp";
import { getMemberInfo } from "@utils/index";
import Layout from "layout";
import { Suspense } from "react";
import {
  Navigate,
  Outlet,
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import ProtectedRoutes from "./ProtectedRoutes";
import UserProvider from "./UserProvider";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <Route
      loader={getMemberInfo}
      path={ROUTE_PATH.home}
      element={<UserProvider />}>
      <Route path={ROUTE_PATH.home} element={<Landing />} />
      <Route
        path={ROUTE_PATH.main}
        element={
          <Layout>
            <Suspense
              fallback={
                <StatusIndicator status="loading" message="Loading..." />
              }>
              <Outlet />
            </Suspense>
          </Layout>
        }>
        <Route index element={<Main />} />
        <Route path={ROUTE_PATH.library} element={<Library />} />
        <Route path={ROUTE_PATH.bookClub} element={<BookClub />} />
      </Route>
      <Route element={<ProtectedRoutes />}>
        <Route path={ROUTE_PATH.auth} element={<Auth />} />
        <Route path={ROUTE_PATH.signUp} element={<SignUp />} />
      </Route>
      <Route path={ROUTE_PATH.notFound} element={<NotFound />} />
      <Route path="*" element={<Navigate to={ROUTE_PATH.notFound} replace />} />
    </Route>
  )
);
