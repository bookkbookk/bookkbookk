import Stepper from "@components/common/Stepper";
import { BoxContent } from "@components/common/common.style";
import { NEW_BOOK_FUNNEL } from "@components/constants";
import { useFunnel } from "@hooks/useFunnel/useFunnel";
import { useNavigate } from "react-router-dom";
import BookChapters from "./BookChapters";
import BookClubGathering from "./BookClubGathering";
import BookSearch from "./BookSearch";

export default function NewBookFunnel() {
  const { bookSearch, bookClubGathering, bookChapters } = NEW_BOOK_FUNNEL;
  const funnelSteps = [bookSearch, bookClubGathering, bookChapters] as const;
  const navigate = useNavigate();

  const [Funnel, activeStepIndex, setStep] = useFunnel(funnelSteps, {
    initialStep: bookSearch,
  });

  return (
    <BoxContent>
      <Stepper activeStep={activeStepIndex} funnel={funnelSteps} />
      <Funnel>
        <Funnel.Step name={bookSearch}>
          <BookSearch />
        </Funnel.Step>
        <Funnel.Step name={bookClubGathering}>
          <BookClubGathering />
        </Funnel.Step>
        <Funnel.Step name={bookChapters}>
          <BookChapters />
        </Funnel.Step>
      </Funnel>
    </BoxContent>
  );
}
