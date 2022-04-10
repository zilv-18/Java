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

// 负责读 ServletContext 里面的数据
// 就从刚才的 WriteServlet 存的数据给取出来
@WebServlet("/reader")
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        // 1.获取到同一个 ServletContext 对象(同一个 webapp 里面)
        ServletContext context = this.getServletContext();
        // 2.从 Context 中获取到刚才存的值
        String message = (String) context.getAttribute("message");
        // 3.把取出的数据显示出来
        resp.getWriter().write("message : " + message);
    }
}
