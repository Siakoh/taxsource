package com.zhidisoft.system.servlet;

import com.zhidisoft.system.dao.UserDao;
import com.zhidisoft.util.EncryptUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/updatePassword.do")
public class UpdatePassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取ajax中的数据
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String username = req.getParameter("username");
        // 设置字符编码，数据类型，获取json，输出对象
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();
        // 更新密码
        UserDao userDao = new UserDao();
        boolean b = userDao.update(username, oldPassword, newPassword);
        if (b) {
            json.put("success", true);
            json.put("msg", "修改成功");
        } else {
            json.put("success", false);
            json.put("msg", "修改失败，密码输入错误");
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
