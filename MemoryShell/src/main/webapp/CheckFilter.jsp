<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
%>
<%!
    // 反射工具方法：获取私有字段
    private Object getField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    // 检测可疑类名的关键词（可根据实际情况扩展）
    private boolean isSuspicious(String className) {
        String[] keywords = {"shell", "memshell", "evil", "filter", "inject"};
        for (String keyword : keywords) {
            if (className.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }
%>

<%
    String result = "";
    try {
        // 1. 获取StandardContext实例
        ServletContext servletContext = request.getServletContext();
        Object applicationContext = getField(servletContext, "context");
        Object standardContext = getField(applicationContext, "context");

        // 2. 获取所有Filter定义（返回的是FilterDef[]数组）
        Method findFilterDefsMethod = standardContext.getClass().getMethod("findFilterDefs");
        Object[] filterDefs = (Object[]) findFilterDefsMethod.invoke(standardContext);

        // 3. 获取Filter映射关系（返回的是FilterMap[]数组）
        Method findFilterMapsMethod = standardContext.getClass().getMethod("findFilterMaps");
        Object[] filterMaps = (Object[]) findFilterMapsMethod.invoke(standardContext);

        // 构建结果HTML
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>检测到 ").append(filterDefs.length).append(" 个Filter</h3>");
        sb.append("<table border='1' style='border-collapse: collapse;'>");
        sb.append("<tr><th>Filter名称</th><th>类名</th><th>映射路径</th><th>风险状态</th></tr>");

        // 遍历FilterDef数组
        for (Object filterDef : filterDefs) {
            // 通过反射获取Filter名称和类名
            Method getFilterNameMethod = filterDef.getClass().getMethod("getFilterName");
            String filterName = (String) getFilterNameMethod.invoke(filterDef);

            Method getFilterClassMethod = filterDef.getClass().getMethod("getFilterClass");
            String className = (String) getFilterClassMethod.invoke(filterDef);

            String urlPatterns = "";

            // 匹配FilterMap中的URL路径
            for (Object filterMap : filterMaps) {
                Method getFilterNameFromMap = filterMap.getClass().getMethod("getFilterName");
                String mapFilterName = (String) getFilterNameFromMap.invoke(filterMap);
                if (mapFilterName.equals(filterName)) {
                    Method getURLPatternsMethod = filterMap.getClass().getMethod("getURLPatterns");
                    String[] urls = (String[]) getURLPatternsMethod.invoke(filterMap);
                    urlPatterns = String.join(" ", urls);
                }
            }

            // 判断是否可疑
            boolean suspicious = isSuspicious(className) || urlPatterns.contains("/*");
            String color = suspicious ? "red" : "green";
            String status = suspicious ? "⚠️ 可疑" : "✅ 正常";

            sb.append("<tr>")
                    .append("<td>").append(filterName).append("</td>")
                    .append("<td style='color:").append(color).append("'>").append(className).append("</td>")
                    .append("<td>").append(urlPatterns).append("</td>")
                    .append("<td>").append(status).append("</td>")
                    .append("</tr>");
        }

        sb.append("</table>");
        result = sb.toString();

    } catch (Exception e) {
        result = "❌ 检测失败，原因：" + e.toString().replace("<", "&lt;").replace(">", "&gt;");
    }
%>

<html>
<head>
    <title>Filter内存马检测工具</title>
    <style>
        table { margin: 20px; }
        th, td { padding: 8px; text-align: left; }
    </style>
</head>
<body>
<h2>Filter内存马检测报告（Tomcat 9+兼容版）</h2>
<%= result %>
<hr>
<p>检测逻辑说明：</p>
<ul>
    <li>红色标注类名包含关键词：shell/memshell/evil/filter/inject</li>
    <li>映射路径为 /* 的Filter会被标记为可疑</li>
    <li>支持Tomcat 7/8/9/10版本</li>
</ul>
</body>
</html>