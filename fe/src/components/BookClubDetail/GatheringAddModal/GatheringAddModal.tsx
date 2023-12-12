import Stepper from "@components/common/Stepper";
import {
  ModalBody,
  ModalBox,
  ModalHeader,
} from "@components/common/common.style";
import { NEW_GATHERING_FUNNEL } from "@components/constants";
import { useFunnel } from "@hooks/useFunnel/useFunnel";
import ClearIcon from "@mui/icons-material/Clear";
import { IconButton, Modal, Typography } from "@mui/material";
import CheckGatherings from "./CheckGatherings";
import { GatheringBookChoice } from "./GatheringBookChoice/GatheringBookChoice";
import GatheringInfo from "./GatheringInfo/GatheringInfo";

export default function GatheringAddModal({
  open,
  handleClose,
}: {
  open: boolean;
  handleClose: () => void;
}) {
  const { bookChoice, gathering, checkInfo } = NEW_GATHERING_FUNNEL;
  const funnelSteps = [bookChoice, gathering, checkInfo] as const;

  const [Funnel, activeStepIndex, setStep] = useFunnel(funnelSteps, {
    initialStep: bookChoice,
  });

  return (
    <Modal open={open} onClose={handleClose}>
      <ModalBox>
        <ModalHeader>
          <Typography variant="h4">새로운 모임 생성</Typography>
          <IconButton size="small" onClick={handleClose}>
            <ClearIcon fontSize="inherit" />
          </IconButton>
        </ModalHeader>
        <ModalBody
          sx={{
            display: "flex",
            flexDirection: "column",
            height: "700px",
            position: "relative",
          }}>
          <Stepper activeStep={activeStepIndex} funnel={funnelSteps} />
          <Funnel>
            <Funnel.Step name={bookChoice}>
              <GatheringBookChoice onNext={() => setStep(gathering)} />
            </Funnel.Step>
            <Funnel.Step name={gathering}>
              <GatheringInfo
                onPrev={() => setStep(bookChoice)}
                onNext={() => setStep(checkInfo)}
              />
            </Funnel.Step>
            <Funnel.Step name={checkInfo}>
              <CheckGatherings
                onPrev={() => setStep(gathering)}
                onNext={() => {
                  handleClose();
                  setStep(bookChoice);
                }}
              />
            </Funnel.Step>
          </Funnel>
        </ModalBody>
      </ModalBox>
    </Modal>
  );
}
