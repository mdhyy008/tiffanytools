package com.dabai.tools.tiffany;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.provider.*;
import android.net.*;
import android.content.res.*;
import java.lang.reflect.*;



public class MainActivity extends Activity 
{
	TextView info;
	String busy;
	TextView br;
	SeekBar po;
	CheckBox roo;
	LinearLayout lin;
	TextView nav;
	
	int pro;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		setTitle("MI5X Tools");
		//初始化
		info = (TextView)findViewById(R.id.mainTextView1);
		br = (TextView)findViewById(R.id.mainTextView2);
		po = (SeekBar)findViewById(R.id.mainSeekBar1);
		roo = (CheckBox)findViewById(R.id.mainCheckBox1);
		lin = (LinearLayout)findViewById(R.id.mainLinearLayout1);
		nav = (TextView)findViewById(R.id.mainTextView3);
		
		
		roo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
                @Override 
                public void onCheckedChanged(CompoundButton buttonView, 
											 boolean isChecked) { 
                    // TODO Auto-generated method stub 
                    if(isChecked){ 
                      po.setMax(2000);
					  handler.removeCallbacks(task);			  
						String cmd[]={"mount -o rw,remount /system",
						"echo 大白最帅",
						"echo 我们的群号是115231828"
						};
  						new shell().execCommand(cmd,true);
						lin.setVisibility(0);
						
					}else{
						po.setMax(255);
						handler.post(task);
						lin.setVisibility(8);
					}
                } 
            }); 
			
		
		po.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				/*
				 * seekbar改变时的事件监听处理
				 * */
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					pro=progress;
				}
				/*
				 * 按住seekbar时的事件监听处理
				 * */
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					}
				/*
				 * 放开seekbar时的时间监听处理
				 * */
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					if(roo.isChecked()){
						
						if(pro>10){
						
						String cmd[]={"chmod 777 /sys/class/leds/lcd-backlight/max_brightness",
							"echo "+pro+" > /sys/class/leds/lcd-backlight/max_brightness",
							"chmod 444 /sys/class/leds/lcd-backlight/max_brightness"
						};
						new shell().execCommand(cmd,true);
						br.setText("屏幕亮度("+pro+")");
						}
						
						
					}
					else{
						saveBrightness(MainActivity.this,pro);
					}
					
					
				}
			});
			
		init();	
    }
	
	public void init(){
		
		try
		{
			Runtime.getRuntime().exec("su");
		}
		catch (IOException e)
		{}

		
		if (checkDeviceHasNavigationBar(this) == true)
		{
			nav.setText("原生导航栏(已开启)");
		}
		else{
			nav.setText("原生导航栏(未开启)");
		}	
		
		
		
		if(Exists("/system/xbin/busybox")){
			busy = "busybox状态:已安装";
		}
		else{
			busy ="busybox状态:未安装";
		}

		info.setText("设备型号:"+Build.MODEL+"\n设备代号:"+Build.PRODUCT+"\nAndroid版本:"+Build.VERSION.RELEASE+"\n"+busy);
		if(!Build.PRODUCT.equals("tiffany")){
			alert("你的设备不是MI 5X\n强行使用本软件可能导致设备Boom");
		}
		
		po.setProgress(getSystemBrightness());
		
		//启动定时器
		handler.post(task);
		
		
	}
	
	public void about(View view){
		alert("来自下一个版本的秘密:\n大家好,我是大白.\n下一版本决定写一写快捷方式还有磁贴,新功能你们在评论区说出来\n你们不评论我也想不出来新功能,或许会停更吧😂");
	}
	
	
	public void on(View view){
		//开启按钮
		String oncmds[] = {"mount -o rw,remount /system","sed -i '/qemu/d' /system/build.prop","echo qemu.hw.mainkeys=0 >> /system/build.prop"};
		new shell().execCommand(oncmds,true);
		nav.setText("原生导航栏(即将开启,重启生效)");

	}
	public void off(View view){
		//关闭按钮
		String offcmds[] = {"mount -o rw,remount /system","sed -i '/qemu/d' /system/build.prop","echo qemu.hw.mainkeys=1 >> /system/build.prop"};
		new shell().execCommand(offcmds,true);
		nav.setText("原生导航栏(即将关闭,重启生效)");

	}
	
	public void font(View view){
		Intent a = new Intent();
		Uri b = Uri.parse("https://www.coolapk.com/feed/5439391");
		a.setAction("android.intent.action.VIEW");  
		a.setData(b);
		startActivity(a);
		toast("跳转酷安下载吧");
	}
	public void Google(View view){
		Intent a = new Intent();
		Uri b = Uri.parse("https://www.coolapk.com/apk/com.xiaochen.GoogleTool");
		a.setAction("android.intent.action.VIEW");  
		a.setData(b);
		startActivity(a);
		toast("跳转酷安下载吧");
	}
	
	
	public void bao(View view){
		String urlQQ = "https://jq.qq.com/?_wv=1027&k=570H4bc"; startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
		toast("刷机包去群里拿,入群1元,不想花钱的入群找群主要。我还你");
	}
	public void one(View view){
		pro=50;
		String cmd[]={"chmod 777 /sys/class/leds/lcd-backlight/max_brightness",
			"echo "+pro+" > /sys/class/leds/lcd-backlight/max_brightness",
			"chmod 444 /sys/class/leds/lcd-backlight/max_brightness"
		};
		new shell().execCommand(cmd,true);
		br.setText("屏幕亮度("+pro+")");
	}
	public void two(View view){
		pro=100;
		String cmd[]={"chmod 777 /sys/class/leds/lcd-backlight/max_brightness",
			"echo "+pro+" > /sys/class/leds/lcd-backlight/max_brightness",
			"chmod 444 /sys/class/leds/lcd-backlight/max_brightness"
		};
		new shell().execCommand(cmd,true);
		br.setText("屏幕亮度("+pro+")");
	}
	public void three(View view){
		pro=200;
		String cmd[]={"chmod 777 /sys/class/leds/lcd-backlight/max_brightness",
			"echo "+pro+" > /sys/class/leds/lcd-backlight/max_brightness",
			"chmod 444 /sys/class/leds/lcd-backlight/max_brightness"
		};
		new shell().execCommand(cmd,true);
		br.setText("屏幕亮度("+pro+")");
	}
	public void four(View view){
		pro=400;
		String cmd[]={"chmod 777 /sys/class/leds/lcd-backlight/max_brightness",
			"echo "+pro+" > /sys/class/leds/lcd-backlight/max_brightness",
			"chmod 444 /sys/class/leds/lcd-backlight/max_brightness"
		};
		new shell().execCommand(cmd,true);
		br.setText("屏幕亮度("+pro+")");
	}
	
	public void adb(View view){
		String cmd[]={"setprop service.adb.tcp.port 5555;stop adbd;sleep 1;start adbd;"};
		new shell().execCommand(cmd,true);
		alert("命令成功\n提示:开启网络adb前，请先连接wifi。开启后通过同一局域网下的电脑，使用adb connect 192.168.1.101:5555 命令连接");
	}
	
	
	//检测导航栏操作
	public static boolean checkDeviceHasNavigationBar(Context context)
	{
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0)
		{
			hasNavigationBar = rs.getBoolean(id);
		}
		try
		{
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride))
			{
				hasNavigationBar = false;
			}
			else if ("0".equals(navBarOverride))
			{
				hasNavigationBar = true;
			}
		}
		catch (Exception e)
		{

		}
		return hasNavigationBar;
	}
	
	
	
	public void vo(View view){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("类原生系统自带的屏幕录制,录屏文件在Picture/Screenrecords 的都可以用，读取这个目录下所有视频，去酷安下载吧");
        builder.setPositiveButton("下载",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					
					Intent a = new Intent();
					Uri b = Uri.parse("https://www.coolapk.com/apk/com.dabai.ScreenRecordManager");
					a.setAction("android.intent.action.VIEW");  
					a.setData(b);
					startActivity(a);
					toast("跳转酷安下载吧");

				}
			});  
		builder.setNeutralButton("取消", null);
        builder.show();
		
		
	}
	
	
	
	
	public void RemovePoint(View view){
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("去除叹号");
        builder.setMessage("把网络测试地址更改为国内,达到去除叹号的目的。");
        builder.setPositiveButton("确认更改",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					String cmd[]={"settings delete global captive_portal_server",
						"settings delete global captive_portal_https_url",
						"settings delete global captive_portal_http_url",
						"settings delete global captive_portal_use_https",
						"settings put global captive_portal_use_https 1",
						"settings put global captive_portal_https_url https://www.qualcomm.cn/generate_204"
					};
					new shell().execCommand(cmd,true);
					alert("修改成功,请手动把WiFi关掉,然后再打开~");
					
					
					
				}
			});  
		builder.setNeutralButton("取消", null);
        builder.show();
			
	}
	
	
	
	
	
	
	
	//工具方法
	
	//循环线程
	private Handler handler = new Handler();   
    private Runnable task = new Runnable() {  
        public void run() {   
			handler.postDelayed(this,100);//设置循环时间，此处是5秒
			br.setText("屏幕亮度("+getSystemBrightness()+")");
       	
			
			
			}   
    };
	
	public void saveBrightness(Activity activity, int brightness) {
        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS, brightness);
        activity.getContentResolver().notifyChange(uri, null);
    }
	
	
	public void su(String[] cmd){
		new shell().execCommand(cmd,true);
	}

	public void toast(String text){
		Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
	}

	public void alert(String text){
		new AlertDialog.Builder(this)
			//.setTitle("标题")
			.setMessage(text)
			.show();
	}
	
	public boolean Exists(String fi){
		try{
			File f=new File(fi);
			if(!f.exists()){
				return false;
			}

		}catch (Exception e) {

			return false;
		}
		return true;
	}
	
	private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }
		
}
