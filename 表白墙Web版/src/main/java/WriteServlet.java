import javax.servlet.ServletContext;
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
 * Date: 2022/3/28
 * Time: 15:30
 */
// 负责往 ServletContext 里面写数据
// 浏览器通过一个形如 /writer?message=aaa 访问到 WriteServlet,就把 这个键值对存到 ServletContext 里面
@WebServlet("/writer")
public class WriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1.先从请求中获取到  message
        String message = req.getParameter("message");
        // 2.取出 ServletContext 对象(这个对象是 tomcat 在加载 webapp 时候自动创建的)
        ServletContext context = this.getServletContext();
        // 3.往这里写入键值对
        context.setAttribute("message",message);
        // 4.返回响应
        resp.getWriter().write("<h3> 存储 message 成功 </h3>");
    }
}
