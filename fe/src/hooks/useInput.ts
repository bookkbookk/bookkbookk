import { useState } from "react";

type useInputOptions = {
  initialValue?: string;
  validators: ((value: string) => {
    isValid: boolean;
    message?: string;
  })[];
};

export default function useInput({
  initialValue = "",
  validators = [],
}: useInputOptions) {
  const [value, setValue] = useState(initialValue);
  const [error, setError] = useState<Error | null>(null);

  const onChange = (newValue: string) => {
    const isValidValue = validators.every((validator) => {
      const { isValid, message } = validator(newValue);

      if (!isValid) {
        setError(new Error(message));

        return false;
      }

      return isValid;
    });

    if (isValidValue) {
      setError(null);
    }

    setValue(newValue);
  };

  return {
    value,
    onChange,
    error: error?.message,
  };
}
