package com.zhidisoft.system.servlet;

import com.google.code.kaptcha.Constants;
import com.zhidisoft.base.BaseServlet;
import com.zhidisoft.system.dao.TaxerDao;
import com.zhidisoft.system.dao.UserDao;
import com.zhidisoft.system.entity.Taxer;
import com.zhidisoft.system.entity.User;
import com.zhidisoft.util.EncryptUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(urlPatterns = "/userLoginServlet")
@WebServlet("/loginServlet")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String remUser = req.getParameter("remUser");

        JSONObject json = new JSONObject();
        PrintWriter out = resp.getWriter();

        String s = EncryptUtil.encryptMD5(password);
        User user = UserDao.selectName(name,s);
        Taxer taxer = TaxerDao.getTaxer(name);
        HttpSession session = req.getSession();
        session.setAttribute("user",user);

        if (user != null){
            if ("on".equals(remUser)){
                Cookie cookie = new Cookie("username",name);
                resp.addCookie(cookie);
                json.put("success",true);

            }
            json.put("success",true);


            if (taxer != null){
                session.setAttribute("taxer",taxer);
            }
        }else{
            json.put("success",false);
            json.put("msg","用户名或者密码错误");
        }
        out.write(json.toString());
        out.flush();
        out.close();

    }
}
