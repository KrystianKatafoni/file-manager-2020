export class FileInfo {
  id: number;
  name: string;
  size: string;
  extension: string;
  creationDate: Date;
  createdBy: string;

  constructor(id: number, name: string, size: string, creationDate: Date, lastModifiedDate: Date, createdBy: string,
              extension: string) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.extension = extension;
    this.creationDate = creationDate;
    this.createdBy = createdBy;
  }
}
