import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {FileManagerService} from "./file-manager.service";
import {File} from "./file";

export class FileDataSource implements DataSource<File> {

    private filesSubject = new BehaviorSubject<File[]>([]);
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
    loadFiles(filter: string, sort: string,
                  pageNumber: number, pageSize: number): void {
        this.fileManagerService.getFiles(filter, sort, pageNumber, pageSize)
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
    connect(): Observable<File[]>
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
