package com.zhb;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.zhb.lts.util.LtsTaskTrackerPlugin;

/**
 * jfinal配置
 */
public class ApplicationConfig extends JFinalConfig {

	//读取系统文件配置
	public static Prop sysProp;
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		sysProp = PropKit.use("config.properties");
		
		//是否开发模式，开发模式会打印进入controller等详细信息
        me.setDevMode(sysProp.getBoolean("project.devMode", false));
        //JSP视图渲染
        me.setViewType(ViewType.JSP);
        //默认为-，修改为&,url参数
        me.setUrlParaSeparator("&");
        //文件上传限制
        me.setMaxPostSize(10*Const.DEFAULT_MAX_POST_SIZE);
	}

	@Override
	public void configEngine(Engine arg0) {
	}

	@Override
	public void configHandler(Handlers arg0) {
	}

	@Override
	public void configInterceptor(Interceptors arg0) {
	}

	@Override
	public void configPlugin(Plugins me) {
		//alibaba druid 数据源
		DruidPlugin druidPlugin = new DruidPlugin(sysProp.get("jdbc.url"), sysProp.get("jdbc.username"),sysProp.get("jdbc.password").trim());
		me.add(druidPlugin);
		//jfinal-ext 自动注册model
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin);
		atbp.autoScan(false).addScanPackages("com.zhb.model").setShowSql(sysProp.getBoolean("project.devMode", false));
		me.add(atbp);
		//lts taskTracker
		LtsTaskTrackerPlugin ltsTaskTrackerPlugin = new LtsTaskTrackerPlugin();
		me.add(ltsTaskTrackerPlugin);
	}

	@Override
	public void configRoute(Routes me) {
		//自动绑定路由信息
		AutoBindRoutes autoBindRoutes = new AutoBindRoutes();
		me.add(autoBindRoutes);
	}
	
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}
	
	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
	}
	
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/");
	}

}
