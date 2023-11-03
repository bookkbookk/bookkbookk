export function validateEmail(email: string) {
  const emailRegex = new RegExp(
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  );

  if (!emailRegex.test(email)) {
    return {
      isValid: false,
      message: "올바른 이메일 형식이 아닙니다.",
    };
  }

  return {
    isValid: true,
    message: "",
  };
}

export function formatDate(timeStamp: string) {
  return new Intl.DateTimeFormat("ko-KR").format(new Date(timeStamp));
}
