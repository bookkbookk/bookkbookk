import { atom, useAtom, useAtomValue, useSetAtom } from "jotai";
import { Info, ReducerAction } from "./type";

type TopicList = Map<number, Info[]>;

type TopicListAtomActionMap = {
  ADD_TOPIC: { chapterIndex: number };
  DELETE_TOPIC: { chapterIndex: number; topicIndex: number };
  DELETE_CHAPTER: { chapterIndex: number };
  UPDATE_TOPIC: { chapterIndex: number; topicIndex: number; info: Info };
};

type TopicsAtomAction = ReducerAction<TopicListAtomActionMap>;

const topicListAtom = atom<TopicList>(new Map());

const useTopicListAtom = atom(
  (get) => get(topicListAtom),
  (_, set, action: TopicsAtomAction) => {
    switch (action.type) {
      case "ADD_TOPIC":
        set(topicListAtom, (prev) => {
          const newTopic = new Map(prev);
          const topics = prev.get(action.payload.chapterIndex);

          topics
            ? topics.push({ title: "" })
            : newTopic.set(action.payload.chapterIndex, [{ title: "" }]);

          return newTopic;
        });
        break;
      case "UPDATE_TOPIC":
        set(topicListAtom, (prev) => {
          const newTopicList = new Map(prev);
          const topics = prev.get(action.payload.chapterIndex) ?? [];
          const newTopics = topics.map((topic, index) =>
            index === action.payload.topicIndex
              ? { ...topic, ...action.payload.info }
              : topic
          );

          return newTopicList.set(action.payload.chapterIndex, newTopics);
        });
        break;
      case "DELETE_TOPIC":
        set(topicListAtom, (prev) => {
          const newTopicList = new Map(prev);
          const topics = prev.get(action.payload.chapterIndex) ?? [];
          const newTopics = topics.filter(
            (_, index) => index !== action.payload.topicIndex
          );

          return newTopicList.set(action.payload.chapterIndex, newTopics);
        });
        break;
      // TODO: 이 방법이 최선일지 고민해보기
      case "DELETE_CHAPTER":
        set(topicListAtom, (prev) => {
          const newTopicList = new Map(prev);

          for (const [key, value] of newTopicList) {
            if (key > action.payload.chapterIndex) {
              newTopicList.set(key - 1, value);
            }
          }

          newTopicList.delete(newTopicList.size - 1);
          return newTopicList;
        });
        break;
    }
  }
);

const useTopicListValue = () => useAtomValue(useTopicListAtom);
const useSetTopicList = () => useSetAtom(useTopicListAtom);
const useTopicList = () => useAtom(useTopicListAtom);

export { useSetTopicList, useTopicList, useTopicListValue };
