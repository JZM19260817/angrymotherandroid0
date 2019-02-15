package zhl.shadiaomother;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public MainActivity mc;
    public EditText editText1;
    public EditText editText2;
    public EditText editText3;
    public Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=(EditText)findViewById(R.id.editText1);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
        button1=(Button)findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String txt0="你们这个是什么群啊?你们这是害人不浅啊你们这个群!麻烦你们，真的太过分了!" +
                        "你们搞这个群干什么?我儿子每一科的成绩都不过那个平均分呐，他现在初二!" +
                        "你叫我儿子怎么办啊!他还不到高中啊好不好!你们这是什么群啊?你们害死我儿子了!" +
                        "谁是群主?快点出来你们群主!再不来我去报警了啊!我跟你们说你们这一帮人啊，一天到晚啊搞这些什么"+
                        editText2.getText().toString()+"啊什么"+editText3.getText().toString()+"啊会害死你们哒！你们没有前途?我跟你说你们这"+
                        editText1.getText().toString()+"多个人好好学习不好吗，一天到晚在"+editText3.getText().toString()+
                        "是不是人啊?你们一天到晚"+editText3.getText().toString()+"不好好学习,会没前途的!"+
                        editText3.getText().toString()+"害死人!";
                //Toast.makeText(MainActivity.this, txt0, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,OutActivity.class);
                //给message起一个名字，并传给另一个activity
                intent.putExtra("OUTPUT",txt0);
                //启动意图
                startActivity(intent);
            }
        });
    }


}
