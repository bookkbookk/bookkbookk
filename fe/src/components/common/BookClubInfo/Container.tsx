import { Card, CardContent, CardProps, styled } from "@mui/material";

export default function BookClubInfoContainer({
  children,
  ...props
}: {
  children: React.ReactNode;
} & CardProps) {
  return (
    <BookClubCard {...props}>
      <CardContent>{children}</CardContent>
    </BookClubCard>
  );
}

const BookClubCard = styled(Card)({
  "width": "100%",
  "transition": "all 0.2s ease-in-out",
  "&:hover": {
    cursor: "pointer",
    transform: "scale(1.05)",
  },

  "@media (min-width:900px)": {
    width: "30%",
  },
});
