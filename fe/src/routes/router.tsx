import Auth from "@pages/Auth";
import BookClub from "@pages/BookClub";
import Landing from "@pages/Landing";
import Library from "@pages/Library";
import Main from "@pages/Main";
import NotFound from "@pages/NotFound";
import Layout from "layout";
import { Suspense } from "react";
import {
  Navigate,
  Outlet,
  Route,
  createBrowserRouter,
  createRoutesFromElements,
} from "react-router-dom";
import { ROUTE_PATH } from "./constants";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path={ROUTE_PATH.home} element={<Landing />} />
      <Route
        path={ROUTE_PATH.main}
        element={
          <Layout>
            <Suspense fallback={<div>loading...</div>}>
              <Outlet />
            </Suspense>
          </Layout>
        }>
        <Route index element={<Main />} />
        <Route path={ROUTE_PATH.library} element={<Library />} />
        <Route path={ROUTE_PATH.bookClub} element={<BookClub />} />
      </Route>
      <Route path={ROUTE_PATH.auth} element={<Auth />} />
      <Route path={ROUTE_PATH.notFound} element={<NotFound />} />
      <Route path="*" element={<Navigate to={ROUTE_PATH.notFound} replace />} />
    </>
  )
);
