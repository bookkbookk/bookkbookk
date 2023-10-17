import { getMember } from "@api/member/client";
import { ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "constant/index";
import { ROUTE_PATH } from "routes/constants";

export function logout() {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(REFRESH_TOKEN_KEY);

  window.location.href = ROUTE_PATH.home;
}

export async function getMemberInfo() {
  try {
    const memberInfo = await getMember();
    return { memberInfo };
  } catch (error) {
    console.error(error);
    return null;
  }
}
