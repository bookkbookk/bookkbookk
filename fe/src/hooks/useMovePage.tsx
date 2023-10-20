import { useCallback } from "react";
import { useNavigate } from "react-router-dom";

export const useMovePage = (option?: { replace: boolean }) => {
  const navigate = useNavigate();
  const movePage = useCallback(
    (routePath: string) => {
      navigate(routePath, { replace: option?.replace ?? false });
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [option?.replace]
  );

  return { movePage };
};
