package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.CustomerModel;
import it.univr.musiclovers.model.EmployerModel;
import it.univr.musiclovers.model.beans.AccountBean;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author blasco991
 */
@ManagedBean
@SessionScoped
public class AuthenticationController extends ControllerModel implements Serializable {

    private AccountBean accountBean = new AccountBean();
    private boolean logged = false;
    private static final long serialVersionUID = 1L;

    public void check() {
        if (!logged) {
            redirect("homepage");
        }
    }

    public String getPassword() {
        return accountBean.getPassword();
    }

    public void setPassword(String password) {
        accountBean.setPassword(password);
    }

    public String getUsername() {
        return accountBean.getUsername();
    }

    public void setUsername(String username) {
        accountBean.setUsername(username);
    }

    public boolean isLogged() {
        return logged;
    }

    public String loginEmployer() {
        AccountBean account = null;
        try {
            account = EmployerModel.getAccount(accountBean.getUsername(), accountBean.getPassword());
        } catch (SQLException ex) {
            Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (account instanceof AccountBean) {
            logged = true;
            return retString("admin/index");
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return retString("index");
        }
    }

    public String loginProfessional() throws SQLException {
        accountBean = CustomerModel.getAccount(accountBean.getUsername(), accountBean.getPassword());
        if (accountBean instanceof AccountBean) {
            logged = true;
            return retString("admin/index");
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return retString("index");
        }
    }

    public void logout() {
        accountBean = new AccountBean();
        logged = false;
        //return retString("homepage.xhtml");
    }

}
