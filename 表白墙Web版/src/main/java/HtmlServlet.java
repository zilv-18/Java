import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/10
 * Time: 8:29
 */

@WebServlet("/html")
public class HtmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求传过来一个 query string 这里有一个参数,叫做 name
        // 返回的页面里,就包含这个 name 的值
        // 用户的请求中的 name 不同.返回的页面就不同
        String name = req.getParameter("name");
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("<h3>" + name + "</h3>");
    }
}
