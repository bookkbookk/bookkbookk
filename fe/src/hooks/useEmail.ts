import { send } from "@emailjs/browser";
const {
  VITE_EMAIL_JS_PUBLIC_KEY,
  VITE_EMAIL_JS_SERVICE_ID,
  VITE_EMAIL_JS_TEMPLATE_ID,
} = import.meta.env;

type EmailSubmitInfo = {
  bookClubName: string;
  invitationUrl: string;
  memberEmails: string[];
};

export default function useEmail() {
  const sendEmail = async (emailSubmitInfo: EmailSubmitInfo) => {
    return await send(
      VITE_EMAIL_JS_SERVICE_ID,
      VITE_EMAIL_JS_TEMPLATE_ID,
      emailSubmitInfo,
      VITE_EMAIL_JS_PUBLIC_KEY
    );
  };

  return {
    sendEmail,
  };
}
