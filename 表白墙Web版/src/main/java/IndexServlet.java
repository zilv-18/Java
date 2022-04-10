import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/9
 * Time: 10:45
 */

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 根据当前用户请求中的 sessionId 获取到用户信息,并显示到页面上
        resp.setContentType("text/html; charset=utf-8");
        // 1.判定当前用户是否已经登录了(判断请求中有没有 sessionId ,以及 sessionId 是否合法)
        // 如果会话不存在,就不能创建了,只是查询,不是登录
        HttpSession session = req.getSession(false);
        if (session == null) {
            // 当前没有找到合法会话,当前用户尚未登陆,重定向到 login.html 让用户进行登录
            resp.sendRedirect("login.html");
            return;
        }

        // 2. 用户已经登录 , 就可以从 HttpSession 中拿到用户信息了
        String userName = (String)session.getAttribute("username");
        Integer loginCount = (Integer) session.getAttribute("loginCount");
        loginCount++;
        session.setAttribute("loginCount",loginCount);
        //  3. 返回一个 html 页面
        StringBuilder html = new StringBuilder();
        html.append("<div>用户: " + userName + "</div>");
        html.append("<div>访问次数: " + loginCount + "</div>");
        resp.getWriter().write(html.toString());
    }
}
