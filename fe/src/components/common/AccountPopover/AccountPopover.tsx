import { useState } from "react";
import AvatarButton from "./AvatarButton";
import Popover from "./Popover";

export default function AccountPopover() {
  const [openTrigger, setOpenTrigger] = useState<HTMLElement | null>(null);

  const handleOpen = (event: React.MouseEvent<HTMLButtonElement>) => {
    setOpenTrigger(event.currentTarget);
  };

  const handleClose = () => {
    setOpenTrigger(null);
  };

  return (
    <>
      <AvatarButton {...{ isOpen: !!openTrigger, handleOpen }} />
      {openTrigger && <Popover {...{ openTrigger, handleClose }} />}
    </>
  );
}
