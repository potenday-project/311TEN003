/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'standalone'
}

//For PWA
const withPWA = require("next-pwa")({
  dest: "public",
  disable: process.env.NODE_ENV === "development",
  runtimeCaching: [],
  sw:'/sw.js',
});
module.exports = withPWA(nextConfig)
