const minuteInMs = 1000 * 60;
const hourInMs = minuteInMs * 60;
const dayInMs = hourInMs * 24;
const weekInMs = dayInMs * 7;
const monthInMs = weekInMs * 4;
const yearInMs = monthInMs * 12;

export const timeUnits = [
  { unit: "방금 전", threshold: minuteInMs, divisor: 1 },
  { unit: "분 전", threshold: hourInMs, divisor: minuteInMs },
  { unit: "시간 전", threshold: dayInMs, divisor: hourInMs },
  { unit: "일 전", threshold: weekInMs, divisor: dayInMs },
  { unit: "주 전", threshold: monthInMs, divisor: weekInMs },
  { unit: "개월 전", threshold: yearInMs, divisor: monthInMs },
];
