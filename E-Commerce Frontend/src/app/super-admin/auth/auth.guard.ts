import { inject } from '@angular/core';
import { CanActivateFn, Router} from '@angular/router';

export const superAdminAuthGuard: CanActivateFn = (route, state) => {
  const _router = inject(Router);
  const token = sessionStorage.getItem('token');
  const isLoggedIn = "true";
  const url = state.url;

  // For Redirecting Page to Dashboard if User if Loggedin
  if(url === '/superAdmin/login'){
    if(token === null){
      return true;
    }else{
      _router.navigate(['/superAdmin/dashboard']);
      return false;
    }
  }

  if(url === '/superAdmin/dashboard'){
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

