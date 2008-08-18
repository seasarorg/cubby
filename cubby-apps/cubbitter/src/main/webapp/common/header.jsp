<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.seasar.org/cubby/tags" prefix="t" %>
<%@taglib uri="http://www.seasar.org/cubby/functions" prefix="f" %>
<%@page import="org.seasar.framework.container.SingletonS2Container"%>
<%!
public static <T> T getComponent(final Class<T> componentClass) {
    return (T) SingletonS2Container.getComponent(componentClass);
}
%>
