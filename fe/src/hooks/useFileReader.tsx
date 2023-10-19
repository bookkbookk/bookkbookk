import imageCompression from "browser-image-compression";
import { useCallback, useMemo, useState } from "react";

const compressOptions = {
  maxSizeMB: 1,
  maxWidthOrHeight: 1920,
  useWebWorker: true,
};

export const useFileReader = (option?: { storedFile?: File }) => {
  const [file, setFile] = useState<File | undefined>(option?.storedFile);
  const [previewUrl, setPreviewUrl] = useState<string | undefined>(undefined);
  const reader = useMemo(() => new FileReader(), []);

  const handleFileChange = useCallback(
    async (e: React.ChangeEvent<HTMLInputElement>) => {
      const uploadedFile = e.target.files?.[0];

      if (!uploadedFile) return;

      const compressedFile = await imageCompression(
        uploadedFile,
        compressOptions
      );

      reader.readAsDataURL(compressedFile);
      reader.onloadend = () => setPreviewUrl(reader.result as string);
      setFile(compressedFile);
    },
    [reader]
  );

  return { file, previewUrl, handleFileChange };
};
