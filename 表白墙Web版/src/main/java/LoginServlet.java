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
 * Time: 10:30
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1. 先从请求的 body 中读取 用户名 和 密码
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        // 2.判定一下用户名密码是否正确(此处固定用户密码 : admin 123456)
        if (!"admin".equals(userName) || !"123456".equals(password)) {
                // 登录失败
            resp.getWriter().write("登录失败!");
            return;
        }
        System.out.println("登录成功!");
        // 3.登录成功创建出一个会话
        // 会话是根据请求中 sessionId 来查的,sessionId 是 在 cookie 中的
        // 但是此处是首次登录,此时请求中是没有 cookie (cookie 是服务器返回的)
        // 此处就会触发 "找不到就创建" 这样的流程
        // 同时这里进行的操作 : 先创建出 一个 HttpSession 对象 (作为value)
        // 再生成一个随机的字符串,作为 sessionId(作为key)
        // 把 key 和 value 插入到哈希表中
        // 同时把这个生成的 sessionId 通过 Set-Cookie 字段返回给浏览器
        HttpSession session = req.getSession(true);
        // 还可以存入 程序猿自定义的数据,可以存入身份信息(用户名和登录次数)
        session.setAttribute("username", "admin");
        session.setAttribute("loginCount", 0);
        // 4.让页面跳转到主页,使用重定向的方式实现即可
        resp.sendRedirect("index");



    }
}
