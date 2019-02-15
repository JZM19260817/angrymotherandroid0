package zhl.shadiaomother;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OutActivity extends AppCompatActivity {
    public static OutActivity ot;
    public TextView outText;
    public Button button2;
    public Button button3;
    public Button button4;
    public String msg;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);

        Intent intent=getIntent();
        msg=intent.getStringExtra("OUTPUT");
        outText=(TextView)findViewById(R.id.outText);
        outText.setText(msg);

        //ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String out=outText.getText().toString();
                myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("out",out);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(OutActivity.this,"复制好了，快去怒斥沙雕群友",Toast.LENGTH_LONG).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String out="阿姨，您好，本群除了你儿子以外，成员跨越全球，人均985，211毕业，当然还有国外高校的，掌握多种科学技术知识与神学理论，清楚分析全球各国时政新闻，从属行业包括但不限于政治家，数学家，历史学家，计算机行业，航空航天体系，军事指挥体系（绝大部分曾担任国家高级军事指挥官，拥有丰富作战经验）对所有领域都有深入涉及，请问：你儿子算什么东西？";
                myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("out2",out);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(OutActivity.this,"复制好了，快去怒斥阿姨",Toast.LENGTH_LONG).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutActivity.this,biaoqingbao.class);
                intent.putExtra("out2",msg);
                startActivity(intent);
            }
        });
    }
}
