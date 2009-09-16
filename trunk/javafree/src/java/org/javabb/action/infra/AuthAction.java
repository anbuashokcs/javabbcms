package org.javabb.action.infra;

import org.javabb.infra.UserContext;

@SuppressWarnings("serial")
public class AuthAction extends ActionSuper {

    public boolean userIsAdmin(){
	//if(getUserLogged() != null && getUserLogged().getAdmin() == )
	return true;
    }
    
    public boolean hasModeratorPermission() {
	if(UserContext.getContext().isAuthenticated()){
	    Integer adm = UserContext.getContext().getUser().getAdmin();
	    if(adm.intValue() == 1 || adm.intValue() == 2){
		return true;
	    }
	}
	return false; 
    }
    
    
}
