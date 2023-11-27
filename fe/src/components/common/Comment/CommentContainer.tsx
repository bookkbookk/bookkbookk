import { ReactNode } from "react";
import * as S from "./style";

export default function CommentContainer({
  children,
}: {
  children?: ReactNode;
}) {
  return <S.CommentContainer>{children}</S.CommentContainer>;
}
