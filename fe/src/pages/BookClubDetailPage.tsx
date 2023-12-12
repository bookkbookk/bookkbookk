import { useGetBookClubDetail } from "@api/bookClub/queries";
import GatheringAddModal from "@components/BookClubDetail/GatheringAddModal/GatheringAddModal";
import BookClubInfo from "@components/BookClubDetail/LeftBox/BookClubInfo";
import BookClubMembersCard from "@components/BookClubDetail/RightBox/BookClubMembersCard";
import {
  AddFab,
  BoxContent,
  LeftBox,
  RightBox,
} from "@components/common/common.style";
import GroupsIcon from "@mui/icons-material/Groups";
import { Box, Tooltip } from "@mui/material";
import { useState } from "react";
import { useParams } from "react-router-dom";

export default function BookClubDetailPage() {
  const { bookClubId } = useParams<{ bookClubId: string }>();
  const { bookClubDetail } = useGetBookClubDetail(Number(bookClubId));

  const [isModalOpen, setIsModalOpen] = useState(false);
  const handleOpen = () => setIsModalOpen(true);
  const handleClose = () => setIsModalOpen(false);

  return (
    <Box sx={{ display: "flex", height: "100%" }}>
      <LeftBox sx={{ justifyContent: "space-between", paddingY: 4 }}>
        {bookClubDetail && <BookClubInfo {...{ bookClubDetail }} />}
        {/* TODO: 모임 목록 조회*/}
      </LeftBox>
      <RightBox>
        <BoxContent>
          {bookClubDetail && (
            <BookClubMembersCard members={bookClubDetail.members} />
          )}
        </BoxContent>
      </RightBox>
      <Tooltip title="새로운 모임을 추가해보세요">
        <AddFab color="primary" aria-label="add" onClick={handleOpen}>
          <GroupsIcon />
        </AddFab>
      </Tooltip>
      <GatheringAddModal open={isModalOpen} handleClose={handleClose} />
    </Box>
  );
}
