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
  const { invitationCode } = useParams<{ invitationCode: string }>();

  const { bookClubInfo, isLoading, isSuccess, isError } = useAuthBookClub({
    invitationCode: invitationCode!,
    isLogin: !!isLogin,
  });

  if (!isLogin) {
    sessionStorage.setItem("invitationCode", invitationCode!);
  }

  useEffect(() => {
    if (isSuccess) {
      sessionStorage.removeItem("invitationCode");
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
