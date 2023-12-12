import { Member } from "@api/member/type";
import { BOOK_CLUB_STATUS } from "@components/constants";

export type BookClubCreationInfo = {
  name: string;
  profileImage?: File;
};

export type BookClubStatus = {
  status: keyof typeof BOOK_CLUB_STATUS;
};

export type NewBookClubInfo = {
  bookClubId: number;
  invitationUrl: string;
};

export type EmailSubmitInfo = {
  bookClubName: string;
  invitationUrl: string;
  memberEmails: string[];
};

export type BookClubProfile = {
  id: number;
  creatorId: number;
  name: string;
  profileImgUrl: string;
};

export type BookClubDetail = BookClubProfile & {
  members: Member[];
  lastBook: {
    name: string;
    author: string;
  };
  createdTime: string;
};

type ClosedBookClubDetail = BookClubDetail & {
  status: "closed";
  closedTime: string;
};

type OpenBookClubDetail = BookClubDetail & {
  status: "open";
  upcomingGatheringDate: string;
};

export type BookClubInfo = OpenBookClubDetail | ClosedBookClubDetail;
