package zhl.shadiaomother;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class biaoqingbao extends AppCompatActivity {
    public static biaoqingbao main;//当一个全局变量使用
    public hua hua;//这个view用来画图片和文字
    public EditText text;//用户输入的文字
    private RecyclerView recy;//滑动控件
    private List<Integer> list;
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        setContentView(R.layout.bpb);//这里改一下，使用的不是activity_main布局
        hua = findViewById(R.id.hua);
        text = findViewById(R.id.text);
        recy = findViewById(R.id.recy);
        //设为横向滚动
        recy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //初始化表情图片，我们把他们的id存起来
        list = new ArrayList<Integer>();
        list.add(R.mipmap.pic);
        Intent intent=getIntent();
        str=intent.getStringExtra("out2");
        text.setText(str);
        hua.img = BitmapFactory.decodeResource(getResources(), list.get(0));
        //recy.setAdapter(new adapter(list));//设置适配器

        findViewById(R.id.xiao).setOnClickListener(new View.OnClickListener() {//添加Button事件 字体缩小
            @Override
            public void onClick(View v) {
                float f = hua.paint.getTextSize();
                if (f > 10) {
                    f--;
                }
                hua.paint.setTextSize(f);
                hua.invalidate();
            }
        });
        findViewById(R.id.da).setOnClickListener(new View.OnClickListener() {//添加Button事件 字体放大
            @Override
            public void onClick(View v) {
                float f = hua.paint.getTextSize();
                f++;
                hua.paint.setTextSize(f);
                hua.invalidate();
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {//添加Button事件 保存图片
            @Override
            public void onClick(View v) {
                hua.invalidate();//刷新画布
                hua.buildDrawingCache();//截图，区域是整个hua
                Bitmap img = hua.getDrawingCache();//获取调用buildDrawingCache()时截取的图片
                String name = "表情" + System.currentTimeMillis() + ".jpg";
                //文件的路径为 sd卡/表情包/
                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/表情包/" + name);
                //判断文件父目录是否存在，不存在则创建
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                try {
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                    img.compress(Bitmap.CompressFormat.JPEG, 50, out);//参数（格式，品质，输出到流）
                    out.flush();
                    out.close();
                    Toast.makeText(getApplicationContext(), "图片已保存到" + f.getParent(), Toast.LENGTH_SHORT).show();
                    //下面这一段代码非常重要 不然图库/QQ/微信都找不到你的图片（重启手机才能看到）
                    //我们要发送一个广播通知图库刷新你的相册
                    //当然手机sd卡那么大全部扫描一遍的话很影响用户体验，所以这里我们只扫描刚刚输出的图片
                    Intent in = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f));//发送广播通知图库刷新amssf
                    sendBroadcast(in);//发送广播
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        text.setSelection(text.getText().toString().length());
        verifyStoragePermissions(this);//动态申请权限
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //这一段代码是复制过来的 动态申请sd卡权限
    //设备系统是 Android 6.0 (API 23) 或更高版本，并且应用的 targetSdkVersion 是 23 或更高版本
    //则针对在 AndroidManifest.xml 中声明的危险权限，在运行时还需要动态请求用户授权。例如读写sd卡

    //这一段也是整个程序写好了才加上来的，因为这个软件在我手机和虚拟机上完美运行 到了别人手机就保存不了了
    //说好的Android 6.0呢？我手机Android 7.1.2都不用动态权限。。。
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

class adapter extends RecyclerView.Adapter<adapter.viewh> {//RecyclerView适配器
    private List<Integer> list;

    public adapter(List l) {
        list = l;
    }

    @Override
    public viewh onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        viewh h = new viewh(v);
        return h;
    }

    @Override
    public void onBindViewHolder(final viewh holder, int position) {
        final int i = list.get(position);
        holder.img.setImageResource(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {//更换图片点击事件。这样做会影响性能，先这样写吧
            @Override
            public void onClick(View v) {
                biaoqingbao.main.hua.img = BitmapFactory.decodeResource(biaoqingbao.main.getResources(), i);
                biaoqingbao.main.hua.invalidate();
            }
        });
    }

    @Override
    public int getItemCount() {//这里要改一下 不然显示不出来
        return list.size();
    }

    static class viewh extends RecyclerView.ViewHolder {
        ImageView img;
        View itemView;

        public viewh(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            this.itemView = itemView;
        }
    }
}
