export class File {
  id: number;
  name: string;
  path: string;
  creationDate: Date;
  createdBy: string;

  constructor(id: number, name: string, path: string, creationDate: Date, lastModifiedDate: Date, createdBy: string) {
    this.id = id;
    this.name = name;
    this.path = path;
    this.creationDate = creationDate;
    this.createdBy = createdBy;
  }



}
