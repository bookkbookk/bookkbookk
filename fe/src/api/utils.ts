// eslint-disable-next-line
export const makeFormData = (form: any) => {
  const formData = new FormData();

  Object.keys(form).forEach((key) => {
    form[key] && formData.append(key, form[key]);
  });

  return formData;
};
