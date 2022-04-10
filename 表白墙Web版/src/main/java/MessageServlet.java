import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 人生如戏
 * Date: 2022/3/8
 * Time: 10:44
 */

class Message {
    public String from;
    public String to;
    public String message;
}

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 当前服务器是把数据给保存到了 messageList 变量中,变量就是内存
     * 此时此刻,一旦程序重启(服务器重启),内存中的东西就没了
     * 当前代码虽然能做到,页面刷新之后数据还存在,但是服务器重启之后,数据就没了
     * 如何让服务器这边做到重启之后数据也不丢失?
     * 答: 持久化存储 -> 写硬盘 , 就需要把当前的数据给写到硬盘里面去,在下次服务器启动的时候
     * 先把硬盘中的数据进行加载
     * <p>
     * 如何写硬盘?
     * 1.直接写到文件中
     * <p>
     * 2.保存到数据库中
     */
    // 要保存到文件中,这个变量就不需要了
//    private List<Message> messageList = new ArrayList<Message>();

    private String filePath = "d:/message.txt"; // 保存文件的路径


    // 这个方法用来处理从服务器获到的消息数据
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        List<Message> messageList = load();
        objectMapper.writeValue(resp.getWriter(), messageList);
    }

    private List<Message> load() {
        // 这个方法负责读文件,把读到的数据获取到之后,放到 List<Message> 里
        List<Message> messageList = new ArrayList<>();
        System.out.println("从文件加载!");
        // 由于 Windows 系统默认编码是 GBK 所以我们要通过 GBK 编码的方式取出字符串
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                // 如果读取到 line 的内容,就把 line 解析成一个 Message 对象
                String[] tokens = line.split("\t");
                Message message = new Message();
                message.from = tokens[0];
                message.to = tokens[1];
                message.message = tokens[2];
                messageList.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageList;
    }

    // 这个方法用来处理从客户端提交数据给服务器
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message message = objectMapper.readValue(req.getInputStream(), Message.class);
//        messageList.add(message);

        // 在这里进行写文件的操作
        save(message);

        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write("{\"OK\": 1}");
    }

    private void save(Message message) {
        System.out.println("向文件写入数据!");
        // FileWrite 的使用方法,就和 PrintWrite 差不多,里面都是有一个关键的方法叫做 write
        // 参数为 true 表示 追加 , 很多语言中(包括 java),打开文件的时候,主要都有三种方式
        /**
         * 1. 读方式打开(使用输入流对象的时候)
         * 2. 写方式打开(使用输出流对象的时候) 直接写方式打开,会清空掉文件原有的内容
         * 3. 追加写方式打开(使用 输出流 对象的时候)  不会情况文件原有内容,而是直接在最后面拼接
         */
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            // 写入文件的方式有很多,可以直接写 JSON,也可以使用行文本(每个记录占一行,字段之间使用分隔符区分)
            fileWriter.write(message.from + "\t" + message.to + "\t" + message.message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
