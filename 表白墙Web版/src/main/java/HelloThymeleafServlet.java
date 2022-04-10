import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

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
 * Time: 9:40
 */

@WebServlet("/helloThymeleaf")
public class HelloThymeleafServlet extends HttpServlet {

    private TemplateEngine engine = new TemplateEngine();

    @Override
    public void init() throws ServletException {
        // 完成 Thymeleaf 的初始化操作
        // 创建一个 模板解析器 对象
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(this.getServletContext());
        // 让模板解析器,来加载模板文件 , 这里的前缀就表示模板文件所在的目录,正因为如此,模板文件必须要放到 WEB-INF 目录中
        // 设定这些前缀后缀,就是告诉模板引擎,要加载哪些文件到内存中,以备后用
        resolver.setPrefix("/WEB-INF/template/"); // 设置前缀
        resolver.setSuffix(".html"); // 设置后缀
        resolver.setCharacterEncoding("utf-8");
        // 把解析器对象,给设置到 engine 对象中
        engine.setTemplateResolver(resolver);
        // 当前 模板引擎是一个对象,解析器是一个对象,这样分开的目的是因为,模板引擎可以同时安排上多个不同类型的解析器
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 0.在执行模板渲染(把刚才写的模板 html 代码里面的 message 变量进行替换之前要先初始化
        //   初始化操作只需要执行一次即可,所以可以放在 init 方法里面
        // 1. 先从参数中读取出用户要穿过来的 message 的值,(从 query string 里读取)
        String message = req.getParameter("message");
        // 2. 把当前从请求中读取出来的 message 的值和 模板 中的 ${message} 关联起来
        WebContext context = new WebContext(req,resp,this.getServletContext());
        context.setVariable("message",message);
        resp.setContentType("text/html; charset=utf-8");
        // 3.进行最终的渲染
        String html = engine.process("hello", context); // 对 hello 这个文件进行渲染
        System.out.println(html);
        resp.getWriter().write(html);
    }
}
