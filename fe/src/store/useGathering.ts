import { Book } from "@api/book/type";
import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";
import { v4 as uuidv4 } from "uuid";
import { ReducerAction } from "./type";

type GatheringInfo = {
  dateTime: string;
  place: string;
};

export type Gathering = {
  id: string;
} & GatheringInfo;

type GatheringList = {
  book: Book | null;
  gatherings: Gathering[];
};

type GatheringListAtomActionMap = {
  ADD_GATHERING: GatheringInfo;
  EDIT_GATHERING: { gatheringId: string; info: GatheringInfo };
  DELETE_GATHERING: { gatheringId: string };
  SELECT_BOOK: { book: Book };
};

type GatheringAtomAction =
  | { type: "RESET" }
  | ReducerAction<GatheringListAtomActionMap>;

const gatheringAtom = atom<GatheringList>({ book: null, gatherings: [] });

const useGatheringAtom = atom(
  (get) => get(gatheringAtom),
  (_, set, action: GatheringAtomAction) => {
    switch (action.type) {
      // TODO: sort by date
      case "ADD_GATHERING":
        set(gatheringAtom, (prev) => ({
          ...prev,
          gatherings: [
            ...prev.gatherings,
            {
              id: uuidv4(),
              ...action.payload,
            },
          ],
        }));
        break;
      case "EDIT_GATHERING":
        set(gatheringAtom, (prev) => ({
          ...prev,
          gatherings: prev.gatherings.map((gathering) =>
            gathering.id === action.payload.gatheringId
              ? { ...gathering, ...action.payload.info }
              : gathering
          ),
        }));
        break;
      case "DELETE_GATHERING":
        set(gatheringAtom, (prev) => ({
          ...prev,
          gatherings: prev.gatherings.filter(
            (gathering) => gathering.id !== action.payload.gatheringId
          ),
        }));
        break;
      case "SELECT_BOOK":
        set(gatheringAtom, (prev) => ({
          ...prev,
          book: action.payload.book,
        }));
        break;
      case "RESET":
        set(gatheringAtom, { book: null, gatherings: [] });
        break;
    }
  }
);

const useGatheringValue = () => useAtomValue(useGatheringAtom);
const useSetGathering = () => useSetAtom(useGatheringAtom);
const useGathering = () => useAtom(useGatheringAtom);

export { useGathering, useGatheringValue, useSetGathering };
