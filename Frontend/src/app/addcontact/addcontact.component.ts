import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { Router } from "@angular/router";
import { AddressbookService } from "../addressbook.service";
import { ContactInformation } from "../contact-information";
import Swal from "sweetalert2";

@Component({
  selector: "app-addcontact",
  templateUrl: "./addcontact.component.html",
  styleUrls: ["./addcontact.component.css"],
})
export class AddcontactComponent implements OnInit {
  message: string;
  error: string;
  userId: any;
  contact: ContactInformation = new ContactInformation(); //added
  constructor(private service: AddressbookService, private router: Router) {}
  ngOnInit(): void {
    this.userId = sessionStorage.getItem("userId");
  }
  postUser(form: NgForm) {
    console.log(form.value.userId);
    form.controls["userId"].setValue(this.userId);
    this.service.postData(form.value).subscribe(
      (data) => {
        console.log(data);
        // if (!data.error) {
        this.message = "Contact added successfully";
        Swal.fire(this.message);
        //   form.reset();
        this.cancel();
        // }
      },
      (error) => {
        this.message="can't add user with same mobile number";
        Swal.fire(this.message);
        //console.log(error);
        //Swal.fire(error.error.message);
      }
    );
  }
  cancel(){
    this.router.navigateByUrl("/viewContacts");
  }
}
