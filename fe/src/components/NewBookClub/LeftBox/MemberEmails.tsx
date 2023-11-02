import { Chip, ListItem, Paper } from "@mui/material";

export default function MemberEmails({
  memberEmails,
  deleteMemberEmail,
}: {
  memberEmails: string[];
  deleteMemberEmail: (email: string) => void;
}) {
  return (
    <Paper
      component="ul"
      sx={{
        display: "flex",
        flexDirection: "column",
        padding: 2,
        maxHeight: 400,
        minHeight: 400,
        width: "100%",
        overflow: "scroll",
      }}>
      {memberEmails.map((email) => {
        return (
          <ListItem key={email}>
            <Chip
              label={email}
              onDelete={() => deleteMemberEmail(email)}
              sx={{
                fontSize: 16,
              }}
            />
          </ListItem>
        );
      })}
    </Paper>
  );
}
