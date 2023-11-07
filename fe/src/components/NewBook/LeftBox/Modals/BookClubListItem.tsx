import { BookClubProfile } from "@api/bookClub/type";
import { ListItemButton } from "@components/common/common.style";
import CheckIcon from "@mui/icons-material/Check";
import { Avatar, ListItem, Typography } from "@mui/material";
import { useSetBookClubChoice } from "store/newBook/useBookClubChoice";

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
      <ListItemButton onClick={onSelectButtonClick}>
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
      </ListItemButton>
    </ListItem>
  );
}
