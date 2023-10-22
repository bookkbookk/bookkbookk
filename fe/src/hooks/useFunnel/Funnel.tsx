import { Children, ReactElement, isValidElement } from "react";
import { FunnelProps, NonEmptyArray, StepProps } from "./type";

export const Step = <T extends NonEmptyArray<string>>({
  children,
}: StepProps<T>) => {
  return <>{children}</>;
};

export const Funnel = <Steps extends NonEmptyArray<string>>({
  steps,
  step,
  children,
}: FunnelProps<Steps>) => {
  const validChildren = Children.toArray(children)
    .filter(isValidElement)
    .filter((i) =>
      steps.includes((i.props as Partial<StepProps<Steps>>).name ?? "")
    ) as Array<ReactElement<StepProps<Steps>>>;

  const targetStep = validChildren.find((child) => child.props.name === step);

  if (targetStep == null) {
    throw new Error(`${step} 스텝 컴포넌트를 찾지 못했습니다.`);
  }

  return <>{targetStep}</>;
};
