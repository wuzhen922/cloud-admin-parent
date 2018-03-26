<#include "/layout/mainLayout.ftl">
<#macro html_title>主页</#macro>
<#macro html_body>

<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="${ctx}/resources/theme/images/profile_small.jpg" /></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                              <span class="clear">
                              <span class="block m-t-xs"><strong class="font-bold">${userName}</strong></span>
                              <span class="text-muted text-xs block">${roleName}<b class="caret"></b></span>
                              </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="javascript:;" onclick="modifyPassword()">修改密码</a>
                            </li>
                            <li><a href="${ctx}/logout">安全退出</a>
                            </li>
                        </ul>
                    </div>
                </li>

                <#--菜单开始-->
                    <li>
                            <a class="J_menuItem" href="${ctx}/picture/main">
                                <span class="nav-label">上传现场图片</span>
                            </a>
                    </li>
                <#--菜单结束-->
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row content-tabs">
            <button class="roll-nav roll-left navbar-minimalize"><i class="fa fa-bars"></i></button>
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/index">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/index"
                    frameborder="0" data-id="${ctx}/index" seamless></iframe>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--修改密码弹出层 -->
    <div id="modifyPasswordLayer" style="display:none;margin-top: 20px;" class="col-xs-12">
        <form class="form-horizontal" role="form" id="modifyPasswordForm">
            <div class="form-group">
                <label for="oldPassword" class="control-label col-xs-4">旧密码</label>
                <div class="zf-layer-input col-xs-8">
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="请输入旧密码">
                </div>
            </div>
            <div class="form-group">
                <label for="newPassword" class="control-label col-xs-4">新密码</label>
                <div class="zf-layer-input col-xs-8">
                    <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="请输入新密码">
                </div>
            </div>
            <div class="form-group">
                <label for="repeatPassword" class="control-label col-xs-4">确认密码</label>
                <div class="zf-layer-input col-xs-8">
                    <input type="password" class="form-control" id="repeatPassword" name="repeatPassword" placeholder="请输入确认密码">
                </div>
            </div>
        </form>
    </div>
</div>
</#macro>
<#macro html_foot>
    <@p.js src="common/contabs.js"/>
    <@p.js src="common/main.js"/>
    <@p.js src="authority/admin.js"/>
    <@p.js src="third/md5.js"/>
</#macro>