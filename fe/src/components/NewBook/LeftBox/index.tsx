import Stepper from "@components/common/Stepper";
import { BoxContent } from "@components/common/common.style";
import { NEW_BOOK_FUNNEL } from "@components/constants";
import { useFunnel } from "@hooks/useFunnel/useFunnel";
import { useNavigate } from "react-router-dom";
import BookClubGatheringStep from "./BookClubGatheringStep";
import BookSearchStep from "./BookSearchStep";

export default function NewBookFunnel() {
  const { bookSearch, bookClubGathering } = NEW_BOOK_FUNNEL;
  const funnelSteps = [bookSearch, bookClubGathering] as const;
  const navigate = useNavigate();

  const [Funnel, activeStepIndex, setStep] = useFunnel(funnelSteps, {
    initialStep: bookSearch,
  });

  return (
    <BoxContent sx={{ position: "relative" }}>
      <Stepper activeStep={activeStepIndex} funnel={funnelSteps} />
      <Funnel>
        <Funnel.Step name={bookSearch}>
          <BookSearchStep
            onPrev={() => navigate(-1)}
            onNext={() => setStep(bookClubGathering)}
          />
        </Funnel.Step>
        <Funnel.Step name={bookClubGathering}>
          <BookClubGatheringStep onPrev={() => setStep(bookSearch)} />
        </Funnel.Step>
      </Funnel>
    </BoxContent>
  );
}
