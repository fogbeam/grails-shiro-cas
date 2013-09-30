package org.apache.shiro.cas;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: menghan
 * Date: 13-9-17
 * Time: 下午6:08
 * To change this template use File | Settings | File Templates.
 */
public class MyCasFilter extends CasFilter {

    private String logoffUrl = "";

    MyCasFilter(){
        super();
    }

    protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, org.apache.shiro.subject.Subject subject, javax.servlet.ServletRequest request, javax.servlet.ServletResponse response) throws java.lang.Exception {

        return super.onLoginSuccess(token,subject,request,response);
    }

    protected boolean onLoginFailure(org.apache.shiro.authc.AuthenticationToken token, org.apache.shiro.authc.AuthenticationException ae, javax.servlet.ServletRequest request, javax.servlet.ServletResponse response) {

        System.out.println("ON login failure....in my cas filter");

        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {

                if(request.getAttribute("ticket") == null){

                    Map<String,String[]> parmas = request.getParameterMap();

                    Set<String> keys = parmas.keySet();

                    Iterator<String> itr= keys.iterator();
                    String paraName = "" ;
                    while (itr.hasNext()){
                        paraName = itr.next();
                        System.out.println(paraName);
                    }

                    //((HttpServletResponse)response).sendRedirect(logoffUrl);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(logoffUrl);
                    dispatcher.forward(request, response);
                }


            } catch (IOException e) {
                System.out.println("Cannot redirect to failure url :"+logoffUrl);
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public void setLogoffUrl(String url){
        logoffUrl = url;
    }

}
