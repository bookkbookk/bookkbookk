import Stepper from "@components/common/Stepper";
import { BoxContent } from "@components/common/common.style";
import { NEW_BOOK_CLUB_FUNNEL } from "@components/constants";
import { useFunnel } from "@hooks/useFunnel/useFunnel";
import { useNavigate } from "react-router-dom";
import { BookClubCongratulation } from "./Congratulation";
import BookClubMember from "./Member";
import BookClubProfile from "./Profile";

export default function NewBookClubFunnel() {
  const { profile, member, congratulation } = NEW_BOOK_CLUB_FUNNEL;
  const funnelSteps = [profile, member, congratulation] as const;
  const navigate = useNavigate();

  const [Funnel, activeStepIndex, setStep] = useFunnel(funnelSteps, {
    initialStep: profile,
  });

  return (
    <BoxContent sx={{ position: "relative" }}>
      <Stepper activeStep={activeStepIndex} funnel={funnelSteps} />
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
