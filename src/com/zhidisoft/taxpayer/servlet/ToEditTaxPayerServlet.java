package com.zhidisoft.taxpayer.servlet;

import com.zhidisoft.manage.dao.IndustryDao;
import com.zhidisoft.manage.dao.TaxOrganDao;
import com.zhidisoft.manage.dao.TaxPayerDao;
import com.zhidisoft.manage.entity.TaxPayer;
import com.zhidisoft.system.dao.TaxerDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/toEditTaxPayerServlet.do")
public class ToEditTaxPayerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        TaxPayer payer = TaxPayerDao.getPayer(id);
        TaxerDao taxerDao = new TaxerDao();
        req.setAttribute("payer", payer);

        req.setAttribute("industry", IndustryDao.getIndustry(payer.getIndustryId()));
        req.setAttribute("organ", TaxOrganDao.getOrgan(payer.getTaxOrganId()));
        req.setAttribute("user",taxerDao.getTaxerByUserId(payer.getUserId()));
        req.setAttribute("organs",TaxOrganDao.getOrgans());
        req.setAttribute("industrys",IndustryDao.getIndustrys());

        req.getRequestDispatcher("/manage/jsp/editTaxPayer.jsp").forward(req,resp);

    }
}
