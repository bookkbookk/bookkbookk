import { useMemo, useState } from "react";
import { Funnel, Step } from "./Funnel";
import { FunnelProps, NonEmptyArray, StepProps } from "./type";

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

  const FunnelComponent = useMemo(() => {
    return Object.assign(
      function RouteFunnel(props: RouteFunnelProps<Steps>) {
        return <Funnel<Steps> steps={steps} step={step} {...props} />;
      },
      {
        Step,
      }
    );
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [step]);

  return [FunnelComponent, activeStepIndex, setStep] as const;
};
