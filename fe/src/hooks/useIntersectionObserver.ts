import { useCallback, useEffect, useRef } from "react";

export const useIntersectionObserver = (
  condition: boolean,
  callback: () => void,
  options?: IntersectionObserverInit
) => {
  const ref = useRef<HTMLDivElement>(null);
  const intersectionObserverCallback = useCallback(
    (entries: IntersectionObserverEntry[], observer: IntersectionObserver) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          observer.unobserve(entry.target);
          condition && callback();
        }
      });
    },
    [condition, callback]
  );

  useEffect(() => {
    if (!ref.current) return;

    const observer = new IntersectionObserver(
      intersectionObserverCallback,
      options
    );
    observer.observe(ref.current);

    return () => observer.disconnect();
  }, [ref, options, intersectionObserverCallback]);

  return ref;
};
