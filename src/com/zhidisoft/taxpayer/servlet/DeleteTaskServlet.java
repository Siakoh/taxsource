package com.zhidisoft.taxpayer.servlet;

import com.zhidisoft.manage.dao.TaxSourceDao;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/deleteTaskServlet.do")
public class DeleteTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应字符编码，类型，创建json对象
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();
        // 获取参数
        String id = req.getParameter("id");
        // 执行删除操作，输出结果，缓冲，关闭
        boolean b = TaxSourceDao.deleteTask(id);
        if (b) {
            json.put("success", true);
            json.put("msg", "删除成功");
        } else {
            json.put("success", false);
            json.put("msg", "删除失败");
        }
        out.print(json);
        out.flush();
        out.close();
    }
}
