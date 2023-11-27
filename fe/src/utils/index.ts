import { timeUnits } from "./constants";

export function validateEmail(email: string) {
  const emailRegex = new RegExp(
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  if (!emailRegex.test(email)) {
    return {
      isValid: false,
      message: "올바른 이메일 형식이 아닙니다.",
    };
  }

  return {
    isValid: true,
    message: "",
  };
}

export function formatDate(
  timeStamp: string,
  option?: Intl.DateTimeFormatOptions
) {
  const defaultOption = {
    year: "numeric" as const,
    month: "numeric" as const,
    day: "numeric" as const,
    weekday: "short" as const,
    ...option,
  };

  return new Intl.DateTimeFormat("ko-KR", defaultOption).format(
    new Date(timeStamp)
  );
}

export const convertPastTimestamp = (timestamp: string) => {
  const startDate = new Date(timestamp);

  if (isNaN(startDate.getTime())) {
    throw new Error("Invalid timestamp");
  }

  const currDate = new Date();
  const diffMs = currDate.getTime() - startDate.getTime();

  for (const { unit, threshold, divisor } of timeUnits) {
    if (diffMs < threshold) {
      const value = Math.floor(diffMs / divisor);

      return divisor === 1 ? `${unit}` : `${value}${unit}`;
    }
  }

  const year = startDate.getFullYear();
  const month = startDate.getMonth();
  const date = startDate.getDate();

  return `${year}/${month + 1}/${date}`;
};
