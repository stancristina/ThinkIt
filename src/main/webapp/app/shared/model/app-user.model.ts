export interface IAppUser {
  id?: number;
  xp?: number;
  userId?: number;
}

export class AppUser implements IAppUser {
  constructor(public id?: number, public xp?: number, public userId?: number) {}
}
