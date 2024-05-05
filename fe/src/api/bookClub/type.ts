import { Member } from "@api/member/type";

export type BookClubCreationInfo = {
  name: string;
  profileImage?: File;
};

export type BookClubProfile = {
  id: number;
  createdId: number;
  name: string;
  profileImgUrl: string;
};

export type BookClubStatus = {
  status: "OPEN" | "CLOSED";
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

export type BookClubDetail = {
  name: string;
  profileImgUrl: string;
  members: Member[];
  lastBook: {
    name: string;
    author: string;
  };
  createdTime: string;
};

export type ClosedBookClubDetail = BookClubDetail & {
  status: "CLOSED";
  closedTime: string;
};

export type OpenBookClubDetail = BookClubDetail & {
  status: "OPEN";
  upcomingGatheringDate: string;
};
