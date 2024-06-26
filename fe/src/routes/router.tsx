import { reissueAccessToken } from "@api/auth/utils";
import { loader as userLoader } from "@api/member/queries";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import Auth from "@pages/Auth";
import BookChapter from "@pages/BookChapter";
import BookClub from "@pages/BookClub";
import BookClubJoin from "@pages/BookClubJoin";
import BookClubList from "@pages/BookClubList";
import BookDetail from "@pages/BookDetail";
import Landing from "@pages/Landing";
import Library from "@pages/Library";
import Main from "@pages/Main";
import MyPage from "@pages/MyPage";
import NewBook from "@pages/NewBook";
import NewBookClub from "@pages/NewBookClub";
import NotFound from "@pages/NotFound";
import SignUp from "@pages/SignUp";
import Layout from "layout/Layout";
import { Suspense } from "react";
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
          path={`${ROUTE_PATH.join}/:invitationCode`}
          element={<BookClubJoin />}
        />
        <Route
          path={ROUTE_PATH.main}
          element={
            <Layout>
              <Suspense
                fallback={
                  <StatusIndicator status="loading" message="로딩중.." />
                }>
                <Outlet />
              </Suspense>
            </Layout>
          }>
          <Route index element={<Main />} />
          <Route path={ROUTE_PATH.library} element={<Library />} />
          <Route
            path={`${ROUTE_PATH.bookDetail}/:bookId`}
            element={<BookDetail />}
          />
          <Route
            path={`${ROUTE_PATH.chapter}/:chapterId`}
            element={<BookChapter />}
          />
          <Route path={ROUTE_PATH.newBook} element={<NewBook />} />
          <Route path={ROUTE_PATH.bookClub} element={<BookClubList />} />
          <Route
            path={`${ROUTE_PATH.bookClub}/:bookClubId`}
            element={<BookClub />}
          />
          <Route path={ROUTE_PATH.newBookClub} element={<NewBookClub />} />
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
