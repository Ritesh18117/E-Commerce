import { inject } from '@angular/core';
import { CanActivateFn, Router} from '@angular/router';

export const adminAuthGuard: CanActivateFn = (route, state) => {
  const _router = inject(Router);
  const token = sessionStorage.getItem('token');
  const isLoggedIn = "true";
  const url = state.url;

  if(url === '/admin/dashboard'){
    if(token === null){
      // console.log("I have token");
      _router.navigate(['/']);
      return false;
    }else{
      return true;
    }
  }

  // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admins/login'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/admins/dashboard']);
      return false;
    }
  }

    // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admins/signup'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/admins/dashboard']);
      return false;
    }
  }

    // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admins/changepassword'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/admins/dashboard']);
      return false;
    }
  }

  // For Login User
  if(isLoggedIn === "true"){
    return true;
  }else{
    _router.navigate(['/login']);
    return false;
  }
};

