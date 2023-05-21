const PRODUCTION_NAME = 'Pinnacle OA'
const TOKEN_NAME = 'JWT_TOKEN'
const COLOR_PRODUCTION = '#00D4FF'
const COLOR_BACKGROUND = '#D8D8D8'
const COLOR_FONT_MAIN = '#4D4D4D'
const COLOR_FONT_SECONDARY = '#9E9E9E'
const SIZE_ICON_XS = '16px'
const SIZE_ICON_SM = '20px'
const SIZE_ICON_MD = '24px'
const SIZE_ICON_LG = '32px'
const SIZE_ICON_XL = '64px'

// Response Code
const SYSTEM_OK = 20000
const LOGIN_SUCCESS = 20010
const LOGIN_USERNAME_PASSWORD_ERROR = 20011
const LOGOUT_SUCCESS = 20015
const LOGOUT_FAILED = 20016
const TOKEN_IS_ILLEGAL = 20017
const TOKEN_HAS_EXPIRED = 20018
const DATABASE_SELECT_OK = 20021
const DATABASE_SAVE_OK = 20022
const DATABASE_UPDATE_OK = 20023
const DATABASE_DELETE_OK = 20024
const DATABASE_SELECT_ERROR = 20031
const DATABASE_SAVE_ERROR = 20032
const DATABASE_UPDATE_ERROR = 20033
const DATABASE_DELETE_ERROR = 20034
const DATABASE_TIMEOUT_ERROR = 20035
const DATABASE_CONNECT_ERROR = 20036

const UNAUTHORIZED = 30010
const ACCESS_DENIED = 30030
const USER_DISABLE = 30031

const SYSTEM_ERROR = 50001
const SYSTEM_TIMEOUT = 50002

export {
    PRODUCTION_NAME,
    TOKEN_NAME,
    COLOR_PRODUCTION,
    COLOR_BACKGROUND,
    COLOR_FONT_MAIN,
    COLOR_FONT_SECONDARY,
    SIZE_ICON_XS,
    SIZE_ICON_SM,
    SIZE_ICON_MD,
    SIZE_ICON_LG,
    SIZE_ICON_XL,
    SYSTEM_OK,
    LOGIN_SUCCESS,
    LOGIN_USERNAME_PASSWORD_ERROR,
    LOGOUT_SUCCESS,
    LOGOUT_FAILED,
    TOKEN_IS_ILLEGAL,
    TOKEN_HAS_EXPIRED,
    DATABASE_SELECT_OK,
    DATABASE_SAVE_OK,
    DATABASE_UPDATE_OK,
    DATABASE_DELETE_OK,
    DATABASE_SELECT_ERROR,
    DATABASE_SAVE_ERROR,
    DATABASE_UPDATE_ERROR,
    DATABASE_DELETE_ERROR,
    DATABASE_TIMEOUT_ERROR,
    DATABASE_CONNECT_ERROR,
    UNAUTHORIZED,
    ACCESS_DENIED,
    USER_DISABLE,
    SYSTEM_ERROR,
    SYSTEM_TIMEOUT
}
