import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { dataModel } from '../Models/dataModel';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class TaskServiceService {
  private resourceUrl = "http://localhost:8081/api/attachImageToPDF";
  constructor(
    private http: HttpClient
  ) { }
  

  attachImageToPDF(data: dataModel): Observable<string> {

    return this.http.post(this.resourceUrl+'/'+data.x+'/'+data.y, data.img,{responseType: 'text'}).pipe(map((res:any) => {
        return res;
    }));
}
  
}
