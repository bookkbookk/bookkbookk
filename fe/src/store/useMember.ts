import { Member } from "@api/member/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

const isLoginAtom = atom<boolean | undefined>(undefined);
const memberAtom = atom<Member | undefined>(undefined);

const useIsLoginAtom = atom(
  (get) => get(isLoginAtom),
  (_, set, payload: boolean) => {
    set(isLoginAtom, payload);
  }
);

const useMemberAtom = atom(
  (get) => get(memberAtom),
  (_, set, payload: Member) => {
    set(memberAtom, payload);
  }
);

const useIsLoginValue = () => useAtomValue(useIsLoginAtom);
const useSetIsLogin = () => useSetAtom(useIsLoginAtom);
const useIsLogin = () => useAtom(useIsLoginAtom);

const useMemberValue = () => useAtomValue(useMemberAtom);
const useSetMember = () => useSetAtom(useMemberAtom);
const useMember = () => useAtom(useMemberAtom);

export {
  useIsLogin,
  useIsLoginValue,
  useMember,
  useMemberValue,
  useSetIsLogin,
  useSetMember,
};
