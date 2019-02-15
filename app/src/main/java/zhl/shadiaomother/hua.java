package zhl.shadiaomother;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.lang.*;
//以下非本人所写，感谢这位大佬
/**
 * Created by Liziguo on 2018/5/31.
 *
 */

public class hua extends View {//这个view叫做“画”用来画表情和文字
    private int w, h;//view宽高
    public float x, y;//控制文本位置
    public Paint paint;//画笔
    public Bitmap img = null;//表情图片

    public hua(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(32);//初始文字大小为70
//        setBackgroundColor(Color.WHITE);//一开始这个类的继承ViewGroup的 如果不添加这句图片画不出来
        setOnTouchListener(new OnTouchListener() {//添加触摸事件
            @Override
            public boolean onTouch(View v, MotionEvent e) {//调节文本位置
                x = e.getX() - paint.getTextSize();
                y = e.getY();
                invalidate();//刷新画布
                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {//画图像
        super.onDraw(canvas);
        if (img != null) canvas.drawBitmap(img, null, new RectF(0, 0, w, h), paint);//画表情图片，平铺整个view
        if (biaoqingbao.main.text != null) {
            String str = biaoqingbao.main.text.getText().toString();
            StringBuffer s0=new StringBuffer(str);
            for(int i=0;i<=s0.length();i++){
                if(i%23==0)
                    s0.insert(i,"\n");
            }
            String[] s=s0.toString().split("\n");
            for (int i = 0; i < s.length; i++) {
                canvas.drawText(s[i], x, y + i * paint.getTextSize(), paint);//这样就能显示多行文字了
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //将宽高保存起来
        this.w = w;
        this.h = h;
        y = h * 0.4f;//文字初始y轴坐标
    }



}
