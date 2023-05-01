module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  extends: [
    "plugin:vue/vue3-essential",
    "eslint:recommended",
    "standard-with-typescript",
    "@vue/eslint-config-typescript",
    "@vue/eslint-config-prettier/skip-formatting"
  ],
  overrides: [],
  parserOptions: {
    ecmaVersion: "latest",
    project: "./tsconfig*.json"
  },
  plugins: [
    "vue"
  ],
  rules: {
    "no-cond-assign": "error",
    "indent": ["error", 4]
  }
};
