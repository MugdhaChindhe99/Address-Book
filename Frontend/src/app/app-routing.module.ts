import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { AddcontactComponent } from './addcontact/addcontact.component';
import { ContactusComponent } from './contactus/contactus.component';
import { HomeComponent } from './home/home.component';
import { UpdatecontactComponent } from './updatecontact/updatecontact.component';
import { ViewcontactsComponent } from './viewcontacts/viewcontacts.component';

const routes: Routes = [
  { path:'', component:HomeComponent},
  {path:'addContact',component:AddcontactComponent},
 // {path:'register',component:SignUpComponent},
 // {path:'login',component:LoginComponent},
  {path:'home/:userId',component:HomeComponent},
  {path:'viewContacts',component:ViewcontactsComponent},
  {path:'updatecontact/:cid',component:UpdatecontactComponent},
  {path:'contactus', component:ContactusComponent},
  {path:'about',component:AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
