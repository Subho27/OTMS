const { createProxyMiddleware } = require("http-proxy-middleware");

const backendHost = process.env.BACKEND_HOST || 'localhost'
const backendPort = process.env.BACKEND_PORT || '8080'

module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: `http://${backendHost}:${backendPort}`,
      changeOrigin: true,
    })
  );
};
