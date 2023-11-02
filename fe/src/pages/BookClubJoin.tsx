import { useAuthBookClub } from "@api/bookClub/queries";
import BookClubJoinGuide from "@components/BookClubJoinGuide";
import StatusIndicator from "@components/common/StatusIndicator/StatusIndicator";
import { Container } from "@components/common/common.style";
import { MESSAGE } from "@constant/index";
import { useEffect } from "react";
import { Navigate, useParams } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";
import { useIsLoginValue } from "store/useMember";

export default function BookClubJoin() {
  const isLogin = useIsLoginValue();
  const { bookClubCode } = useParams<{ bookClubCode: string }>();

  const { bookClubInfo, isLoading, isSuccess, isError } = useAuthBookClub({
    bookClubCode: bookClubCode!,
    isLogin: !!isLogin,
  });

  if (!isLogin) {
    sessionStorage.setItem("bookClubCode", bookClubCode!);
  }

  useEffect(() => {
    if (isSuccess) {
      sessionStorage.removeItem("bookClubCode");
    }
  }, [isSuccess]);

  return (
    <Container>
      {!isLogin && <BookClubJoinGuide />}
      {isLoading && (
        <StatusIndicator
          status="loading"
          message={MESSAGE.BOOK_CLUB_JOIN_LOADING}
        />
      )}
      {isError && (
        <StatusIndicator
          status="error"
          message={MESSAGE.BOOK_CLUB_JOIN_ERROR}
        />
      )}
      {isLogin && isSuccess && bookClubInfo && (
        <Navigate
          to={`${ROUTE_PATH.bookClub}/${bookClubInfo.bookClubId}`}
          replace
        />
      )}
    </Container>
  );
}
