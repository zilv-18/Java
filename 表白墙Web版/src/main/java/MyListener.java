import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/28
 * Time: 16:30
 */


@WebListener
public class MyListener implements ServletContextListener {
    // ServletContext 初始化完毕后会执行这个
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext 初始化!");
        // 获取一下 ServletContext 对象,通过方法参数获取到的
        ServletContext context = sce.getServletContext();
        context.setAttribute("message","初始化的消息");
    }

    // ServletContext 销毁之前,会执行这个方法
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
