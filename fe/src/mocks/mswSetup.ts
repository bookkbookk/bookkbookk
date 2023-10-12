import browserServiceWorker from "../mocks/browser";

if (process.env.NODE_ENV === "development") {
  browserServiceWorker.start({
    onUnhandledRequest: "bypass",
  });
}
