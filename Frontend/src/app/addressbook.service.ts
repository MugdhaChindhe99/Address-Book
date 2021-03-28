import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Addressbookconstants } from "./addressbookconstants";
import { ContactInformation } from "./contact-information";
import { Login } from "./login";
import { User } from "./user";

@Injectable({
  providedIn: "root",
})
export class AddressbookService {
  user: User = new User();
  contact: ContactInformation = new ContactInformation();
  constructor(private http: HttpClient) {}

  viewAllContacts(): Observable<any> {
    return this.http.get(Addressbookconstants.USER_CONTACTS_URL);
  }
  getUsers(): Observable<any> {
    return this.http.get(Addressbookconstants.ALL_USERS_URL);
  }
  getContact(contactId: any): Observable<ContactInformation> {
    return this.http.get<ContactInformation>(
      Addressbookconstants.GET_CONTACT_BY_ID_URL + "/" + contactId
    );
  }
  getUserContacts(userId: any): Observable<any> {
    return this.http.get(Addressbookconstants.USER_CONTACTS_URL + "/" + userId);
  }
  addFavorite(contactId: number): Observable<ContactInformation[]> {
    return this.http.get<ContactInformation[]>(
      Addressbookconstants.ADD_FAVORITE_URL + "/" + contactId
    );
  }
  getFavorities(userId: number): Observable<ContactInformation[]> {
    return this.http.get<ContactInformation[]>(Addressbookconstants.VIEW_FAVORITE_URL + "/" + userId);
  }
  deleteContact(contactId: number): Observable<any> {
    return this.http.delete(
      Addressbookconstants.DELETE_CONTACT_URL + "/" + contactId
    );
  }
  searchContact(str: string, userId: any) : Observable<ContactInformation[]> {
    return this.http.get<ContactInformation[]>(
      Addressbookconstants.SEARCH_URL + "/" + str.trim() + "/" + userId
    );
  }
  signUp(login: Login) {
    return this.http.post(Addressbookconstants.ADD_USER_URL, login);
  }
  login(tmpUsername: string, tmpPassword: string): Observable<any> {
    console.log(
      "In service of find by username " +
        tmpUsername +
        " and password " +
        tmpPassword
    );
    return this.http.get(
      Addressbookconstants.LOGIN_URL + "/" + tmpUsername + "/" + tmpPassword
    );
  }
  postData(contact: any) {
    return this.http.post<any>(`http://addressbook11.us-east-1.elasticbeanstalk.com/addContact`, contact);
  }
  putData(contact: any) {
    return this.http.put<any>(`http://addressbook11.us-east-1.elasticbeanstalk.com/update`, contact);
  }
  // Export By Id
  exportbyId(userId:any):any{
    return this.http.get('http://addressbook11.us-east-1.elasticbeanstalk.com/Download/' + userId, {responseType: 'blob'});
  }
  public upload(formData: FormData){
    return this.http.post<any>(`http://addressbook11.us-east-1.elasticbeanstalk.com/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }
}
