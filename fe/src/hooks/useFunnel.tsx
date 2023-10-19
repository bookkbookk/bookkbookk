import {
  Children,
  ReactElement,
  ReactNode,
  isValidElement,
  useState,
} from "react";

export type NonEmptyArray<T> = readonly [T, ...T[]];

interface FunnelProps<Steps extends NonEmptyArray<string>> {
  steps: Steps;
  step: Steps[number];
  children:
    | Array<ReactElement<StepProps<Steps>>>
    | ReactElement<StepProps<Steps>>;
}

interface StepProps<Steps extends NonEmptyArray<string>> {
  name: Steps[number];
  children: ReactNode;
}

type RouteFunnelProps<Steps extends NonEmptyArray<string>> = Omit<
  FunnelProps<Steps>,
  "steps" | "step"
>;

type FunnelComponent<Steps extends NonEmptyArray<string>> = ((
  props: RouteFunnelProps<Steps>
) => JSX.Element) & {
  Step: (props: StepProps<Steps>) => JSX.Element;
};

export const useFunnel = <Steps extends NonEmptyArray<string>>(
  steps: Steps,
  options?: { initialStep?: Steps[number] }
): readonly [
  FunnelComponent<Steps>,
  activeStepIndex: number,
  (step: Steps[number]) => void
] => {
  const [step, setStep] = useState(options?.initialStep ?? steps[0]);
  const activeStepIndex = steps.findIndex((s) => s === step);

  const Step = <T extends NonEmptyArray<string>>({
    children,
  }: StepProps<T>) => {
    return <>{children}</>;
  };

  const Funnel = <Steps extends NonEmptyArray<string>>({
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

  const FunnelComponent = Object.assign(
    function RouteFunnel(props: RouteFunnelProps<Steps>) {
      return <Funnel<Steps> steps={steps} step={step} {...props} />;
    },
    {
      Step,
    }
  );

  return [FunnelComponent, activeStepIndex, setStep] as const;
};
