module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  extends: [
    "plugin:vue/vue3-essential",
    "standard-with-typescript",
    "plugin:prettier/recommended"
  ],
  overrides: [],
  parserOptions: {
    project: "./tsconfig*.json",
    ecmaVersion: "latest",
    sourceType: "module"
  },
  plugins: [
    "vue",
    "prettier"
  ],
  rules: {
    "no-cond-assign": "error",
    "eqeqeq": "error",
    "indent": ["error", 4, {"SwitchCase": 1}],
    "prettier/prettier": [
      "error",
      {
        endOfLine: "auto",
      },
    ]
  }
};
