import { inject } from '@angular/core';
import { CanActivateFn, Router} from '@angular/router';

export const adminAuthGuard: CanActivateFn = (route, state) => {
  const _router = inject(Router);
  const token = sessionStorage.getItem('token');
  const isLoggedIn = "true";
  const url = state.url;

  // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admin/login'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/dashboard']);
      return false;
    }
  }

    // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admin/signup'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/dashboard']);
      return false;
    }
  }

    // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/admin/changepassword'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/dashboard']);
      return false;
    }
  }

  if(url === '/admin/dashboard'){
    if(token === null){
      _router.navigate(['/']);
      return false;
    }else{
      return true;
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

