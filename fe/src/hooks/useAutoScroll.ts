import { useEffect, useRef } from "react";

export default function useAutoScroll() {
  const ref = useRef<HTMLDivElement>(null);

  const autoScroll = () => {
    ref.current?.scrollIntoView({ behavior: "smooth", block: "end" });
  };

  useEffect(() => {
    autoScroll();
  }, [ref.current]);

  return ref;
}
