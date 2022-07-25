package it.unipd.dei.webapp.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestResource {

    protected final HttpServletRequest req;
    protected final HttpServletResponse res;

    public RestResource(HttpServletRequest req, HttpServletResponse res){
        this.req = req;
        this.res = res;
    }

}
