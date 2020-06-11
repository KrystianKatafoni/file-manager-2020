import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {FileManagerService} from "./file-manager.service";
import {FileInfo} from "../../shared/file-info";

export class FileDataSource implements DataSource<FileInfo> {

    private filesSubject = new BehaviorSubject<FileInfo[]>([]);
    public totalPages: number;
    public totalElements: number;
    /**
     * Constructor
     *
     * @param {DocumentManagerService} documentManagerService
     */
    constructor(
        private fileManagerService: FileManagerService
    )
    {

    }
    loadFiles(sort: string, pageNumber: number, pageSize: number, filteredName: string): void {
        this.fileManagerService.getFilesInfo(sort, pageNumber, pageSize, filteredName)
            .subscribe(documentPage => {
                console.log(documentPage);
                this.totalPages = documentPage['totalPages'];
                this.totalElements = documentPage['totalElements'];
                this.filesSubject.next(documentPage['content']);
            });
    }

    /**
     * Connect function called by the table to retrieve one stream containing the data to render.
     *
     * @returns {Observable<any[]>}
     */
    connect(): Observable<FileInfo[]>
    {
        return this.filesSubject.asObservable();
    }

    /**
     * Disconnect
     */
    disconnect(): void
    {
        this.filesSubject.complete();
    }
}
