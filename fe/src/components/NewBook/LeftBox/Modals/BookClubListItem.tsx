import { BookClubProfile } from "@api/bookClub/type";
import CheckIcon from "@mui/icons-material/Check";
import { Avatar, ListItem, Typography } from "@mui/material";
import { useSetBookClubChoice } from "store/newBook/useBookClubChoice";
import * as S from "../../style";

export default function BookClubListItem({
  bookClub,
  active,
  onSelectItem,
}: {
  bookClub: BookClubProfile;
  active: boolean;
  onSelectItem: () => void;
}) {
  const { name, profileImgUrl } = bookClub;

  const setBookClubChoice = useSetBookClubChoice();

  const onSelectButtonClick = () => {
    setBookClubChoice(bookClub);
    onSelectItem();
  };

  return (
    <ListItem sx={{ padding: 0 }}>
      <S.ListItemButton onClick={onSelectButtonClick}>
        <Avatar src={profileImgUrl} alt={name} />
        <Typography variant="body1">{name}</Typography>
        {active && (
          <div
            style={{
              display: "flex",
              flexGrow: 1,
              justifyContent: "flex-end",
            }}>
            <CheckIcon />
          </div>
        )}
      </S.ListItemButton>
    </ListItem>
  );
}
