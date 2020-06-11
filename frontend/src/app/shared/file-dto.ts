import {FileInfo} from "./file-info";

export class FileDto {
  fileInfo: FileInfo;
  file;

  constructor(fileInfo: FileInfo, file) {
    this.fileInfo = fileInfo;
    this.file = file;
  }
}
