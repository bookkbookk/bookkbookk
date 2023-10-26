import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";

type Gathering = {
  id: number;
  type: "ONLINE" | "OFFLINE";
  date: string;
  location: string;
};

type GatheringAtomAction =
  | {
      type: "ADD";
    }
  | {
      type: "REMOVE";
      payload: number;
    };

const gatheringAtom = atom<Gathering[]>([]);

const useGatheringAtom = atom(
  (get) => get(gatheringAtom),
  (_, set, action: GatheringAtomAction) => {
    switch (action.type) {
      case "ADD":
        set(gatheringAtom, (prev) => [
          ...prev,
          {
            id: prev.length + 1,
            type: "ONLINE",
            date: "",
            location: "",
          },
        ]);
        break;
      case "REMOVE":
        set(gatheringAtom, (prev) =>
          prev.filter((_, index) => index !== action.payload)
        );
        break;
    }
  }
);

const useGatheringValue = () => useAtomValue(useGatheringAtom);
const useSetGathering = () => useSetAtom(useGatheringAtom);
const useGathering = () => useAtom(useGatheringAtom);

export { useGathering, useGatheringValue, useSetGathering };
