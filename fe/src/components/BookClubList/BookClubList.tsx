import { useGetBookClubList } from "@api/bookClub/queries";
import { BookClubInfoCard } from "@components/common/BookClubInfo";
import { BOOK_CLUB_TAB } from "@components/constants";
import { Avatar, Box, Stack, styled } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROUTE_PATH } from "routes/constants";

export default function BookClubList({ activeTabID }: { activeTabID: number }) {
  const bookClubList = useGetBookClubList({
    status: BOOK_CLUB_TAB[activeTabID].name,
  });
  const navigate = useNavigate();

  return (
    <BookClubListContainer>
      {bookClubList.map((bookClubDetail) => {
        const isOpenBookClub = bookClubDetail.status === "open";

        return (
          <BookClubInfoCard
            key={bookClubDetail.id}
            onClick={() =>
              navigate(`${ROUTE_PATH.bookClub}/${bookClubDetail.id}`)
            }>
            <BookClubInfoCard.StatusChip
              bookClubStatus={bookClubDetail.status}
            />
            <Stack
              display="flex"
              justifyContent="center"
              alignItems="center"
              gap={1}>
              <Avatar
                sx={{ width: 80, height: 80 }}
                src={bookClubDetail.profileImgUrl}
              />
              <BookClubInfoCard.Title
                name={bookClubDetail.name}
                createdTime={bookClubDetail.createdTime}
                closedTime={
                  isOpenBookClub ? undefined : bookClubDetail.closedTime
                }
              />
              <Stack display="flex" justifyContent="center">
                <BookClubInfoCard.Members members={bookClubDetail.members} />
                <BookClubInfoCard.LastBook
                  bookClubStatus={bookClubDetail.status}
                  lastBook={bookClubDetail.lastBook}
                />
                {isOpenBookClub && (
                  <BookClubInfoCard.UpcomingGatheringDate
                    upcomingGatheringDate={bookClubDetail.upcomingGatheringDate}
                  />
                )}
              </Stack>
            </Stack>
          </BookClubInfoCard>
        );
      })}
    </BookClubListContainer>
  );
}

const BookClubListContainer = styled(Box)({
  "width": "90%",
  "gap": "2rem",
  "display": "flex",
  "paddingTop": "2rem",

  "@media (min-width:300px)": {
    flexDirection: "column",
  },

  "@media (min-width:900px)": {
    flexDirection: "row",
    flexWrap: "wrap",
  },
});
