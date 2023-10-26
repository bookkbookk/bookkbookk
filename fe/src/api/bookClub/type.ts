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
  status: "open" | "closed";
};
