<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "厂商";
    if (mapper != null) {
        dpName = request.getParameter("dpName");
        String path = SiteConfig.getInstance().getWarLoc() + mapper;
        out = new ConstPageWriter(path, response.getWriter());
    }
%>
<html>
<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="<%=dpName%>"/>
    </jsp:include>
    <script src="/js/lweb-tool-format.js"></script>
    <script>
        $(document).ready(function () {
            loadData();
        });

        function loadData() {
            $("#itemTypeList").empty();
            $("#itemTypeList").append("<option selected='selected' disabled='disabled' value='selected'></option>");

            $("#manuInfo").empty();

            $("#manuTypeList").empty();
            $("#manuTypeList").append("<tr><td>ID</td><td>厂商</td><td>添加时间</td></tr>");
            $.post("/api/manu.json?action=getAll", function (data) {
                if (data.result) {
                    for (let i = 0; i < data.data.length; i++) {
                        const manu = data.data[i];
                        $("<tr><td>" + manu.id + "</td><td><a onclick='loadManuInfo(\"" + manu.name + "\")'>" + manu.name + "</a></td><td>" + formatUnixTime(manu.insDate) + "</td></tr>").appendTo("#manuTypeList");
                    }
                } else {
                    alert(data.msg);
                }
            });

            $.post("/api/itemType.json?action=getAll", function (data) {
                if (data.result) {
                    for (let i = 0; i < data.data.length; i++) {
                        const itemType = data.data[i];
                        $("#itemTypeList").append("<option value='" + itemType.id + "'>" + itemType.name + "</option>");
                    }
                } else {
                    alert(data.msg);
                }
            });
        }

        function loadManuInfo(name) {
            $.post("/api/manu.json?action=getManuInfo", {
                name: name
            }, function (data) {
                if (data.result) {
                    $("#manuInfo").empty();
                    $("#manuInfo").append("<caption>" + name + "</caption>");
                    $("#manuInfo").append("<tr><td>类型</td><td>添加时间</td><td>操作</td></tr>");
                    for (let i = 0; i < data.data.length; i++) {
                        const manu = data.data[i];
                        $("#manuInfo").append("<tr><td>" + manu.itemType + "</td><td>" + formatUnixTime(manu.insDate) + "</td><td><button onclick='delManuType(" + manu.id + ")'>删除</button></td></tr>");
                    }
                } else {
                    alert(data.msg);
                }
            });
        }

        function delManuType(id) {
            $.post("/api/manu.json?action=delManu", {
                id: id
            }, function (data) {
                if (data.result) {
                    loadData();
                } else {
                    alert(data.msg);
                }
            });
        }

        function postManuType() {
            const manuName = $(":text[name=manuName]").val();
            const itemType = $("#itemTypeList").val();
            if (itemType == null) {
                alert("请选择制造的物品类型");
                return false;
            }
            if (manuName.length > 0) {
                $.post("/api/manu.json?action=addManu", {
                        name: manuName,
                        itemTypeId: itemType
                    }, function (data) {
                        if (data.result) {
                            loadData();
                            alert("添加成功");
                        } else {
                            alert(data.msg);
                        }
                    }
                )
            } else {
                alert("请认真填写");
            }
            return false;
        }
    </script>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div>
    <form method="post" onsubmit="return postManuType()">
        <label>厂商:<input type="text" name="manuName" placeholder="ASUS...技嘉..." required/></label>
        <label>制造:<select id="itemTypeList">
        </select></label>
        <button>添加</button>
    </form>
    <table id="manuTypeList" border="1">
    </table>

    <table id="manuInfo" border="1">
    </table>
</div>
</body>
</html>
<%
    out.close();
%>
