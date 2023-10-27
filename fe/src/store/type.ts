export type Info = { title: string };

export type ReducerAction<T> = {
  [K in keyof T]: {
    type: K;
    payload: T[K];
  };
}[keyof T];
