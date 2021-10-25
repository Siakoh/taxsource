package com.zhidisoft.taxpayer.servlet;

import com.zhidisoft.manage.dao.TaxSourceDao;
import com.zhidisoft.manage.entity.TaxSource;
import com.zhidisoft.system.entity.User;
import com.zhidisoft.util.FormatDate;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@WebServlet(urlPatterns = "/editTaskServlet.do")
public class EditTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取响应对象
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        JSONObject json = new JSONObject();
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        // 获取参数，建立对象
        TaxSource task = new TaxSource();
        Integer id = Integer.valueOf(req.getParameter("id"));  //  NumberFormatException: null
        task.setId(id);
        String payerId = req.getParameter("payerId");
        if (payerId == null || payerId.length() == 0) {
            task.setPayerId(null);
        } else {
            task.setPayerId(Integer.parseInt(payerId));
        }
        String taskName = req.getParameter("taskName");
        task.setTaskName(taskName);
        String subOrganId = req.getParameter("subOrganId");
        if ("-1".equals(subOrganId)) {
            task.setSubOrganId(null);
        } else {
            task.setSubOrganId(Integer.parseInt(subOrganId));
        }
        String approverId = req.getParameter("approverId");
        if ("-1".equals(approverId)) {
            task.setApproverId(null);
        } else {
            task.setApproverId(Integer.parseInt(approverId));
        }
        String executeId = req.getParameter("executeId");
        if ("-1".equals(executeId)) {
            task.setExecuteId(null);
        } else {
            task.setExecuteId(Integer.parseInt(executeId));
        }
        String executeTime = req.getParameter("executeTime");
        task.setExecuteTime(executeTime);
        String taskState = req.getParameter("taskState");
        task.setTaskState(taskState);
        String idea = req.getParameter("idea");
        task.setIdea(idea);
        String riskLevel = req.getParameter("riskLevel");
        task.setRiskLevel(Integer.parseInt(riskLevel));
        User user = (User) session.getAttribute("user");
        Integer recordUserId = user.getId();
        task.setRecordUserId(recordUserId);
        Date date = new Date();
        task.setRecordTaskDate(FormatDate.format(date));
        boolean b = TaxSourceDao.edit(task, id);
        if (b) {
            json.put("success", true);
            json.put("msg", "修改任务成功");
        } else {
            json.put("success", false);
            json.put("msg", "修改失败");
        }
        out.print(json);
        out.flush();
        out.close();
    }
}
