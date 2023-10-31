import { Member } from "@api/member/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";
import { ReducerAction } from "./type";

type MemberAtomActionMap = {
  INIT: Member;
  UPDATE: Partial<Member>;
};

type MemberAtomAction = ReducerAction<MemberAtomActionMap>;

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
  (_, set, action: MemberAtomAction) => {
    switch (action.type) {
      case "INIT":
        set(memberAtom, action.payload);
        break;
      case "UPDATE":
        set(memberAtom, (prev) => {
          if (!prev) return prev;
          return { ...prev, ...action.payload };
        });
        break;
    }
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
