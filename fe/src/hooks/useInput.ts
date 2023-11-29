import { useState } from "react";

type useInputOptions = {
  initialValue?: string;
  validators: ((value: string) => {
    isValid: boolean;
    message?: string;
  })[];
  callback?: (value: string) => void;
};

export default function useInput({
  initialValue = "",
  validators = [],
  callback,
}: useInputOptions) {
  const [value, setValue] = useState(initialValue);
  const [error, setError] = useState<Error | null>(null);

  const onChange = (newValue: string) => {
    const isValidValue = validators.every((validator) => {
      const { isValid, message } = validator(newValue);

      if (!isValid) {
        setError(new Error(message));
        newValue === "" && setValue("");

        return false;
      }

      return isValid;
    });

    if (isValidValue) {
      setValue(newValue);
      callback?.(newValue);
      setError(null);
    }
  };

  return {
    value,
    onChange,
    error: error?.message,
  };
}
