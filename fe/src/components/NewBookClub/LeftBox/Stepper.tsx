import { Step, StepLabel, Stepper } from "@mui/material";
import React from "react";
import { NEW_BOOK_CLUB_FUNNEL } from "../constants";

export default function NewBookClubStepper({
  activeStep,
}: {
  activeStep: number;
}) {
  return (
    <Stepper activeStep={activeStep}>
      {Object.values(NEW_BOOK_CLUB_FUNNEL).map((step) => {
        const stepProps: { completed?: boolean } = {};
        const labelProps: { optional?: React.ReactNode } = {};
        return (
          <Step key={step} {...stepProps}>
            <StepLabel {...labelProps}>{step}</StepLabel>
          </Step>
        );
      })}
    </Stepper>
  );
}
