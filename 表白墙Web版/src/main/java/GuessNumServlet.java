import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/10
 * Time: 8:40
 */


/**
 * 1. 约定好前后端交互的方式
 * GET /guessNum
 * 通过这个请求,来获取到一个页面(页面里面就包含猜数字基本的界面),同时在服务器这边初始化(让服务器生成一个 [1-100] 的数字
 * 响应内容就是一个页面
 * POST /guessNum
 * 处理一次猜的过程,页面上有一个输入框,还有一个提交按钮,点击提交按钮,就会把输入的数据提交给服务器
 * 服务器收到这个数据之后,判断一下当前这个数字是不是猜对了,返回一个页面,返回的页面里就包含 猜大了/猜小了/猜对了
 * 2.
 */

@WebServlet("/guessNum")
public class GuessNumServlet extends HttpServlet {

    private TemplateEngine engine = new TemplateEngine();

    private int toGuess; // 表示猜的数字
    private int count; // 表示猜的次数

    @Override
    public void init() throws ServletException {
        // 对模板引擎初始化
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(this.getServletContext());
        resolver.setPrefix("/WEB-INF/template/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("utf-8");
        engine.setTemplateResolver(resolver);
    }

    // 获取到页面的初始化情况,并且初始化,生成一个待猜的数字
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1.生成一个待猜的数字
        Random random = new Random();
        // [0,100) + 1 => [1,100]
        toGuess = random.nextInt(100) + 1;
        // 2.返回一个页面
        // 开启一句新的游戏
        WebContext context = new WebContext(req, resp, this.getServletContext());
        context.setVariable("newGame", true);
        engine.process("guessNum", context, resp.getWriter());
    }

    // 处理一次猜的过程
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        // 1.从请求中,读取出用户提交的数字内容
        int guessNum = Integer.parseInt(req.getParameter("num"));
        String result = "";
        if (guessNum < toGuess) {
            result = "猜低了";
        } else if (guessNum > toGuess) {
            result = "猜高了";
        } else {
            result = "猜对了";
        }

        // 3.自增猜的次数
        count++;
        // 4.构造一个结果页面 , 能够显示当前猜的结果
        WebContext context = new WebContext(req, resp, this.getServletContext());
        context.setVariable("newGame", false);
        context.setVariable("result", "结果: " + result);
        context.setVariable("count", "猜的次数: " + count);

        engine.process("guessNum",context,resp.getWriter());
    }
}
