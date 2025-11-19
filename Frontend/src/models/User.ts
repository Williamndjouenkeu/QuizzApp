export class User {
  name: string;
  email: string;
  provider: string;
  admin: boolean;
  image: string;
  accessToken: string;
  accessTokenExpirationAt: number;
  refreshToken: string;

  constructor(
    name: string = "",
    email: string = "",
    provider: string = "",
    admin: boolean = false,
    image: string = "",
    accessToken: string = "",
    accessTokenExpirationAt: number = 0,
    refreshToken: string = ""
  ) {
    this.name = name;
    this.email = email;
    this.provider = provider;
    this.admin = admin;
    this.image = image;
    this.accessToken = accessToken;
    this.accessTokenExpirationAt = accessTokenExpirationAt;
    this.refreshToken = refreshToken;
  }

  static parse(accessToken: string, refreshToken: string): User {
    interface AccessTokenPayload {
    name: string;
    sub: string;
    admin: boolean;
    exp: number;
  }
    const res: User = new User();
    const accessTokenPayload: string = accessToken.split('.')[1];
    const parseAccessTokenPayload: AccessTokenPayload  = JSON.parse(atob(accessTokenPayload));
    res.name = parseAccessTokenPayload.name;
    res.email = parseAccessTokenPayload.sub;
    res.admin = parseAccessTokenPayload.admin;
    res.accessTokenExpirationAt = parseAccessTokenPayload.exp;
    res.accessToken = accessToken;
    res.refreshToken = refreshToken;
    return res;
  }

  static isLoggedIn(user: User): boolean {
    return user.name != "" && user.accessTokenExpirationAt * 1000 > Date.now();
  }

  static isNotLogged(user: User): boolean {
    return !User.isLoggedIn(user);
  }

  static isAdmin(user: User): boolean {
    return User.isLoggedIn(user) && user.admin ? true : false;
  }
}

export const USER_NOT_AUTHENTICATED = new User();
