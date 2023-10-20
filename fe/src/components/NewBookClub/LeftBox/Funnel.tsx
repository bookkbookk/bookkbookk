import { BoxContent } from "@components/common/common.style";
import { NonEmptyArray, useFunnel } from "@hooks/useFunnel";
import { useNavigate } from "react-router-dom";
import { StepperWrapper } from "../NewBookClub.style";
import { NEW_BOOK_CLUB_FUNNEL } from "../constants";
import { BookClubCongratulation } from "./Congratulation";
import BookClubMember from "./Member";
import BookClubProfile from "./Profile";
import NewBookClubStepper from "./Stepper";

export default function NewBookClubFunnel() {
  const { profile, member, congratulation } = NEW_BOOK_CLUB_FUNNEL;
  const funnelSteps: NonEmptyArray<string> = [profile, member, congratulation];
  const navigate = useNavigate();

  const [Funnel, activeStepIndex, setStep] = useFunnel(funnelSteps, {
    initialStep: profile,
  });

  return (
    <BoxContent>
      <StepperWrapper>
        <NewBookClubStepper activeStep={activeStepIndex} />
      </StepperWrapper>
      <Funnel>
        <Funnel.Step name={profile}>
          <BookClubProfile
            onPrev={() => navigate(-1)}
            onNext={() => setStep(member)}
          />
        </Funnel.Step>
        <Funnel.Step name={member}>
          <BookClubMember
            onPrev={() => setStep(profile)}
            onNext={() => setStep(congratulation)}
          />
        </Funnel.Step>
        <Funnel.Step name={congratulation}>
          <BookClubCongratulation />
        </Funnel.Step>
      </Funnel>
    </BoxContent>
  );
}
