import { NonEmptyArray } from "@hooks/useFunnel/type";
import { Stepper as MuiStepper, Step, StepLabel } from "@mui/material";
import React from "react";

export default function Stepper({
  activeStep,
  funnel,
}: {
  activeStep: number;
  funnel: NonEmptyArray<string>;
}) {
  return (
    <MuiStepper
      sx={{
        padding: "1.5rem 0",
        margin: "0 auto",
      }}
      activeStep={activeStep}>
      {funnel.map((step) => {
        const stepProps: { completed?: boolean } = {};
        const labelProps: { optional?: React.ReactNode } = {};

        return (
          <Step key={step} {...stepProps}>
            <StepLabel {...labelProps}>{step}</StepLabel>
          </Step>
        );
      })}
    </MuiStepper>
  );
}
